package org.example.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.UserService;
import org.example.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    public OAuth2LoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        String email = oauthToken.getPrincipal().getAttribute("email");
        String name = oauthToken.getPrincipal().getAttribute("name");

        // Check or create user
        Optional<User> existing = userService.findByEmail(email);
        User user = existing.orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(name.replaceAll("\\s+", "").toLowerCase());
            newUser.setPasswordHash(null);
            newUser.setRole(User.Role.USER);
            userService.registerGoogleUser(newUser);
            return newUser;
        });

        String redirectUrl = String.format(
                "http://localhost:5173/oauth-success?username=%s&email=%s&id=%d",
                user.getUsername(),
                user.getEmail(),
                user.getId()
        );

        response.sendRedirect("http://localhost:5173/oauth-success");

    }

}

package org.example.controllers;

import org.example.domain.ErrorResponse;
import org.example.domain.Result;
import org.example.domain.UserService;
import org.example.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Find user by ID (for testing)
    @GetMapping("/{userId}")
    public ResponseEntity<User> findById(@PathVariable int userId) {
        Optional<User> user = userService.findById(userId);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user) {
        Result<User> result = userService.register(user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        Result<User> result = userService.authenticate(user.getUsername(), user.getPasswordHash());
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getPayload());
        }
        return ErrorResponse.build(result);
    }
}

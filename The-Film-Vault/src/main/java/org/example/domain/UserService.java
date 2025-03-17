package org.example.domain;

import org.example.data.UserRepository;
import org.example.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Result<User> register(User user) {
        Result<User> result = new Result<>();

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            result.addMessage("Username is already taken.", ResultType.INVALID);
            return result;
        }

        if (!userRepository.create(user)) {
            result.addMessage("User could not be created.", ResultType.INVALID);
            return result;
        }

        result.setPayload(user);
        return result;
    }

    public Result<User> authenticate(String username, String password) {
        Result<User> result = new Result<>();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty() || !userOpt.get().getPasswordHash().equals(password)) {
            result.addMessage("Invalid username or password.", ResultType.INVALID);
            return result;
        }

        result.setPayload(userOpt.get());
        return result;
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}

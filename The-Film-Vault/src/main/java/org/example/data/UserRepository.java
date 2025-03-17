package org.example.data;

import org.example.models.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    Optional<User> findById(int id);
    boolean create(User user);
}
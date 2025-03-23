package org.example.data;

import org.example.models.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserJdbcClientRepository implements UserRepository {

    private final JdbcClient jdbcClient;

    public UserJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        final String sql = "SELECT * FROM Users WHERE username = ?;";

        return jdbcClient.sql(sql)
                .param(username)
                .query((rs, rowNum) -> new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        User.Role.valueOf(rs.getString("role")),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ))
                .optional();
    }

    @Override
    public Optional<User> findById(int id) {
        final String sql = "SELECT * FROM Users WHERE id = ?;";

        return jdbcClient.sql(sql)
                .param(id)
                .query((rs, rowNum) -> new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        User.Role.valueOf(rs.getString("role")),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ))
                .optional();
    }

    @Override
    public boolean create(User user) {
        final String sql = "INSERT INTO Users (username, email, password_hash, role) VALUES (?, ?, ?, ?);";

        return jdbcClient.sql(sql)
                .params(user.getUsername(), user.getEmail(), user.getPasswordHash(), user.getRole().name())
                .update() > 0;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        final String sql = "SELECT * FROM Users WHERE email = ?;";

        return jdbcClient.sql(sql)
                .param(email)
                .query((rs, rowNum) -> new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        User.Role.valueOf(rs.getString("role")),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ))
                .optional();
    }

}

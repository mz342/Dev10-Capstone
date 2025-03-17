package org.example.data;

import org.example.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserJdbcClientRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByUsername(String username) {
        final String sql = "SELECT * FROM Users WHERE username = ?;";
        return jdbcTemplate.query(sql, new UserMapper(), username)
                .stream()
                .findFirst();
    }

    public Optional<User> findById(int id) {
        final String sql = "SELECT * FROM Users WHERE id = ?;";
        return jdbcTemplate.query(sql, new UserMapper(), id)
                .stream()
                .findFirst();
    }

    public boolean create(User user) {
        final String sql = "INSERT INTO Users (username, email, password_hash, role) VALUES (?, ?, ?, ?);";
        return jdbcTemplate.update(sql,
                user.getUsername(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRole().name()) > 0;
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPasswordHash(rs.getString("password_hash"));
            user.setRole(User.Role.valueOf(rs.getString("role")));
            user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return user;
        }
    }
}

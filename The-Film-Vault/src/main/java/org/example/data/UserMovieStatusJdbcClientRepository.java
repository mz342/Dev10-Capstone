package org.example.data;

import org.example.models.UserMovieStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserMovieStatusJdbcClientRepository implements UserMovieStatusRepository {

    private final JdbcClient jdbcClient;

    public UserMovieStatusJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<UserMovieStatus> findAllByUserId(int userId) {
        final String sql = """
            SELECT ums.*, m.title, m.poster
            FROM UserMovieStatus ums
            JOIN Movies m ON ums.movie_id = m.id
            WHERE ums.user_id = ?;
        """;

        return jdbcClient.sql(sql)
                .param(userId)
                .query((rs, rowNum) -> {
                    UserMovieStatus status = new UserMovieStatus(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("movie_id"),
                            UserMovieStatus.Status.valueOf(rs.getString("status")),
                            rs.getTimestamp("updated_at").toLocalDateTime()
                    );
                    status.setTitle(rs.getString("title"));
                    status.setPoster(rs.getString("poster"));
                    return status;
                })
                .list();
    }

    @Override
    public List<UserMovieStatus> findAllByMovieId(int movieId) {
        final String sql = "SELECT * FROM UserMovieStatus WHERE movie_id = ?;";

        return jdbcClient.sql(sql)
                .param(movieId)
                .query((rs, rowNum) -> new UserMovieStatus(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("movie_id"),
                        UserMovieStatus.Status.valueOf(rs.getString("status")),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ))
                .list();
    }

    @Override
    public Optional<UserMovieStatus> findByUserAndMovie(int userId, int movieId) {
        final String sql = "SELECT * FROM UserMovieStatus WHERE user_id = ? AND movie_id = ?;";

        return jdbcClient.sql(sql)
                .params(userId, movieId)
                .query((rs, rowNum) -> new UserMovieStatus(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("movie_id"),
                        UserMovieStatus.Status.valueOf(rs.getString("status")),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ))
                .optional();
    }

    @Override
    public boolean save(UserMovieStatus status) {
        final String sql = """
            INSERT INTO UserMovieStatus (user_id, movie_id, status)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE status = VALUES(status), updated_at = CURRENT_TIMESTAMP;
        """;

        return jdbcClient.sql(sql)
                .params(status.getUserId(), status.getMovieId(), status.getStatus().name())
                .update() > 0;
    }

    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM UserMovieStatus WHERE id = ?;";
        return jdbcClient.sql(sql).param(id).update() > 0;
    }
}

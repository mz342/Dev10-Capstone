package org.example.data;

import org.example.models.Review;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReviewJdbcClientRepository implements ReviewRepository {

    private final JdbcClient jdbcClient;

    public ReviewJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Review> findAllByMovieId(int movieId) {
        final String sql = "SELECT * FROM Reviews WHERE movie_id = ?;";

        return jdbcClient.sql(sql)
                .param(movieId)
                .query((rs, rowNum) -> new Review(
                        rs.getInt("id"),
                        rs.getInt("movie_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("rating"),
                        rs.getString("review_text"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ))
                .list();
    }

    @Override
    public List<Review> findAllByUserId(int userId) {
        final String sql = "SELECT * FROM Reviews WHERE user_id = ?;";

        return jdbcClient.sql(sql)
                .param(userId)
                .query((rs, rowNum) -> new Review(
                        rs.getInt("id"),
                        rs.getInt("movie_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("rating"),
                        rs.getString("review_text"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ))
                .list();
    }

    @Override
    public Optional<Review> findById(int id) {
        final String sql = "SELECT * FROM Reviews WHERE id = ?;";

        return jdbcClient.sql(sql)
                .param(id)
                .query((rs, rowNum) -> new Review(
                        rs.getInt("id"),
                        rs.getInt("movie_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("rating"),
                        rs.getString("review_text"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ))
                .optional();
    }

    @Override
    public boolean create(Review review) {
        final String sql = "INSERT INTO Reviews (movie_id, user_id, rating, review_text) " +
                "VALUES (?, ?, ?, ?);";

        return jdbcClient.sql(sql)
                .params(review.getMovieId(), review.getUserId(), review.getRating(), review.getReviewText())
                .update() > 0;
    }

    @Override
    public boolean update(Review review) {
        final String sql = "UPDATE Reviews SET rating = ?, review_text = ? WHERE id = ?;";

        return jdbcClient.sql(sql)
                .params(review.getRating(), review.getReviewText(), review.getId())
                .update() > 0;
    }

    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM Reviews WHERE id = ?;";

        return jdbcClient.sql(sql)
                .param(id)
                .update() > 0;
    }
}

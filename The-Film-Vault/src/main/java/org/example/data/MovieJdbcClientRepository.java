package org.example.data;

import org.example.models.Movie;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieJdbcClientRepository implements MovieRepository {

    private final JdbcClient jdbcClient;

    public MovieJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Movie> findAll() {
        final String sql = "SELECT * FROM Movies;";
        return jdbcClient.sql(sql)
                .query((rs, rowNum) -> new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("release_year"),
                        rs.getString("director"),
                        rs.getDouble("rating"),
                        rs.getString("poster"), // ✅ Poster column now included
                        rs.getTimestamp("created_at").toLocalDateTime()
                ))
                .list();
    }

    @Override
    public Optional<Movie> findById(int id) {
        final String sql = "SELECT * FROM Movies WHERE id = ?;";

        return jdbcClient.sql(sql)
                .param(id)
                .query((rs, rowNum) -> new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("release_year"),
                        rs.getString("director"),
                        rs.getDouble("rating"),
                        rs.getString("poster"), // ✅ Poster column now included
                        rs.getTimestamp("created_at").toLocalDateTime()
                ))
                .optional();
    }

    @Override
    public boolean create(Movie movie) {
        final String sql = "INSERT INTO Movies (title, description, release_year, director, rating, poster) " +
                "VALUES (?, ?, ?, ?, ?, ?);";

        return jdbcClient.sql(sql)
                .params(movie.getTitle(), movie.getDescription(), movie.getReleaseYear(),
                        movie.getDirector(), movie.getRating(), movie.getPoster()) // ✅ Added poster
                .update() > 0;
    }

    @Override
    public boolean update(Movie movie) {
        final String sql = "UPDATE Movies SET title = ?, description = ?, release_year = ?, " +
                "director = ?, rating = ?, poster = ? WHERE id = ?;";

        return jdbcClient.sql(sql)
                .params(movie.getTitle(), movie.getDescription(), movie.getReleaseYear(),
                        movie.getDirector(), movie.getRating(), movie.getPoster(), movie.getId()) // ✅ Added poster
                .update() > 0;
    }

    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM Movies WHERE id = ?;";

        return jdbcClient.sql(sql)
                .param(id)
                .update() > 0;
    }
}

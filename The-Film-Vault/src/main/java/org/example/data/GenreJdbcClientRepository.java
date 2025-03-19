package org.example.data;

import org.example.models.Genre;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreJdbcClientRepository implements GenreRepository {

    private final JdbcClient jdbcClient;

    public GenreJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Genre> findAll() {
        final String sql = "SELECT * FROM Genres;";
        return jdbcClient.sql(sql)
                .query((rs, rowNum) -> new Genre(
                        rs.getInt("id"),
                        rs.getString("name")
                ))
                .list();
    }

    @Override
    public Optional<Genre> findById(int id) {
        final String sql = "SELECT * FROM Genres WHERE id = ?;";

        return jdbcClient.sql(sql)
                .param(id)
                .query((rs, rowNum) -> new Genre(
                        rs.getInt("id"),
                        rs.getString("name")
                ))
                .optional();
    }

    @Override
    public boolean create(Genre genre) {
        final String sql = "INSERT INTO Genres (name) VALUES (?);";

        return jdbcClient.sql(sql)
                .param(genre.getName())
                .update() > 0;
    }

    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM Genres WHERE id = ?;";

        return jdbcClient.sql(sql)
                .param(id)
                .update() > 0;
    }

    @Override
    public List<Genre> findByMovieId(int movieId) {
        final String sql = "SELECT g.id, g.name FROM Genres g " +
                "JOIN MovieGenres mg ON g.id = mg.genre_id " +
                "WHERE mg.movie_id = ?;";

        return jdbcClient.sql(sql)
                .param(movieId)
                .query((rs, rowNum) -> new Genre(
                        rs.getInt("id"),
                        rs.getString("name")
                ))
                .list();
    }

    @Override
    public boolean addGenreToMovie(int movieId, int genreId) {
        final String sql = "INSERT INTO MovieGenres (movie_id, genre_id) VALUES (?, ?);";

        return jdbcClient.sql(sql)
                .params(movieId, genreId)
                .update() > 0;
    }

    @Override
    public boolean removeGenreFromMovie(int movieId, int genreId) {
        final String sql = "DELETE FROM MovieGenres WHERE movie_id = ? AND genre_id = ?;";

        return jdbcClient.sql(sql)
                .params(movieId, genreId)
                .update() > 0;
    }
}

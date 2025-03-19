package org.example.data;

import org.example.models.Genre;
import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> findAll();
    Optional<Genre> findById(int id);
    boolean create(Genre genre);
    boolean deleteById(int id);
    List<Genre> findByMovieId(int movieId);
    boolean addGenreToMovie(int movieId, int genreId);
    boolean removeGenreFromMovie(int movieId, int genreId);
}

package org.example.data;

import org.example.models.Movie;
import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    List<Movie> findAll();
    Optional<Movie> findById(int id);
    boolean create(Movie movie);
    boolean update(Movie movie);
    boolean deleteById(int id);
}
package org.example.data;

import org.example.models.UserMovieStatus;
import java.util.List;
import java.util.Optional;

public interface UserMovieStatusRepository {
    List<UserMovieStatus> findAllByUserId(int userId);
    List<UserMovieStatus> findAllByMovieId(int movieId);
    Optional<UserMovieStatus> findByUserAndMovie(int userId, int movieId);
    boolean save(UserMovieStatus status);
    boolean deleteById(int id);
}

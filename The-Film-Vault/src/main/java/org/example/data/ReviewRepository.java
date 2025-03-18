package org.example.data;

import org.example.models.Review;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    List<Review> findAllByMovieId(int movieId);
    List<Review> findAllByUserId(int userId);
    Optional<Review> findById(int id);
    boolean create(Review review);
    boolean update(Review review);
    boolean deleteById(int id);
}

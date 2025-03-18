package org.example.domain;

import org.example.data.ReviewRepository;
import org.example.models.Review;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> findAllByMovieId(int movieId) {
        return reviewRepository.findAllByMovieId(movieId);
    }

    public List<Review> findAllByUserId(int userId) {
        return reviewRepository.findAllByUserId(userId);
    }

    public Result<Review> findById(int id) {
        Result<Review> result = new Result<>();
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isEmpty()) {
            result.addMessage("Review not found.", ResultType.NOT_FOUND);
            return result;
        }
        result.setPayload(review.get());
        return result;
    }

    public Result<Review> add(Review review) {
        Result<Review> result = validate(review);
        if (!result.isSuccess()) {
            return result;
        }

        if (!reviewRepository.create(review)) {
            result.addMessage("Review could not be added.", ResultType.INVALID);
        } else {
            result.setPayload(review);
        }

        return result;
    }

    public Result<Review> update(Review review) {
        Result<Review> result = validate(review);
        if (!result.isSuccess()) {
            return result;
        }

        if (!reviewRepository.update(review)) {
            result.addMessage("Review update failed. Review not found.", ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<Review> deleteById(int id) {
        Result<Review> result = new Result<>();
        if (!reviewRepository.deleteById(id)) {
            result.addMessage("Review not found.", ResultType.NOT_FOUND);
        }
        return result;
    }

    private Result<Review> validate(Review review) {
        Result<Review> result = new Result<>();

        if (review.getRating() < 0 || review.getRating() > 10) {
            result.addMessage("Rating must be between 0 and 10.", ResultType.INVALID);
        }

        if (review.getReviewText() == null || review.getReviewText().isBlank()) {
            result.addMessage("Review text is required.", ResultType.INVALID);
        }

        return result;
    }
}

package org.example.controllers;

import org.example.domain.ErrorResponse;
import org.example.domain.ReviewService;
import org.example.domain.Result;
import org.example.models.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/movie/{movieId}") //
    public ResponseEntity<List<Review>> findAllByMovieId(@PathVariable int movieId) {
        List<Review> reviews = reviewService.findAllByMovieId(movieId);
        if (reviews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reviews);
    }


    @GetMapping("/user/{userId}")
    public List<Review> findAllByUserId(@PathVariable int userId) {
        return reviewService.findAllByUserId(userId);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Object> findById(@PathVariable int reviewId) {
        Result<Review> result = reviewService.findById(reviewId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getPayload());
        }
        return ErrorResponse.build(result);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Review review) {
        Result<Review> result = reviewService.add(review);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Object> update(@PathVariable int reviewId, @RequestBody Review review) {
        if (reviewId != review.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Review> result = reviewService.update(review);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteById(@PathVariable int reviewId) {
        Result<Review> result = reviewService.deleteById(reviewId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

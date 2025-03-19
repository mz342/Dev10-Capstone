package org.example.controllers;

import org.example.domain.ErrorResponse;
import org.example.domain.UserMovieStatusService;
import org.example.domain.Result;
import org.example.models.UserMovieStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-movie-status")
public class UserMovieStatusController {

    private final UserMovieStatusService userMovieStatusService;

    public UserMovieStatusController(UserMovieStatusService userMovieStatusService) {
        this.userMovieStatusService = userMovieStatusService;
    }

    @GetMapping("/user/{userId}")
    public List<UserMovieStatus> findAllByUserId(@PathVariable int userId) {
        return userMovieStatusService.findAllByUserId(userId);
    }

    @GetMapping("/movie/{movieId}")
    public List<UserMovieStatus> findAllByMovieId(@PathVariable int movieId) {
        return userMovieStatusService.findAllByMovieId(movieId);
    }

    @GetMapping("/{userId}/{movieId}")
    public ResponseEntity<Object> findByUserAndMovie(@PathVariable int userId, @PathVariable int movieId) {
        Result<UserMovieStatus> result = userMovieStatusService.findByUserAndMovie(userId, movieId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getPayload());
        }
        return ErrorResponse.build(result);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody UserMovieStatus status) {
        Result<UserMovieStatus> result = userMovieStatusService.save(status);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        Result<UserMovieStatus> result = userMovieStatusService.deleteById(id);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

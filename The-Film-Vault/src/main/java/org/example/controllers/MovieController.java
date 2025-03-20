package org.example.controllers;

import org.example.domain.ErrorResponse;
import org.example.domain.MovieService;
import org.example.domain.Result;
import org.example.models.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> findAll() {
        return movieService.findAll();
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Object> findById(@PathVariable int movieId) {
        Result<Movie> result = movieService.findById(movieId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getPayload());
        }
        return ErrorResponse.build(result);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Movie movie) {
        Result<Movie> result = movieService.add(movie);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<Object> update(@PathVariable int movieId, @RequestBody Movie movie) {
        if (movieId != movie.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Movie> result = movieService.update(movie);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteById(@PathVariable int movieId) {
        Result<Movie> result = movieService.deleteById(movieId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

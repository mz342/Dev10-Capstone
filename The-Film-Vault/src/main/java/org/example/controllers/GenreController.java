package org.example.controllers;

import org.example.domain.ErrorResponse;
import org.example.domain.GenreService;
import org.example.domain.Result;
import org.example.models.Genre;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<Genre> findAll() {
        return genreService.findAll();
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<Object> findById(@PathVariable int genreId) {
        Result<Genre> result = genreService.findById(genreId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getPayload());
        }
        return ErrorResponse.build(result);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Genre genre) {
        Result<Genre> result = genreService.add(genre);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<Void> deleteById(@PathVariable int genreId) {
        Result<Genre> result = genreService.deleteById(genreId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/movie/{movieId}")
    public List<Genre> findByMovieId(@PathVariable int movieId) {
        return genreService.findByMovieId(movieId);
    }

    @PostMapping("/movie/{movieId}/{genreId}")
    public ResponseEntity<Object> addGenreToMovie(@PathVariable int movieId, @PathVariable int genreId) {
        Result<Void> result = genreService.addGenreToMovie(movieId, genreId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/movie/{movieId}/{genreId}")
    public ResponseEntity<Void> removeGenreFromMovie(@PathVariable int movieId, @PathVariable int genreId) {
        Result<Void> result = genreService.removeGenreFromMovie(movieId, genreId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

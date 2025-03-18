package org.example.domain;

import org.example.data.MovieRepository;
import org.example.models.Movie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Result<Movie> findById(int id) {
        Result<Movie> result = new Result<>();
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            result.addMessage("Movie not found.", ResultType.NOT_FOUND);
            return result;
        }
        result.setPayload(movie.get());
        return result;
    }

    public Result<Movie> add(Movie movie) {
        Result<Movie> result = validate(movie);
        if (!result.isSuccess()) {
            return result;
        }

        if (!movieRepository.create(movie)) {
            result.addMessage("Movie could not be added.", ResultType.INVALID);
        } else {
            result.setPayload(movie);
        }

        return result;
    }

    public Result<Movie> update(Movie movie) {
        Result<Movie> result = validate(movie);
        if (!result.isSuccess()) {
            return result;
        }

        if (!movieRepository.update(movie)) {
            result.addMessage("Movie update failed. Movie not found.", ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<Movie> deleteById(int id) {
        Result<Movie> result = new Result<>();
        if (!movieRepository.deleteById(id)) {
            result.addMessage("Movie not found.", ResultType.NOT_FOUND);
        }
        return result;
    }

    private Result<Movie> validate(Movie movie) {
        Result<Movie> result = new Result<>();

        if (movie.getTitle() == null || movie.getTitle().isBlank()) {
            result.addMessage("Title is required.", ResultType.INVALID);
        }

        if (movie.getRating() < 0 || movie.getRating() > 10) {
            result.addMessage("Rating must be between 0 and 10.", ResultType.INVALID);
        }

        return result;
    }
}

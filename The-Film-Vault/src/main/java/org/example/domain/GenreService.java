package org.example.domain;

import org.example.data.GenreRepository;
import org.example.models.Genre;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Result<Genre> findById(int id) {
        Result<Genre> result = new Result<>();
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isEmpty()) {
            result.addMessage("Genre not found.", ResultType.NOT_FOUND);
            return result;
        }
        result.setPayload(genre.get());
        return result;
    }

    public Result<Genre> add(Genre genre) {
        Result<Genre> result = validate(genre);
        if (!result.isSuccess()) {
            return result;
        }

        if (!genreRepository.create(genre)) {
            result.addMessage("Genre could not be added.", ResultType.INVALID);
        } else {
            result.setPayload(genre);
        }

        return result;
    }

    public Result<Genre> deleteById(int id) {
        Result<Genre> result = new Result<>();
        if (!genreRepository.deleteById(id)) {
            result.addMessage("Genre not found.", ResultType.NOT_FOUND);
        }
        return result;
    }

    public List<Genre> findByMovieId(int movieId) {
        return genreRepository.findByMovieId(movieId);
    }

    public Result<Void> addGenreToMovie(int movieId, int genreId) {
        Result<Void> result = new Result<>();
        if (!genreRepository.addGenreToMovie(movieId, genreId)) {
            result.addMessage("Could not add genre to movie.", ResultType.INVALID);
        }
        return result;
    }

    public Result<Void> removeGenreFromMovie(int movieId, int genreId) {
        Result<Void> result = new Result<>();
        if (!genreRepository.removeGenreFromMovie(movieId, genreId)) {
            result.addMessage("Could not remove genre from movie.", ResultType.NOT_FOUND);
        }
        return result;
    }

    private Result<Genre> validate(Genre genre) {
        Result<Genre> result = new Result<>();

        if (genre.getName() == null || genre.getName().isBlank()) {
            result.addMessage("Genre name is required.", ResultType.INVALID);
        }

        return result;
    }
}

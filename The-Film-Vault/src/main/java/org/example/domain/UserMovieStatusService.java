package org.example.domain;

import org.example.data.UserMovieStatusRepository;
import org.example.models.UserMovieStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserMovieStatusService {

    private final UserMovieStatusRepository userMovieStatusRepository;

    public UserMovieStatusService(UserMovieStatusRepository userMovieStatusRepository) {
        this.userMovieStatusRepository = userMovieStatusRepository;
    }

    public List<UserMovieStatus> findAllByUserId(int userId) {
        return userMovieStatusRepository.findAllByUserId(userId);
    }

    public List<UserMovieStatus> findAllByMovieId(int movieId) {
        return userMovieStatusRepository.findAllByMovieId(movieId);
    }

    public Result<UserMovieStatus> findByUserAndMovie(int userId, int movieId) {
        Result<UserMovieStatus> result = new Result<>();
        Optional<UserMovieStatus> status = userMovieStatusRepository.findByUserAndMovie(userId, movieId);
        if (status.isEmpty()) {
            result.addMessage("User movie status not found.", ResultType.NOT_FOUND);
            return result;
        }
        result.setPayload(status.get());
        return result;
    }

    public Result<UserMovieStatus> save(UserMovieStatus status) {
        Result<UserMovieStatus> result = validate(status);
        if (!result.isSuccess()) {
            return result;
        }

        if (!userMovieStatusRepository.save(status)) {
            result.addMessage("User movie status could not be saved.", ResultType.INVALID);
        } else {
            result.setPayload(status);
        }

        return result;
    }

    public Result<UserMovieStatus> deleteById(int id) {
        Result<UserMovieStatus> result = new Result<>();
        if (!userMovieStatusRepository.deleteById(id)) {
            result.addMessage("User movie status not found.", ResultType.NOT_FOUND);
        }
        return result;
    }

    private Result<UserMovieStatus> validate(UserMovieStatus status) {
        Result<UserMovieStatus> result = new Result<>();

        if (status.getStatus() == null) {
            result.addMessage("Status is required.", ResultType.INVALID);
        }

        return result;
    }
}

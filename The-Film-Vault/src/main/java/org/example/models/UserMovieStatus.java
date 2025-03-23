package org.example.models;

import java.time.LocalDateTime;

public class UserMovieStatus {

    public enum Status {
        WATCHED,
        WATCHING,
        WANT_TO_WATCH
    }

    private int id;
    private int userId;
    private int movieId;
    private Status status;
    private LocalDateTime updatedAt;

    private String title;
    private String poster;

    // Constructors
    public UserMovieStatus() {
    }

    public UserMovieStatus(int id, int userId, int movieId, Status status, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "UserMovieStatus{" +
                "id=" + id +
                ", userId=" + userId +
                ", movieId=" + movieId +
                ", status=" + status +
                ", updatedAt=" + updatedAt +
                ", title='" + title + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }
}

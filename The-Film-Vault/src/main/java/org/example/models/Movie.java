package org.example.models;

import java.time.LocalDateTime;
import java.util.List;

public class Movie {
    private int id;
    private String title;
    private String description;
    private int releaseYear;
    private String director;
    private double rating;
    private LocalDateTime createdAt;
    private String poster;
    private List<Genre> genres;


    // Constructors
    public Movie() {
    }

    public Movie(int id, String title, String description, int releaseYear, String director, double rating, String poster, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.director = director;
        this.rating = rating;
        this.poster = poster;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseYear=" + releaseYear +
                ", director='" + director + '\'' +
                ", rating=" + rating +
                ", createdAt=" + createdAt +
                ", poster='" + poster + '\'' +
                '}';
    }
}

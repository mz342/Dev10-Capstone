import { useEffect, useState } from "react";
import { fetchMovies } from "../api/movies";
import { Link } from "react-router-dom";
import "./Home.css"; 

export default function Home() {
  const [movies, setMovies] = useState([]);

  useEffect(() => {
    fetchMovies().then(setMovies);
  }, []);

  return (
    <div className="container mt-5">
      <h1 className="text-center mb-4">🎬 Movies</h1>
      <div className="movies-grid">
        {movies.length > 0 ? (
          movies.map((movie) => (
            <div key={movie.id} className="movie-card">
              <Link to={`/movies/${movie.id}`} className="movie-link">
                <div className="movie-image-container">
                  <img
                    src={movie.poster || "https://via.placeholder.com/300x450?text=No+Image"}
                    alt={movie.title}
                    className="movie-poster"
                  />
                  <div className="overlay">
                    <h5 className="movie-title">{movie.title}</h5>
                    <p className="movie-rating">⭐ {movie.rating}/10</p>
                    <p className="movie-year">{movie.release_year}</p>
                  </div>
                </div>
              </Link>
              <div className="movie-info">
                <p className="movie-description">{movie.description.slice(0, 100)}...</p>
                <Link to={`/movies/${movie.id}`} className="btn btn-primary btn-sm">
                  View Details
                </Link>
              </div>
            </div>
          ))
        ) : (
          <p className="text-center text-muted">No movies available.</p>
        )}
      </div>
    </div>
  );
}

import { useEffect, useState } from "react";
import { fetchMovies } from "../api/movies";
import { Link } from "react-router-dom";

export default function Home() {
  const [movies, setMovies] = useState([]);

  useEffect(() => {
    fetchMovies().then(setMovies);
  }, []);

  return (
    <div className="container mt-5">
      <h1 className="text-center mb-4">🎬 Movies</h1>
      <div className="row">
        {movies.length > 0 ? (
          movies.map((movie) => (
            <div key={movie.id} className="col-md-4 mb-4">
              <div className="card shadow-sm">
                <img
                  src={movie.poster || "https://via.placeholder.com/300x450?text=No+Image"}
                  alt={movie.title}
                  className="card-img-top"
                />
                <div className="card-body">
                  <h5 className="card-title">{movie.title}</h5>
                  <p className="card-text">{movie.description.slice(0, 100)}...</p>
                  <Link to={`/movies/${movie.id}`} className="btn btn-primary">
                    View Details
                  </Link>
                </div>
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

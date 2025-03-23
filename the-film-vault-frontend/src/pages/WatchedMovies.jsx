import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getPosterUrl } from "../api/tmdb";

export default function WatchedMovies() {
  const [movies, setMovies] = useState([]);
  const user = JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    if (!user) return;

    fetch(`http://localhost:8080/api/user-movie-status/user/${user.id}`, {
      credentials: "include"
    })
      .then((res) => res.json())
      .then((data) => {
        // Filter for only watched movies
        const watchedMovies = data.filter(movie => movie.status === "WATCHED");
        setMovies(watchedMovies);
      })
      .catch((error) => console.error("Error fetching watched movies:", error));
  }, [user]);

  if (!user) return <p className="text-center">Please log in to view your watched movies.</p>;

  return (
    <div className="container mt-5">
      <h2>Watched Movies</h2>
      {movies.length === 0 ? (
        <p className="text-muted">You haven't marked any movies as watched.</p>
      ) : (
        <div className="row">
          {movies.map((movie) => (
            <div key={movie.movieId} className="col-md-3 mb-4">
              <div className="card h-100 shadow-sm">
                <img
                  src={getPosterUrl(movie.poster)}
                  alt={movie.title}
                  className="card-img-top"
                />
                <div className="card-body d-flex flex-column justify-content-between">
                  <h5 className="card-title">{movie.title}</h5>
                  <Link to={`/movies/${movie.movieId}`} className="btn btn-primary btn-sm mt-2">
                    View Details
                  </Link>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

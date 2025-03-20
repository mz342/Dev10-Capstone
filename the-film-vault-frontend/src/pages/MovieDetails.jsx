import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function MovieDetails() {
  const { id } = useParams();
  const [movie, setMovie] = useState(null);

  useEffect(() => {
    fetch(`http://localhost:8080/api/movies/${id}`)
      .then((res) => res.json())
      .then(setMovie)
      .catch((error) => console.error("Error fetching movie:", error));
  }, [id]);

  if (!movie) return <p className="text-center text-muted">Loading...</p>;

  return (
    <div className="container mt-5">
      <div className="row">
        <div className="col-md-4">
          <img
            src={movie.poster || "https://via.placeholder.com/300x450?text=No+Image"}
            alt={movie.title}
            className="img-fluid rounded shadow"
          />
        </div>
        <div className="col-md-8">
          <h1>{movie.title}</h1>
          <p className="lead">{movie.description}</p>
          <p><strong>Director:</strong> {movie.director}</p>
          <p><strong>Release Year:</strong> {movie.release_year}</p>
          <p><strong>Rating:</strong> {movie.rating}/10</p>
        </div>
      </div>
    </div>
  );
}

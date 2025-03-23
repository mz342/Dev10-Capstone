import { useEffect, useState } from "react";
import { getPosterUrl } from "../api/tmdb";

export default function Watchlist() {
  const [watchlist, setWatchlist] = useState([]);
  const user = JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    if (!user) return;

    fetch(`http://localhost:8080/api/user-movie-status/user/${user.id}`, {
        credentials: "include"
    })
      .then((res) => res.json())
      .then((data) => {
        // Filter movies that are in the "WANT_TO_WATCH" status
        const filteredMovies = data.filter((movie) => movie.status === "WANT_TO_WATCH");
        setWatchlist(filteredMovies);
      })
      .catch((error) => console.error("Error fetching watchlist:", error));
  }, [user]);

  const handleRemove = async (movieId) => {
    if (!user) return;
  
    // Fetch the status ID first
    const response = await fetch(`http://localhost:8080/api/user-movie-status/${user.id}/${movieId}`, {
        credentials: "include"
    });
  
    if (!response.ok) {
      console.error("Failed to find movie status for removal.");
      return;
    }
  
    const data = await response.json();
    
    if (data.status === "NONE" || !data.id) {
      console.error("No valid status ID found.");
      return;
    }
  
    // Now delete by status ID
    const deleteResponse = await fetch(`http://localhost:8080/api/user-movie-status/${data.id}`, {
      credentials: "include",
      method: "DELETE",
    });
  
    if (deleteResponse.ok) {
      setWatchlist(watchlist.filter((movie) => movie.movieId !== movieId));
    } else {
      console.error("Failed to remove movie from watchlist.");
    }
  };
  

  if (!user) return <p className="text-center text-muted">You must be logged in to view your watchlist.</p>;

  return (
    <div className="container mt-5">
      <h2>Your Watchlist</h2>
      {watchlist.length === 0 ? (
        <p className="text-muted">Your watchlist is empty.</p>
      ) : (
        <div className="row">
          {watchlist.map((movie) => (
            <div key={movie.movieId} className="col-md-3 mb-4">
  <div className="card h-100 shadow-sm">
  <img
    src={getPosterUrl(movie.poster)}
    alt={movie.title || "Movie Poster"}
    className="card-img-top"
  />
  <div className="card-body d-flex flex-column justify-content-between">
                  <h5 className="card-title">{movie.title}</h5>
                  <button className="btn btn-danger btn-sm" onClick={() => handleRemove(movie.movieId)}>
                    Remove from Watchlist
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

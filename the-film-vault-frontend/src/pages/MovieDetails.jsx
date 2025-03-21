import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function MovieDetails() {
  const { id } = useParams();
  const [movie, setMovie] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [newReview, setNewReview] = useState("");
  const [status, setStatus] = useState(null);

  useEffect(() => {
    // Fetch movie details
    fetch(`http://localhost:8080/api/movies/${id}`)
      .then((res) => res.json())
      .then(setMovie)
      .catch((error) => console.error("Error fetching movie:", error));
  
    //  Fetch reviews 
    fetch(`http://localhost:8080/api/reviews/movie/${id}`)
      .then((res) => res.json())
      .then((data) => {
        console.log("Fetched Reviews from API:", data); //  Debugging log

        if (Array.isArray(data)) {
          setReviews(data); //  Correct format
        } else {
          console.error("Invalid response format for reviews:", data);
          setReviews([]); // Prevents crashes
        }
      })
      .catch((error) => console.error("Error fetching reviews:", error));

    // Fetch user movie status (Watchlist/Watched)
    const user = JSON.parse(localStorage.getItem("user"));
    if (user) {
      fetch(`http://localhost:8080/api/user-movie-status/${user.id}/${id}`)
        .then((res) => res.json())
        .then((data) => setStatus(data.status))
        .catch(() => setStatus(null)); // Default to null if no status
    }
}, [id]);


const handleAddReview = async () => {
    const user = JSON.parse(localStorage.getItem("user"));
    if (!user) return alert("You must be logged in to leave a review.");
  
    const reviewPayload = {
      userId: user.id,
      movieId: id,
      reviewText: newReview
    };
  
    console.log("Submitting Review:", reviewPayload); //  Debugging log
  
    const response = await fetch("http://localhost:8080/api/reviews", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(reviewPayload),
    });
  
    if (!response.ok) {
      const errorText = await response.text(); //  Capture backend error message
      console.error("Failed to submit review:", errorText);
      return;
    }
  
    console.log("Review added successfully!");
    setNewReview(""); //  Clear input field
  
    fetch(`http://localhost:8080/api/reviews/movie/${id}`)
      .then((res) => res.json())
      .then((data) => {
        console.log("Updated Reviews After Submission:", data); // Debugging log
        if (Array.isArray(data)) {
          setReviews(data); //  Properly updates the state with the latest reviews
        } else {
          console.error("Invalid response format for reviews:", data);
          setReviews([]);
        }
      })
      .catch((error) => console.error("Error fetching updated reviews:", error));
  };
  

  const updateStatus = async (newStatus) => {
    const user = JSON.parse(localStorage.getItem("user"));
    if (!user) return alert("You must be logged in to update your watchlist.");
  
    //  Convert frontend-friendly names to backend enum values
    const statusMapping = {
      WATCHLIST: "WANT_TO_WATCH", 
      WATCHING: "WATCHING",
      WATCHED: "WATCHED",
    };
  
    const backendStatus = statusMapping[newStatus]; 
    if (!backendStatus) {
      console.error("Invalid status:", newStatus);
      return;
    }
  
    const statusPayload = {
      userId: user.id,
      movieId: parseInt(id, 10), // Ensure `id` is an integer
      status: backendStatus, 
    };
  
    console.log("Submitting Movie Status Update:", statusPayload); //  Debugging log
  
    const response = await fetch("http://localhost:8080/api/user-movie-status", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(statusPayload),
    });
  
    if (!response.ok) {
      const errorText = await response.text();
      console.error("Failed to update movie status:", errorText);
      return;
    }
  
    console.log("Movie status updated successfully!");
    setStatus(backendStatus); //  Update UI after successful change
  };
  
  

  if (!movie) return <p className="text-center text-muted">Loading...</p>;

  return (
    <div className="container mt-5">
      <div className="row">
        {/* Movie Poster */}
        <div className="col-md-4">
          <img
            src={movie.poster || "https://via.placeholder.com/300x450?text=No+Image"}
            alt={movie.title}
            className="img-fluid rounded shadow"
          />
        </div>

        {/* Movie Details */}
        <div className="col-md-8">
          <h1>{movie.title}</h1>
          <p className="lead">{movie.description}</p>
          <p><strong>Director:</strong> {movie.director}</p>
          <p><strong>Release Year:</strong> {movie.release_year}</p>
          <p><strong>Rating:</strong> {movie.rating}/10</p>

         {/* Watchlist / Watched Buttons */}
<div className="mt-3">
  {/* Watchlist Button */}
  <button
    className={`btn me-2 ${status === "WANT_TO_WATCH" ? "btn-warning selected-glow" : "btn-outline-warning"}`}
    onClick={() => updateStatus("WATCHLIST")}
  >
    {status === "WANT_TO_WATCH" ? "✓ In Watchlist" : "Add to Watchlist"}
  </button>

  {/* Watched Button */}
  <button
    className={`btn ${status === "WATCHED" ? "btn-success selected-glow" : "btn-outline-success"}`}
    onClick={() => updateStatus("WATCHED")}
  >
    {status === "WATCHED" ? "✓ Watched" : "Mark as Watched"}
  </button>
</div>


          {/* Reviews Section */}
          <h3 className="mt-4">Reviews</h3>
          <ul className="list-group">
            {reviews.length > 0 ? (
              reviews.map((review, index) => (
                <li key={index} className="list-group-item">
                  {review.reviewText}
                </li>
              ))
            ) : (
              <li className="list-group-item text-muted">No reviews yet.</li>
            )}
          </ul>

          {/* Add a Review */}
          <div className="mt-3">
            <textarea
              className="form-control"
              value={newReview}
              onChange={(e) => setNewReview(e.target.value)}
              placeholder="Write a review..."
            />
            <button className="btn btn-primary mt-2" onClick={handleAddReview}>
              Submit Review
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

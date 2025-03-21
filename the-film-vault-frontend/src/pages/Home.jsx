import { useEffect, useState } from "react";
import { fetchMovies } from "../api/movies";
import { Link } from "react-router-dom";

export default function Home() {
  const [movies, setMovies] = useState([]);
  const [filteredMovies, setFilteredMovies] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedGenre, setSelectedGenre] = useState("");
  const [selectedYear, setSelectedYear] = useState("");

  useEffect(() => {
    fetchMovies().then((data) => {
      setMovies(data);
      setFilteredMovies(data);
    });
  }, []);

  // Extract unique genres and years
  const genres = [...new Set(movies.map((movie) => movie.genre))];
  const years = [...new Set(movies.map((movie) => movie.release_year))].sort((a, b) => b - a);

  const handleFilter = () => {
    let filtered = movies;

    if (searchQuery) {
      filtered = filtered.filter((movie) =>
        movie.title.toLowerCase().includes(searchQuery.toLowerCase())
      );
    }
    if (selectedGenre) {
      filtered = filtered.filter((movie) => movie.genre === selectedGenre);
    }
    if (selectedYear) {
      filtered = filtered.filter((movie) => movie.release_year.toString() === selectedYear);
    }

    setFilteredMovies(filtered);
  };

  // Handle input changes and apply filters
  useEffect(() => {
    handleFilter();
  }, [searchQuery, selectedGenre, selectedYear]);

  return (
    <div className="container mt-5">
      <h1 className="text-center mb-4">🎬 Movies</h1>

      {/* Filter Section */}
      <div className="row mb-4">
        {/* Search Bar */}
        <div className="col-md-4">
          <input
            type="text"
            className="form-control"
            placeholder="Search by title..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
          />
        </div>

        {/* Genre Filter */}
        <div className="col-md-4">
          <select
            className="form-select"
            value={selectedGenre}
            onChange={(e) => setSelectedGenre(e.target.value)}
          >
            <option value="">All Genres</option>
            {genres.map((genre) => (
              <option key={genre} value={genre}>
                {genre}
              </option>
            ))}
          </select>
        </div>

        {/* Year Filter */}
        <div className="col-md-4">
          <select
            className="form-select"
            value={selectedYear}
            onChange={(e) => setSelectedYear(e.target.value)}
          >
            <option value="">All Years</option>
            {years.map((year) => (
              <option key={year} value={year}>
                {year}
              </option>
            ))}
          </select>
        </div>
      </div>

      {/* Movie Cards */}
      <div className="row">
        {filteredMovies.length > 0 ? (
          filteredMovies.map((movie) => (
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
          <p className="text-center text-muted">No movies found.</p>
        )}
      </div>
    </div>
  );
}

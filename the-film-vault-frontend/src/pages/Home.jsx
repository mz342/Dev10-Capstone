import { useEffect, useState } from "react";
import { fetchMovies } from "../api/movies";
import { Link } from "react-router-dom";
import { getPosterUrl } from "../api/tmdb";
import { fetchGenres } from "../api/genres"; 




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
  useEffect(() => {
    fetchGenres().then(setAllGenres);
  }, []);
  
  const [allGenres, setAllGenres] = useState([]);
  
  const years = [...new Set(movies.map((movie) => movie.releaseYear))].sort((a, b) => b - a);


  const handleFilter = () => {
    let filtered = movies;

    if (searchQuery) {
      filtered = filtered.filter((movie) =>
        movie.title.toLowerCase().includes(searchQuery.toLowerCase())
      );
    }
    if (selectedGenre) {
        filtered = filtered.filter((movie) =>
          movie.genres?.some((g) => g.name === selectedGenre)
        );
      }
      
      if (selectedYear) {
        filtered = filtered.filter((movie) => movie.releaseYear.toString() === selectedYear);
      }
      

    setFilteredMovies(filtered);
  };

  // Handle input changes and apply filters
  useEffect(() => {
    handleFilter();
  }, [searchQuery, selectedGenre, selectedYear]);

  return (
    <div className="container mt-5">
      {/* Hero Banner */}
      <div className="bg-dark text-white text-center p-5 rounded shadow mb-4">
        <h1 className="display-4 fw-bold mb-3">🎬 The Film Vault</h1>
        <p className="lead mb-0">Discover, review, and track your favorite movies.</p>
      </div>
  
      {/* Filter Section */}
      <div className="row g-3 mb-4 align-items-end">
        {/* Search Bar */}
        <div className="col-md-4">
          <label className="form-label fw-semibold">Search</label>
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
          <label className="form-label fw-semibold">Filter by Genre</label>
          <select
            className="form-select"
            value={selectedGenre}
            onChange={(e) => setSelectedGenre(e.target.value)}
          >
            <option value="">All Genres</option>
            {allGenres.map((genre) => (
              <option key={genre.id} value={genre.name}>
                {genre.name}
              </option>
            ))}
          </select>
        </div>
  
        {/* Year Filter */}
        <div className="col-md-4">
          <label className="form-label fw-semibold">Filter by Year</label>
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
              <div className="card h-100 shadow-sm border-0">
                <img
                  src={
                    movie.poster?.startsWith("http")
                      ? movie.poster
                      : getPosterUrl(movie.poster || movie.poster_path)
                  }
                  alt={movie.title}
                  className="card-img-top"
                />
                <div className="card-body d-flex flex-column justify-content-between">
                  <h5 className="card-title">{movie.title}</h5>
                  <p className="card-text text-muted">
                    {movie.description.slice(0, 100)}...
                  </p>
                  <Link to={`/movies/${movie.id}`} className="btn btn-primary mt-auto">
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

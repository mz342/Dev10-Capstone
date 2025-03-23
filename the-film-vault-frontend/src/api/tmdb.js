// src/api/tmdb.js
const API_KEY = "adc08ebf9fd490eaf581608acec46b1b";
const BASE_URL = "https://api.themoviedb.org/3";

export async function searchMovies(query) {
  const response = await fetch(`${BASE_URL}/search/movie?api_key=${API_KEY}&query=${encodeURIComponent(query)}`);
  const data = await response.json();
  return data.results;
}

export function getPosterUrl(path) {
    return path && path.startsWith("/")
      ? `https://image.tmdb.org/t/p/w500${path}`
      : path || "https://via.placeholder.com/300x450?text=No+Image";
  }
  

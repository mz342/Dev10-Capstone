import { useState, useEffect } from "react";
import { Routes, Route } from "react-router-dom"; 
import Navbar from "./components/Navbar";
import Home from "./pages/Home";
import MovieDetails from "./pages/MovieDetails";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Watchlist from "./pages/Watchlist";
import WatchedMovies from "./pages/Watchedmovies";
import OAuthSuccess from "./pages/OAuthSuccess";

export default function App() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  }, []);

  return (
    <>
      <Navbar user={user} setUser={setUser} />
      <div className="container mt-4">
        <Routes> 
          <Route path="/" element={<Home />} />
          <Route path="/movies/:id" element={<MovieDetails />} />
          <Route path="/login" element={<Login setUser={setUser} />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/watchlist" element={<Watchlist />} />
          <Route path="/watched" element={<WatchedMovies />} />
          <Route path="/oauth-success" element={<OAuthSuccess setUser={setUser} />} />
        </Routes>
      </div>
    </>
  );
}

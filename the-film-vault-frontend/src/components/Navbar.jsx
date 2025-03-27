import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

export default function Navbar() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");

    if (storedUser) {
      setUser(JSON.parse(storedUser));
    } else {
      fetch("http://localhost:8080/api/user/me", {
        credentials: "include",
      })
        .then((res) => {
          if (res.ok) return res.json();
          throw new Error("No session user");
        })
        .then((userData) => {
          localStorage.setItem("user", JSON.stringify(userData));
          setUser(userData);
        })
        .catch(() => setUser(null));
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("user");
    setUser(null);
    navigate("/login");
    window.location.reload();
  };

  return (
    <nav className="navbar navbar-expand-lg" style={{ background: "linear-gradient(to right, #1f1f1f, #343a40)", boxShadow: "0 4px 8px rgba(0, 0, 0, 0.3)" }}>
      <div className="container-fluid">
        <Link
          className="navbar-brand fw-bold text-white"
          to="/"
          style={{ textShadow: "0 0 8px #00e1ff" }}
        >
          🎬 The Film Vault
        </Link>
        <button
          className="navbar-toggler bg-light"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto d-flex align-items-center gap-2">
            <li className="nav-item">
              <Link className="nav-link text-white" to="/">Home</Link>
            </li>
            {user && (
              <>
                <li className="nav-item">
                  <Link className="nav-link text-white" to="/watchlist">Watchlist</Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link text-white" to="/watched">Watched Movies</Link>
                </li>
              </>
            )}
            {user ? (
              <li className="nav-item">
                <button
                  className="btn btn-outline-danger rounded-pill px-3"
                  onClick={handleLogout}
                >
                  Logout
                </button>
              </li>
            ) : (
              <>
                <li className="nav-item">
                  <Link className="btn btn-outline-primary rounded-pill px-3" to="/login">
                    Login
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="btn btn-outline-success rounded-pill px-3" to="/signup">
                    Sign Up
                  </Link>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
}

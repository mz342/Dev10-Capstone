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
      //  Check if the user is logged in via Google session
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
        .catch(() => {
          // User is not logged in via Google or session expired
          setUser(null);
        });
    }
  }, []);
  

  const handleLogout = () => {
    //  Clear localStorage & Update State
    localStorage.removeItem("user");
    setUser(null); 

    //  Navigate back to login page
    navigate("/login");

    //   Force a full page reload to ensure fresh state
    window.location.reload();
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container">
        <Link className="navbar-brand" to="/">🎬 The Film Vault</Link>
        <button 
          className="navbar-toggler" 
          type="button" 
          data-bs-toggle="collapse" 
          data-bs-target="#navbarNav"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/">Home</Link>
            </li>
            {user && (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to="/watchlist">Watchlist</Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/watched">Watched Movies</Link>
                </li>
              </>
            )}
            {user ? (
              <li className="nav-item">
                <button className="btn btn-danger ms-3" onClick={handleLogout}>Logout</button>
              </li>
            ) : (
              <>
                <li className="nav-item">
                  <Link className="btn btn-primary ms-3" to="/login">Login</Link>
                </li>
                <li className="nav-item">
                  <Link className="btn btn-success ms-3" to="/signup">Sign Up</Link>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
}

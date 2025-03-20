import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Login({ setUser }) { //  Accept setUser prop
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");

    const response = await fetch("http://localhost:8080/api/user/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, passwordHash: password }),
    });

    if (response.ok) {
      const userData = await response.json();
      localStorage.setItem("user", JSON.stringify(userData)); //  Save user
      setUser(userData); //  Update Navbar
      navigate("/"); // Redirect home
    } else {
      setError("Invalid username or password.");
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="text-center">Login</h2>
      {error && <p className="text-danger text-center">{error}</p>}
      <form onSubmit={handleLogin} className="w-50 mx-auto p-4 border rounded shadow">
        <div className="mb-3">
          <label className="form-label">Username</label>
          <input
            type="text"
            className="form-control"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Password</label>
          <input
            type="password"
            className="form-control"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="btn btn-primary w-100">Login</button>
      </form>
    </div>
  );
}

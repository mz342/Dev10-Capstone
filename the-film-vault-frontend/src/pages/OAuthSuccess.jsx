import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function OAuthSuccess({ setUser }) {
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch the authenticated user info from backend
    fetch("http://localhost:8080/api/user/me", {
      credentials: "include" 
    })
      .then((res) => {
        if (!res.ok) throw new Error("Failed to fetch user.");
        return res.json();
      })
      .then((user) => {
        localStorage.setItem("user", JSON.stringify(user));
        setUser(user);
        navigate("/");
      })
      .catch((err) => {
        console.error("OAuth login error:", err);
        navigate("/login");
      });
  }, []);

  return (
    <div className="text-center mt-5">
      <h2>Signing you in...</h2>
    </div>
  );
}

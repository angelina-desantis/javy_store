import { useNavigate } from "react-router-dom";
import "AuthRequiredModal.css";

export default function AuthRequiredModal() {
    const navigate = useNavigate();

    return (
        <div className="text-center">
            <h2>Login Required</h2>
            <p>You must be logged in to access this page.</p>

            <button onClick={() => navigate("/login")}>Login</button>
            <button onClick={() => navigate("/register")}>Sign Up</button>

        </div>
    );
}
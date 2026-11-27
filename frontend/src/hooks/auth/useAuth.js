import { useContext } from "react";
import { AuthContext } from "../../context/auth/AuthContext";

/* Returns current authentication context value */
export function useAuth() {
    return useContext(AuthContext);
}
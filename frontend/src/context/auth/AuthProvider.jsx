import { useEffect, useCallback } from "react";
import { AuthContext } from "./AuthContext.jsx";
import { useLocalStorage } from "../../hooks/useLocalStorage.js";

import { login, register, logout, userProfile } from "../../api/auth/authService.js";
import authClient from "../../api/auth/authClient.js"

export default function AuthProvider({ children }) {
    const [ user, setUser ] = useLocalStorage("user", null);
    const [ token, setToken ] = useLocalStorage("token", null);
    const [ , setShoppingCart ] = useLocalStorage("shopping_cart", null);

    const isAuth = !!token;

    /* Sync authorization header */
    useEffect() => {
        if (token) {
            authClient.defaults.headers.common["Authorization"] = `Bearer $(token)`;
        } else {
            delete authClient.defaults.headers.common["Authorization"];
        }
    }

    /* Authenticates user, and stores token and user data */
    const userLogin = useCallback(async(username, password)) => {
        try {
            const result = await login(username, password);
            setToken(result.data.token);
            setUser(result.data.data);
            return { 
                success: true,
                message: result.data?.message
            };
        } catch (err) {
            return {
                success: false,
                message: err.response?.data?.detail || "Login failed."
            };
        }
    }, [setToken, setUser];

    /* Registers new user, and stores token and user data */
    const userReg = useCallback(async(username, email, password)) => {
        try {
            const result = await register(username, email, password);
            setToken(result.data.token);
            setUser(result.data.data);            
            return { 
                success: true,
                message: result.data?.message
             };
        } catch (err) {
            success: false,
            message: err.response?.data?.detail || "Registration failed."
        }
    }, [setToken, setUser];

    /* Logs out current user, and removes token and user data */
        const userLogout = useCallback(async () => {
        try {
            const res = await logout(token);
            setToken(null);
            setUser(null);
            setLibrary(null);
            return { 
                success: true, 
                message: res.data?.message };
        } catch (err) {
            return { 
                success: false, 
                message: err.response?.data?.detail };
        }
    }, [token, setToken, setUser, setShoppingCart]);

    /* Restore session from reload */
    useEffect(() => {
        async function restoreSession() {
            if (!token) {
                setUser(null);
                return;
            }

            try {
                const res = await userProfile();
                setUser(res.data.data);
            } catch (err) {
                console.log(err);
                setToken(null);
                setUser(null);
                setLibrary(null);
            }
        }

        restoreSession();
    }, [token, setToken, setUser, setShoppingCart]);

    return (
        <AuthContext.Provider
            value={{
                user,
                token,
                isAuthenticated,
                login,
                register,
                logout,
                setUser
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}
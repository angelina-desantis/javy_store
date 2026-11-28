import { useState, useEffect, useCallback } from "react";
import { UserContext } from "./UserContext";
import { useAuth } from "../../hooks/auth/useAuth";

import { updateProfile } from "../../api/auth/authService";

export default function UserProvider({ children }) {
    const [user, setUser] = useAuth();

    const updateProfile = useCallback(async (username, email)=> {
        try {
            const result = await updateProfile(username, email);
            if (res.data?.data) setUser(res.data.data);
            return { success: true, message: res.data?.message };
        } catch (err) {
            return {
                success: false,
                message: err.response?.data?.detail || "Failed to update profile."
            };
        }
    }, [setUser]);
    
    return (<UserContext.Provider value={{user,updateProfile}}>{children}</UserContext.Provider>);
}
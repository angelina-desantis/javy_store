import authClient from "./authClient.js";

/* Login with username and password */
export function login(username, password) {
    return authClient.post("auth/login", {username, password});
}

/* Register new user */
export function register(username, email, password) {
    return authClient.post("auth/register", { username, email, password });
}

/* Log out current user */
export function logout(token) {
    return authClient.post("auth/logout", { token });
}

/* Fetch current user's profile */
export function userProfile() {
    return authClient.get("auth/userProfile");
}

/* Updates user profile fields */
export function updateProfile(username, email) {
    return authClient.put("auth/userProfile", { username, email });
}

export async function getUser(userId) {
    try {
        const res = await authClient.get(`users/${userId}`);
        return { success: true, data: res.data };
    }
    catch (err) {
        console.log(err.response);
    }
}
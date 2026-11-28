import { useCallback, useEffect, useState } from "react";
import { CollectionContext } from "./CollectionContext";
import { useAuth } from "../../hooks/auth/useAuth";
import wishlistClient from "../../api/wishlist/wishlistClient";
import { getWishlist, addEntry, removeEntry } from "../../api/wishlist/wishlistService";

export default function WishlistProvider({ children }) {
    const { token } = useAuth();
    const [ wishlist, setWishlist ] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const getWishlist = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const res = await getWishlist();
            setWishlist(res.data);
            return res.data;
        } catch (err) {
            setError(err.response?.data?.detail || "Failed to load wishlist");
            return null;
        } finally {
            setLoading(false);
        }
    }, []);
    
    const addEntry = async (collectionId, gameId) => {
        try {
            const res = await addBookToCollection(collectionId, gameId);
            return res.data;
        } catch (err) {
            setError(err.response?.data?.detail || "Failed to add game to wishlist");
            return null;
        }
    };
    const removeEntry = async (collectionId, gameId) => {
        try {
            const res = await removeBookFromCollection(collectionId, bookId);
            return res.data;
        } catch (err) {
            setError(err.response?.data?.detail || "Failed to remove game from wishlist");
            return null;
        }
    };

    useEffect(() => {
        if (!token) {
            setWishlist([]);
            setError(null);
            return;
        }

        wishlistClient.defaults.headers.common["Authorization"] = `Bearer ${token}`;
        getWishlist();
    }, [token, getWishlist]);

    return (
        <CollectionContext.Provider value={{ loading, error, setError, getWishlist, addEntry, removeEntry }}>
            {children}
        </CollectionContext.Provider>
    );
}
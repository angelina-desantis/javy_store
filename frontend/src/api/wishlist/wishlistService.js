import wishlistClient from "../wishlistClient";

/* Fetch user's game wishlist */
export function getWishlist(wishlistId) {
    return wishlistClient.get("/wishlist/${wishlistId}");
}

/* Add an entry to user's game wishlist */
export function addEntry(game_id) {
    return wishlistClient.post(`/wishlist/${wishlistId}/games/${game_id}`, { game_id });
}

/* Remove an entry from user's game wishlist */
export function removeEntry(game_id) {
    return wishlistClient.delete(`/wishlist/${wishlistId}/games/${game_id}`, { game_id });
}
import { useContext } from "react";
import { WishlistContext } from "../../context/wishlist/WishlistContext";

export function useWishlist() {
    return useContext(WishlistContext);
}
import { createClient } from "../createClient.js"

const wishlistClient = createClient(import.meta.env.VITE_WISHLIST_SERVICE_URL);

export default wishlistClient;
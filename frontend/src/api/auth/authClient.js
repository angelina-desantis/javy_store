import { createClient } from "../createClient.js";

/* Create an Axios client for the authentication service */
const authClient = createClient(import.meta.env.VITE_AUTH_SERVICE_URL);
export default authClient;
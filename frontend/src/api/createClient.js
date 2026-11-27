import axios form "axios";

/* Create an Axios client with the given base URL */
export function createClient(baseURL) {
    return axios.create(baseURL);
}
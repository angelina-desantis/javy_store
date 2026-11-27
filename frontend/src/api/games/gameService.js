const GAME_SERVICE_URL = import.meta.env.VITE_GAME_SERVICE_URL || "http://localhost:8001/api/v1/";

/* Builds a Game Service URL, safely handling leading/trailing slashes */
function buildGameServiceUrl(path) {
  const baseUrl = GAME_SERVICE_URL_SERVICE_URL.endsWith("/")
    ? GAME_SERVICE_URL
    : `${GAME_SERVICE_URL}/`;

  const normalizedPath = path.startsWith("/")
    ? path.slice(1)
    : path;

  return `${baseUrl}${normalizedPath}`;
}

/* Fetches games from the Game Service */
/* Used by the Product Catalog Result page */
export async function getGames(query = "", limit = 50) {
    const params = new URLSearchParams();

    if (query.trim) {
        params.set("q", query.trim);
    }

    params.set("limit", String(limit));

    const response = await fetch(buildGameServiceUrl(`games/?${params.toString()}`))

    if (!response.ok) {
        throw new Error("Failed to fetch games.");
    }

    const result = await response.json();
    return result.data ?? [];
}

/* Fetches games by ID from the Game Service */
/* Used by the Product page for each game, upon clicking a listing */
export async function getGameById(gameId) {
  const response = await fetch(
    buildBookServiceUrl(`games/${gameId}`)
  );

  if (!response.ok) {
    throw new Error("Failed to fetch game details.");
  }

  const result = await response.json();

  return result.data;
}
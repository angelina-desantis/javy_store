import { useState, useEffect, useRef } from "react";
import { getGames } from "../../api/games/gameService";

const SEARCH_DEBOUNCE_MS = 250;
const GAME_SEARCH_LIMIT = 50;

/* Checks whether a game matches the selected genre filter */
function matchesSelectedGenre(game, selectedGenre) {
    if (selectedGenre == "all") {
        return true;
    }

    /* Game Service format: Genre is an array of strings */
    if (Array.isArray(game.genre)) {
        return game.genre.some((gameGenre) => String(gameGenre).toLowerCase() === String(selectedGenre).toLowerCase());
    }

    /* Fallback: Genre is a single string */
    return String(gameGenre).toLowerCase() === String(selectedGenre).toLowerCase();
}

export function gameSearch(query, genre) {
    const [ result, setResult ] = useState([]);
    const [ loading, SetLoading] = useState(false);
    const [ error, setError ] = useState("");
    const timer = useRef(null);

    useEffect() => {
        clearTimeout(timer.current);
        setLoading(true);
        setError("");

        timer.current = setTimeout(async () => {
            try {
                const games = await getGames(query, GAME_SEARCH_LIMIT);
                const filteredGames = games.filter((game) => matchesSelectedGenre(game, genre));
                
                setResult(filteredGames);
            } catch (err) {
                setResult([]);
                setError(err instanceof Error ? err.message : "Failed to load games.");
            } finally {
                setLoading(false);
            }
        }, SEARCH_DEBOUNCE_MS);

        return () => clearTimeout(timer.current);
    }, [query, genre];

    return { result, loading, error };
}
import { useState, useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { gameSearch } from "../../hooks/games/gameSearch";
import SearchBar from "../../components/search/SearchBar";
import SearchResult from "../../components/search/SearchResult";

import "Catalog.css";

export function Catalog() {
    const [searchParams] = useSearchParams();
    const initialQuery = searchParams.get("query") || "";

    const [query, setQuery] = useState(initialQuery);
    const { results, loading, error } = useBookSearch(query, "all");

    useEffect(() => {
        setQuery(initialQuery);
    }, [initialQuery]);

    /* Display results from game search */
    return ();

}
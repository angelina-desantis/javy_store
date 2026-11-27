import { useState, useEffect } from "react";

export function useLocalStorage(key, initialValue = null) {
    // Initialize state from localStorage (with error handling)
    const [value, setValue] = useState(() => {
        try {
            const item = localStorage.getItem(key);

            // If nothing stored, use the initial value
            if (item === null) {
                return initialValue;
            }

            // Attempt to parse stored JSON
            return JSON.parse(item);
        }
        catch {
            // If parsing fails, fall back to initial value
            return initialValue;
        }
    });
     useEffect(() => {
        // If value is null, remove the key entirely
        if (value === null) {
            localStorage.removeItem(key);
        }
        else {
            // Otherwise, store the updated value
            localStorage.setItem(key, JSON.stringify(value));
        }

    }, [value, key]);

    // Return state and setter (same API as useState)
    return [value, setValue];
}
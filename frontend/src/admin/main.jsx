import React from "react";
import ReactDOM from "react-dom/client";
import { useState } from "react";

import "../index.css";
import "./AdminLayout.css";

import AdminSidebar from "../components/admin/AdminSidebar";
import AdminGames from "../pages/admin/games/AdminGames";
import AddGame from "../pages/admin/add-game/AddGame";

function AdminApp() {
  const [page, setPage] = useState("games");
  const [games, setGames] = useState([
  { name: "Elden Ring", platform: "PC / PS5", price: 79.99, stock: 30 },
  { name: "Minecraft", platform: "PC", price: 39.99, stock: 45 },
  { name: "Cyberpunk 2077", platform: "PC / PS5", price: 59.99, stock: 26 },
  { name: "Hollow Knight", platform: "Switch", price: 19.99, stock: 0 }
]);
function addGame(newGame) {
  setGames([...games, newGame]);
  setPage("games");
}
  return (
    <div className="admin-layout">
      <AdminSidebar />

      {page === "games" ? (
        <AdminGames games={games} onAddGame={() => setPage("add")}/>
      ) : (
        <AddGame onBack={() => setPage("games")} onSave={addGame}/>
      )}
    </div>
  );
}

ReactDOM.createRoot(document.getElementById("root")).render(
  <AdminApp />
);
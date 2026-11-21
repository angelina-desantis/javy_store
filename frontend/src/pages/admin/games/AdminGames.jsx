
import {useState} from "react";
import "./AdminGames.css";

function AdminGames({games,onAddGame}){
  const [search,setSearch]=useState("");
  const [platform,setPlatform]=useState("All");

  const filteredGames=games.filter((game)=>{
    return game.name.toLowerCase().includes(search.toLowerCase()) &&
      (platform==="All" || game.platform.includes(platform));
  });

  const inStock=games.filter((game)=>game.stock>0).length;

  return (
    <main className="admin-games">
      <div className="games-header">
        <div>
          <h1>Game Inventory</h1>
          <p>Manage your games, prices and stock.</p>
        </div>
        <button className="add-game-btn" onClick={onAddGame}>
          + Add Game
        </button>
      </div>

      <div className="games-stats">
        <div className="games-stat-card">
          <p>Total Games</p>
          <h2>{games.length}</h2>
        </div>
        <div className="games-stat-card">
          <p>In Stock</p>
          <h2>{inStock}</h2>
        </div>
        <div className="games-stat-card">
          <p>Out of Stock</p>
          <h2>{games.length-inStock}</h2>
        </div>
      </div>

      <div className="games-table-box">
        <h2>All Games</h2>

        <div className="games-filters">
          <input
            type="text"
            placeholder="Search games..."
            value={search}
            onChange={(e)=>setSearch(e.target.value)}
          />

          <select value={platform} onChange={(e)=>setPlatform(e.target.value)}>
            <option value="All">All Platforms</option>
            <option value="PC">PC</option>
            <option value="PS5">PS5</option>
            <option value="Xbox">Xbox</option>
            <option value="Switch">Switch</option>
          </select>
        </div>

        <table className="games-table">
          <thead>
            <tr>
              <th>Game</th>
              <th>Platform</th>
              <th>Price</th>
              <th>Stock</th>
              <th>Action</th>
            </tr>
          </thead>

          <tbody>
            {filteredGames.map((game)=>(
              <tr key={game.name}>
                <td>{game.name}</td>
                <td>{game.platform}</td>
                <td>${game.price.toFixed(2)}</td>
                <td className={game.stock===0 ? "out-stock" : "in-stock"}>
                  {game.stock}
                </td>
                <td className="edit-game">Edit</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </main>
  );
}

export default AdminGames;

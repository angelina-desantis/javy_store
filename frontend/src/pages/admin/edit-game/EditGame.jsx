import {useState} from "react";
import "./EditGame.css";
function EditGame({game,onBack,onSave}){
  const [name,setName]=useState(game.name);
  const [platform,setPlatform]=useState(game.platform);
  const [genre,setGenre]=useState(game.genre || "");
  const [price,setPrice]=useState(game.price);
  const [stock,setStock]=useState(game.stock);
  const [description,setDescription]=useState(game.description || "");

  function handleSave(){
    if(!name.trim() || !platform.trim() || price==="" || stock===""){
      alert("Please fill in the required fields.");
      return;
    }

    if(Number(price)<0 || Number(stock)<0 || !Number.isInteger(Number(stock))){
      alert("Please enter a valid price and stock quantity.");
      return;
    }

    onSave({
      ...game,
      name:name.trim(),
      platform,
      genre,
      price:Number(price),
      stock:Number(stock),
      description
    });
  }

  return (
    <main className="edit-game-page">
      <div className="edit-game-header">
        <div>
          <h1>Edit Game</h1>
          <p>Update game information and stock.</p>
        </div>
        <button type="button" onClick={onBack}>← Back</button>
      </div>

      <div className="edit-game-form">
        <h2>Game Information</h2>

        <label>Game Title</label>
        <input
          type="text"
          value={name}
          onChange={(e)=>setName(e.target.value)}
        />

        <label>Platform</label>
        <input
          type="text"
          value={platform}
          onChange={(e)=>setPlatform(e.target.value)}
        />

        <label>Genre</label>
        <select value={genre} onChange={(e)=>setGenre(e.target.value)}>
          <option value="">Select Genre</option>
          <option value="Action">Action</option>
          <option value="Adventure">Adventure</option>
          <option value="RPG">RPG</option>
          <option value="Strategy">Strategy</option>
          <option value="Sports">Sports</option>
        </select>

        <label>Price ($)</label>
        <input
          type="number"
          min="0"
          step="0.01"
          value={price}
          onChange={(e)=>setPrice(e.target.value)}
        />

        <label>Stock Quantity</label>
        <input
          type="number"
          min="0"
          step="1"
          value={stock}
          onChange={(e)=>setStock(e.target.value)}
        />

        <label>Description</label>
        <textarea
          rows="5"
          value={description}
          onChange={(e)=>setDescription(e.target.value)}
        />

        <div className="edit-game-buttons">
          <button type="button" onClick={onBack}>Cancel</button>
          <button type="button" onClick={handleSave}>Save Changes</button>
        </div>
      </div>
    </main>
  );
}

export default EditGame;

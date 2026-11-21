
import {useState} from "react";
import "./AddGame.css";

function AddGame({onBack,onSave}){
  const [name,setName]=useState("");
  const [platform,setPlatform]=useState("");
  const [genre,setGenre]=useState("");
  const [price,setPrice]=useState("");
  const [stock,setStock]=useState("");
  const [description,setDescription]=useState("");

  function handleSave(){
    if(!name.trim() || !platform || price==="" || stock===""){
      alert("Please fill in the required fields.");
      return;
    }

    if(Number(price)<0 || Number(stock)<0 || !Number.isInteger(Number(stock))){
      alert("Please enter a valid price and stock quantity.");
      return;
    }

    onSave({
      name:name.trim(),
      platform,
      genre,
      price:Number(price),
      stock:Number(stock),
      description
    });
  }

  return (
    <main className="add-game">
      <div className="add-game-header">
        <div>
          <h1>Add New Game</h1>
          <p>Enter game details to add to your store.</p>
        </div>
        <button type="button" onClick={onBack}>← Back</button>
      </div>

      <div className="add-game-form">
        <h2>Game Information</h2>

        <label>Game Title</label>
        <input
          type="text"
          placeholder="Enter game title"
          value={name}
          onChange={(e)=>setName(e.target.value)}
        />

        <label>Platform</label>
        <select value={platform} onChange={(e)=>setPlatform(e.target.value)}>
          <option value="">Select Platform</option>
          <option value="PC">PC</option>
          <option value="PS5">PS5</option>
          <option value="Xbox">Xbox</option>
          <option value="Switch">Switch</option>
        </select>

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
          placeholder="0.00"
          min="0"
          step="0.01"
          value={price}
          onChange={(e)=>setPrice(e.target.value)}
        />

        <label>Stock Quantity</label>
        <input
          type="number"
          placeholder="0"
          min="0"
          step="1"
          value={stock}
          onChange={(e)=>setStock(e.target.value)}
        />

        <label>Description</label>
        <textarea
          placeholder="Enter game description"
          rows="5"
          value={description}
          onChange={(e)=>setDescription(e.target.value)}
        />

        <label>Game Cover</label>
        <input type="file" accept="image/png, image/jpeg" />

        <div className="add-game-buttons">
          <button type="button" onClick={onBack}>Cancel</button>
          <button type="button" onClick={handleSave}>Save Game</button>
        </div>
      </div>
    </main>
  );
}

export default AddGame;

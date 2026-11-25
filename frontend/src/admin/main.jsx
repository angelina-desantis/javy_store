import React from "react";
import ReactDOM from "react-dom/client";
import { useState } from "react";
import "../index.css";
import "./AdminLayout.css";
import AdminDashboard from "../pages/admin/dashboard/AdminDashboard";
import AdminCustomers from "../pages/admin/customers/AdminCustomers";
import AdminSidebar from "../components/admin/AdminSidebar";
import AdminGames from "../pages/admin/games/AdminGames";
import AddGame from "../pages/admin/add-game/AddGame";
import EditGame from "../pages/admin/edit-game/EditGame";
import CustomerDetails from "../pages/admin/customers/CustomerDetails";
import AdminSales from "../pages/admin/sales/AdminSales";

function AdminApp() {
  const [page, setPage] = useState("games");
  const [selectedGame,setSelectedGame]=useState(null);
  const [selectedCustomer,setSelectedCustomer]=useState(null);
  const [customers,setCustomers]=useState([
  {id:1,name:"Eric Chen",email:"eric@example.com",orders:5},
  {id:2,name:"Sarah Lee",email:"sarah@example.com",orders:3},
  {id:3,name:"Alex Wong",email:"alex@example.com",orders:2}]);
  const [games, setGames] = useState([
  { name: "Elden Ring", platform: "PC / PS5", price: 79.99, stock: 30 },
  { name: "Minecraft", platform: "PC", price: 39.99, stock: 45 },
  { name: "Cyberpunk 2077", platform: "PC / PS5", price: 59.99, stock: 26 },
  { name: "Hollow Knight", platform: "Switch", price: 19.99, stock: 0 }
]);
function viewCustomer(customer){
  setSelectedCustomer(customer);
  setPage("customer-details");
}
function editGame(game){
  setSelectedGame(game);
  setPage("edit");
}
function addGame(newGame) {
  setGames([...games, newGame]);
  setPage("games");
}

function updateGame(updatedGame){
  const newGames=[];
  for(let i=0;i<games.length;i++){
    if(games[i]===selectedGame){
      newGames.push(updatedGame);
    }
    else{
      newGames.push(games[i]);
    }
  }
  setGames(newGames);
  setPage("games");
}
function updateCustomer(updatedCustomer){
  const newCustomers=[];

  for(let i=0;i<customers.length;i++){
    if(customers[i].id===updatedCustomer.id){
      newCustomers.push(updatedCustomer);
    }
    else{
      newCustomers.push(customers[i]);
    }
  }

  setCustomers(newCustomers);
  setSelectedCustomer(updatedCustomer);
}
  return (
    <div className="admin-layout">
    <AdminSidebar page={page} setPage={setPage}/>

      
    {page==="games" && (<AdminGames games={games} onAddGame={()=>setPage("add")} onEditGame={editGame}/>)}

    {page==="add" && (<AddGame onBack={()=>setPage("games")} onSave={addGame}/>)}

    {page==="edit" && ( <EditGame game={selectedGame} onBack={()=>setPage("games")} onSave={updateGame}/>)}

    {page==="customers" && (<AdminCustomers customers={customers} onViewCustomer={viewCustomer}/>)}
    {page==="customer-details" && (<CustomerDetails customer={selectedCustomer} onBack={()=>setPage("customers")} onSave={updateCustomer}/>)}
    {page==="sales" && (<AdminSales/>)}
    {page==="dashboard" && (<AdminDashboard games={games} customers={customers}/>
)}
    </div>
  );
}

ReactDOM.createRoot(document.getElementById("root")).render(
  <AdminApp />
);

import {useState} from "react";
import "./AdminSales.css";

const orders=[
  {id:1001,customer:"Eric Chen",game:"Elden Ring",quantity:1,price:79.99,date:"2026-11-10",status:"Completed"},
  {id:1002,customer:"Eric Chen",game:"Minecraft",quantity:1,price:39.99,date:"2026-11-12",status:"Completed"},
  {id:1003,customer:"Sarah Lee",game:"Cyberpunk 2077",quantity:2,price:59.99,date:"2026-11-15",status:"Completed"},
  {id:1004,customer:"Alex Wong",game:"Hollow Knight",quantity:1,price:19.99,date:"2026-11-18",status:"Completed"}
];

function AdminSales(){
  const [search,setSearch]=useState("");
  const [date,setDate]=useState("");

  const filtered=orders.filter((order)=>{
    const matchSearch=order.customer.toLowerCase().includes(search.toLowerCase()) ||
      order.game.toLowerCase().includes(search.toLowerCase());

    const matchDate=date==="" || order.date===date;

    return matchSearch && matchDate;
  });

  return (
    <main className="admin-sales">
      <div className="sales-header">
        <h1>Sales</h1>
        <p>View sales history and customer orders.</p>
      </div>

      <div className="sales-table-box">
        <h2>All Orders</h2>

        <div className="sales-filters">
          <input
            type="text"
            placeholder="Search customer or game..."
            value={search}
            onChange={(e)=>setSearch(e.target.value)}
          />

          <input
            type="date"
            value={date}
            onChange={(e)=>setDate(e.target.value)}
          />

          <button type="button" onClick={()=>setDate("")}>
            Clear Date
          </button>
        </div>

        <table className="sales-table">
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Customer</th>
              <th>Game</th>
              <th>Quantity</th>
              <th>Unit Price</th>
              <th>Total</th>
              <th>Date</th>
              <th>Status</th>
            </tr>
          </thead>

          <tbody>
            {filtered.map((order)=>(
              <tr key={order.id}>
                <td>#{order.id}</td>
                <td>{order.customer}</td>
                <td>{order.game}</td>
                <td>{order.quantity}</td>
                <td>${order.price.toFixed(2)}</td>
                <td>${(order.price*order.quantity).toFixed(2)}</td>
                <td>{order.date}</td>
                <td>{order.status}</td>
              </tr>
            ))}
          </tbody>
        </table>

        {filtered.length===0 && (
          <p>No orders found.</p>
        )}
      </div>
    </main>
  );
}

export default AdminSales;

import "./AdminCustomers.css";
import "./CustomerDetails.css";
import {useState} from "react";
function CustomerDetails({customer,onBack,onSave}){
  const [editing,setEditing]=useState(false);
  const [name,setName]=useState(customer.name);
  const [email,setEmail]=useState(customer.email);
  const orders=[{id:1001,customerId:1,date:"Nov 10, 2026",total:79.99,status:"Completed"},
  {id:1002,customerId:1,date:"Nov 12, 2026",total:39.99,status:"Completed"},
  {id:1003,customerId:2,date:"Nov 15, 2026",total:59.99,status:"Completed"},
  {id:1004,customerId:3,date:"Nov 18, 2026",total:19.99,status:"Completed"}];

    const history=orders.filter((order)=>order.customerId===customer.id);

    function handleSave(){
    if(!name.trim() || !email.trim()){
     alert("Please fill in all fields.");
    return;
  }onSave({...customer,name:name.trim(), email:email.trim()});
  setEditing(false);
}
  return (
    <main className="customer-details">
    <div className="customers-header">
        <h1>Customer Details</h1>
        <p>View customer information and purchase history.</p>
        <button type="button" onClick={onBack}>← Back</button>
    </div>

    <div className="customer-info">
    <h2>Customer Information</h2>
        {editing ? (<div className="customer-edit">
    <label>Name</label>
    <input type="text" value={name} onChange={(e)=>setName(e.target.value)}/>
    <label>Email</label>
    <input type="email" value={email} onChange={(e)=>setEmail(e.target.value)}/>
    <div className="customer-edit-buttons">

     <button type="button" onClick={()=>{ setName(customer.name);setEmail(customer.email); setEditing(false);}}>
          Cancel
    </button>

    <button type="button" onClick={handleSave}>
          Save Changes
    </button>
    </div>
</div>):(<div>
      <p>Name: {customer.name}</p>
      <p>Email: {customer.email}</p>
      <p>Customer ID: {customer.id}</p>
      <p>Total Orders: {customer.orders}</p>

      <button type="button" className="customer-edit-btn" onClick={()=>setEditing(true)} >
        Edit Customer
      </button>
    </div>)}
</div>

      <div className="customer-history">
        <h2>Purchase History</h2>

        <table className="customers-table">
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Date</th>
              <th>Total</th>
              <th>Status</th>
            </tr>
          </thead>

        <tbody>
            {history.map((order)=>(
            <tr key={order.id}>
            <td>#{order.id}</td>
            <td>{order.date}</td>
            <td>${order.total.toFixed(2)}</td>
            <td>{order.status}</td>
            </tr>))}
        </tbody>
        </table>
      </div>
    </main>
  );
}

export default CustomerDetails;
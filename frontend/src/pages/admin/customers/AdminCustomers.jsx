import {useState} from "react";
import "./AdminCustomers.css";
const customers=[
  {id:1,name:"Eric Chen",email:"eric@example.com",orders:5},
  {id:2,name:"Sarah Lee",email:"sarah@example.com",orders:3},
  {id:3,name:"Alex Wong",email:"alex@example.com",orders:2}
];

function AdminCustomers({customers,onViewCustomer}){
  const [search,setSearch]=useState("");

  const filtered=customers.filter((customer)=>{
    return customer.name.toLowerCase().includes(search.toLowerCase()) ||
      customer.email.toLowerCase().includes(search.toLowerCase());
  });

  return (
    <main className="admin-customers">
      <div className="customers-header">
        <h1>Customers</h1>
        <p>Manage customer accounts and view purchase history.</p>
      </div>

      <div className="customers-table-box">
        <h2>All Customers</h2>

        <input
          type="text"
          placeholder="Search customers..."
          value={search}
          onChange={(e)=>setSearch(e.target.value)}
        />

        <table className="customers-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Orders</th>
              <th>Action</th>
            </tr>
          </thead>

          <tbody>
            {filtered.map((customer)=>(
              <tr key={customer.id}>
                <td>{customer.name}</td>
                <td>{customer.email}</td>
                <td>{customer.orders}</td>
                <td>
                  <button type="button" className="view-customer" onClick={()=>onViewCustomer(customer)}>
                    View
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </main>
  );
}

export default AdminCustomers;
import "./AdminDashboard.css";
function AdminDashboard({games,customers}){
  const orders=[
    {id:1001,customer:"Eric Chen",total:79.99},
    {id:1002,customer:"Eric Chen",total:39.99},
    {id:1003,customer:"Sarah Lee",total:119.98},
    {id:1004,customer:"Alex Wong",total:19.99}
  ];

  let revenue=0;

  for(let i=0;i<orders.length;i++){
    revenue=revenue+orders[i].total;
  }

  return (
    <main className="admin-dashboard">
      <div className="dashboard-header">
        <h1>Dashboard</h1>
        <p>Overview of your store.</p>
      </div>

      <div className="dashboard-stats">
        <div className="dashboard-card">
          <p>Total Games</p>
          <h2>{games.length}</h2>
        </div>

        <div className="dashboard-card">
          <p>Total Customers</p>
          <h2>{customers.length}</h2>
        </div>

        <div className="dashboard-card">
          <p>Total Orders</p>
          <h2>{orders.length}</h2>
        </div>

        <div className="dashboard-card">
          <p>Total Revenue</p>
          <h2>${revenue.toFixed(2)}</h2>
        </div>
      </div>

      <div className="dashboard-orders">
        <h2>Recent Orders</h2>

        <table className="dashboard-table">
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Customer</th>
              <th>Total</th>
            </tr>
          </thead>

          <tbody>
            {orders.slice().reverse().map((order)=>(
              <tr key={order.id}>
                <td>#{order.id}</td>
                <td>{order.customer}</td>
                <td>${order.total.toFixed(2)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </main>
  );
}

export default AdminDashboard;
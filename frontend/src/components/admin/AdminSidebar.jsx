import "./AdminSidebar.css";

function AdminSidebar() {
  return (
    <aside className="admin-sidebar">
      <h2 className="admin-logo">Javy Store</h2>

      <nav className="admin-nav">
        <a href="#dashboard">Dashboard</a>
        <a href="#games" className="active">Games</a>
        <a href="#customers">Customers</a>
        <a href="#sales">Sales</a>
      </nav>
    </aside>
  );
}

export default AdminSidebar;
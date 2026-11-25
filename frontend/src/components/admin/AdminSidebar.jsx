import "./AdminSidebar.css";

function AdminSidebar({page,setPage}){
  return (
    <aside className="admin-sidebar">
      <h2 className="admin-logo">Javy Store</h2>

      <nav className="admin-nav">
        <button type="button" className={page==="dashboard" ? "active" : ""} onClick={()=>setPage("dashboard")}>
          Dashboard
        </button>

        <button type="button" className={page==="games" || page==="add" || page==="edit" ? "active" : ""} onClick={()=>setPage("games")}>
          Games
        </button>

        <button type="button" className={page==="customers" ? "active" : ""} onClick={()=>setPage("customers")}>
          Customers
        </button>

        <button type="button" onClick={()=>setPage("sales")}>
          Sales
        </button>
      </nav>
    </aside>
  );
}

export default AdminSidebar;
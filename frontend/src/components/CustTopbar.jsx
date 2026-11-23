import "./CustTopbar.css";

function CustTopbar() {
    return (
        <aside className="cust-topbar">
            <h2 className="page-logo">Javy Store</h2>

            <nav className="page-nav">
                <a href="#catalog">Catalog</a>
                <a href="#shopping-cart">Shopping Cart</a>
                <a href="#login">Login/Register</a>
            </nav>
        </aside>
    );
}

export default CustTopbar;
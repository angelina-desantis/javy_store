import "./CustTopbar.css";

function AuthCustTopbar() {
    return (
        <aside className="cust-topbar">
            <h2 className="page-logo">Javy Store</h2>

            <nav className="page-nav">
                <a href="#catalog">Catalog</a>
                <a href="#shopping-cart">Shopping Cart</a>
                <a href="#user-profile">Profile</a>
                <a href="#home">Logout</a>
            </nav>
        </aside>
    );
}

export default AuthCustTopbar;
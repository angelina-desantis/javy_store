import "./CustTopbar.css";

function CustTopbar() {

    const [page, setPage] = useState("home");
    const [auth, setAuth] = useState("none");

    /* Change page to product catalog page */
    function catalog() {
        setPage("catalog");
    }

    /* Change page to shopping cart page */
    function shopping_cart() {
        setPage("shopping cart");
    }

    /* Change page to login page */
    function login() {
        setPage("login");
    }

    /* Change page to user profile */
    /* Should only be visible if user is logged in */
    function profile() {
        setPage("profile");
    }
    
    /* Check if the user is logged in; change top bar depending on login state */
    return (

        {auth === "auth" ? (
            <aside className="cust-topbar">
                <h2 className="page-logo">Javy Store</h2>

                <nav className="page-nav">
                    <a href="#catalog">Catalog</a>
                    <a href="#shopping-cart">Shopping Cart</a>
                    <a href="#login">Login/Register</a>
                </nav>
            </aside>

        ) : (
            <aside className="cust-topbar">
                <h2 className="page-logo">Javy Store</h2>

                <nav className="page-nav">
                    <a href="#catalog">Catalog</a>
                    <a href="#shopping-cart">Shopping Cart</a>
                    <a href="#profile">Profile</a>
                    <a href="#home">Logout</a>
                </nav>
            </aside>
        )}


    );
}

export default CustTopbar;
import React from 'react';
import ReactDOM from 'react-dom/client';
import { useState } from "react";

import './index.css';
import "./UserLayout.css";
import "HomePage.css";

import CustTopbar from "../components/CustTopbar";
import AuthCustTopbar from "../components/AuthCustTopbar";

function HomePage() {
  const [auth, setAuth] = useState("none")

  /* Change page to product catalog page */
  function catalog() {

  }

  /* Change page to shopping cart page */
  function shopping_cart() {

  }

  /* Change page to login page */
  function login() {

  }

  /* Check if the user is logged in; change the top bar and the welcome message depending on login state */
  return (
    <div className="cust-layout">
      {auth === "auth" ? (
        <AuthCustTopbar />
            <div className="text-center">
                <h1> Welcome back. 
                You can continue searching through our catalog, or view your shopping cart.
                </h1>
            </div>
      ) : (
        <CustTopbar />
            <div className="text-center">
                <h1> Welcome to Javy! The most popular games, one shop. 
                You can get started by searching our catalog!
                </h1>
            </div>
      )
      }
    </div>
  );
}

ReactDOM.createRoot(document.getElementById("root")).render(<CustApp />);
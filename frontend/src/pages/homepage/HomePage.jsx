import React from 'react';
import ReactDOM from 'react-dom/client';
import { useState } from "react";

import "./index.css";
import "./UserLayout.css";
import "HomePage.css";

import CustTopbar from "../components/topbar/CustTopbar";

function HomePage() {
  
  /* Check if the user is logged in; change the welcome message depending on login state */
  return (
    <div className="cust-layout">
      <CustTopbar />      
      {auth === "auth" ? (       
        <div className="text-center">
            <h1> Welcome back. 
            You can continue searching through our catalog, or view your shopping cart.
            </h1>
        </div>
      ) : (
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
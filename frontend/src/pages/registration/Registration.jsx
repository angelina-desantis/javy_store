import React from 'react';
import ReactDOM from 'react-dom/client';
import { useState } from "react";

import "./index.css";
import "./UserLayout.css";
import "Checkout.css";

import CustTopbar from "../components/topbar/CustTopbar";

function Registration() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");

    function processForm() {
        /* Check if at least one field is empty */
        if (username === "" || password === "") {
            alert("One or more fields are missing. Please try again.");
            return;
        }

        if (!email.toLower().matches("[a-z0-9]*\@{1}[a-z]*\.[a-z]{2,3}")) {
            alert("Email address is in the incorrect format.");
            return;
        }
    }

    return (
        <main className="login">
            <CustTopbar />
                <div className="form">
                    <label>Username</label>
                    <input type="text" value={username}/>
                    
                    <label>Password</label>
                    <input type="password" value={password}/>

                    <label>First Name</label>
                    <input type="text" value={firstName}/>
                    
                    <label>Last Name</label>
                    <input type="text" value={lastName}/>

                    <label>Email Address</label>
                    <input type="text" value={email}/>

                    <button type="button" onClick={processForm}>Next</button>
                </div>
        </main>
    )
}
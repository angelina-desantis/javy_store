import React from 'react';
import ReactDOM from 'react-dom/client';
import { useState } from "react";

import "./index.css";
import "./UserLayout.css";
import "Login.css";

import CustTopbar from "../components/CustTopbar";

function Login(user_list) {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [auth, setAuth] = useState(false);

    function processForm() {
        /* Check if at least one field is empty */
        if (username === "" || password === "") {
            alert("One or more fields are missing. Please try again.");
            setAuth(false);
            return;
        }

        /* Query database and determine if the login is valid */
        for () {
            if () {
                setAuth(true);
                return;
            }
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

                    <button type="button" onClick={processForm}>Login</button>
                    
                    <a href="#registration">Don't have an account? Register now!</a>
                </div>
        </main>
    )
}

export default Login;
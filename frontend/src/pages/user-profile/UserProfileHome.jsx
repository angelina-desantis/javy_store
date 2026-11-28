import React from 'react';
import ReactDOM from 'react-dom/client';
import { useState } from "react";

import "./index.css";
import "./UserLayout.css";
import "UserProfile.css";

import CustTopbar from "../components/topbar/CustTopbar";

function UserProfileHome() {

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [username, setUsername ] = useState("");
    const [email, setEmail] = useState("");

    function processForm() {
        if (firstName === "" || lastName === "" || username === "" 
            || email === "") {
            alert("One or more fields are missing. Please try again.");
            return;
            }
        if (!email.toLower().matches("[a-z0-9]*\@{1}[a-z]*\.[a-z]{2,3}")) {
            alert("Email address is in the incorrect format.");
            return;
        }

        validateUserInformation({
            firstName: firstName.trim(),
            lastName: lastName.trim(),
            username: username.trim(),
            email: email.trim()
        });
    }

    /* TODO: Retrieve user info and auto-fill fields */
    /* TODO: If user info is validated, send an update request */
    return (
        <main className="profile">
            <CustTopbar />
                <div className="form">
                    <label>First Name</label>
                    <input type="text" value={firstName}/>
                        
                    <label>Last Name</label>
                    <input type="text" value={lastName}/>

                    <label>Username</label>
                    <input type="text" value={username}/>

                    <label>Email Address</label>
                    <input type="text" value={email}/>

                    <button type="button" onClick={processForm}>Update</button>                

                </div>
        </main>
    )
}

export default UserProfileHome;
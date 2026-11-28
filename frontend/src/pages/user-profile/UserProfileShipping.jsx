import React from 'react';
import ReactDOM from 'react-dom/client';
import { useState } from "react";

import "./index.css";
import "./UserLayout.css";
import "UserProfile.css";

import AuthCustTopbar from "../components/topbar/AuthCustTopbar";

function UserProfileShipping() {

    const [address, setAddress] = useState("");
    const [city, setCity] = useState("");
    const [province, setProvince] = useState("");
    const [country, setCountry] = useState("");
    const [postalCode, setPostalCode] = useState("");

    function processForm() {
        if (address === "" || city === "" || province === ""
            || country === "" || postalCode === "") {
            alert("One or more fields are missing. Please try again.");
            return;
        }

        if (!postalCode.matches("[A-Z][0-9][A-Z] [0-9][A-Z][0-9]")) {
            alert("The postal code is in the incorrect format. (E.g. A1A 1A1)");
            return;
        }

        validateShipping({
            address:address.trim(),
            city:city.trim()
            province:province.trim(),
            country:country.trim(),
            postalCode
        });
    }

    /* TODO: If shipping information is validated, send an update request  */
    return (
        <main className="checkout">
            <CustTopbar />
            <div className="form">
                <label>Street Address</label>
                <input type="text" value={address}/>
                    
                <label>City</label>
                <input type="password" value={city}/>

                <label>Province</label>
                <input type="text" value={province}/>

                <label>Country</label>
                <input type="text" value={country}/>
                    
                <label>Postal Code</label>
                <input type="text" value={postalCode}/>

                <button type="button" onClick={processForm}>Update</button>                
            </div>
        </main>
    )
}

export default UserProfileShipping;
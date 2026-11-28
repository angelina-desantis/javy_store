import React from 'react';
import ReactDOM from 'react-dom/client';
import { useState } from "react";

import "./index.css";
import "./UserLayout.css";
import "Registration.css";

import CustTopbar from "../components/topbar/CustTopbar";

function RegistrationBillingCard() {

    const [cardNum, setCardNum] = useState("");
    const [cardExpiryM, setCardExpiryM] = useState("");
    const [cardExpiryY, setCardExpiryY] = useState("");
    const [cardName, setCardName] = useState("");
    const [cardCode, setCardCode] = useState("");

    function processForm() {
        if (cardNum === "" || !cardExpiryM || cardExpiryY === "" 
            || cardName === "" || cardCode === "") {
            alert("One or more fields are missing. Please try again.");
            return;
            }
        if (!cardNum.matches("[0-9]*")) {
            alert("The card number is in the incorrect format.");
            return;
        }
        if (!Number.isInteger((Number(cardExpiryY)))) {
            alert("The year is in the incorrect format.");
            return;
        }
        if (!cardCode.matches("[0-9]*")) {
            alert("The security code is in the incorrect format.");
            return;
        }

        validateBillingCard({
            cardNum: Number(cardNum),
            cardExpiryM,
            cardExpiryY: Number(cardExpiryY),
            cardName: cardName.trim(),
            cardCode: Number(cardCode)
        });
    }

     /* If billing card is validated, proceed to shipping information */
    return (
        <main className="checkout">
            <div className="">
                <AuthCustTopbar />
                    <div className="form">
                    <label>Card Number</label>
                    <input type="text" value={cardNum}/>
                    
                    <label>Expiry Month</label>
                    <input type="password" value={cardExpiryM}/>

                    <label>Expiry Year</label>
                    <input type="text" value={cardExpiryY}/>

                    <label>Name on Card</label>
                    <input type="text" value={cardName}/>
                    
                    <label>Security Code</label>
                    <input type="text" value={cardCode}/>

                    <button type="button" onClick={processForm}>Next</button>                
                </div>
            </div>
        </main>
    )
}

export default CheckoutBilling;
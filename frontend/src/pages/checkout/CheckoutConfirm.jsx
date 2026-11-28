import React from 'react';
import ReactDOM from 'react-dom/client';
import { useState } from "react";

import "./index.css";
import "./UserLayout.css";
import "Checkout.css";

import AuthCustTopbar from "../components/topbar/AuthCustTopbar";

function CheckoutConfirm() {

    const [paymentState, setPaymentState] = useState(true);
    const [checkout, setCheckout] = useState(true);

    /* Dummy function for confirming or denying checkout */
    function checkCheckoutSuccess() {
        if (paymentState) {
            setCheckout(true);
            setPaymentState(false);
        }
        else if () {    /* If not enough stock to fulfill order */
            setCheckout(false);
        }
        else {
            setCheckout(false);
            setPaymentState(true);
        }
    }

    /*  If order can be completed, display order summary as a scrollable list, and calculate total
        Otherwise, display error message
    */
    return (
        <main className="checkout">
            <div className="">
                <CustTopbar />
                {checkout === true ? (
                    /* Todo: Display each game listing and total of order */

                ) : (
                    <div className="text-center">
                        <h1> The order could not be completed. Please try again.
                        </h1>
                    </div>
                    <button type="button" onclick="#shopping-cart">Return to shopping cart</button>
                )}
            </div>
        </main>
    )
}

export default CheckoutConfirm;
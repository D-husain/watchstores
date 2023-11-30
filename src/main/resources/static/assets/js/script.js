function openJsCheckoutPopup(orderId, txnToken, amount)
 {
    // console.log(orderId, txnToken, amount);
    var config = {
        "root": "",
        "flow": "DEFAULT",
        "data": {
            "orderId": orderId,
            "token": txnToken,
            "tokenType": "TXN_TOKEN",
            "amount": amount
        },
        "handler": {
        "notifyMerchant": function(eventName,data){
            console.log("notifyMerchant handler function called");
            console.log("eventName => ",eventName);
            console.log("data => ",data);
            location.reload();
        }
        }
    };
     if(window.Paytm && window.Paytm.CheckoutJS){
            // initialze configuration using init method
            window.Paytm.CheckoutJS.init(config).then(function onSuccess() {
                // after successfully updating configuration, invoke checkoutjs
                window.Paytm.CheckoutJS.invoke();
                document.getElementById('someFormId').submit();
            }).catch(function onError(error){
                console.log("error => ",error);
            });
    }
}
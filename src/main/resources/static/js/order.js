var $ = jQuery;

$(document).ready(function($) {
    //checking changed data
    $("#payment").change(function () {
        if ($(this).val() === "online") {
            $(".change").addClass("change-hide");
            $(".order-btn[type='submit']").hide();
            $(".pay-online").show();
        }
        else {
            $(".change").removeClass("change-hide");
            $(".pay-online").hide();
            $(".order-btn[type='submit']").show();
        }
    })

    if($("#payment").val() === "online"){
        $(".order-btn[type='submit']").hide();
    }else{
        $(".pay-online").hide();
    }

    $("input[name='fullName']").change(function () {
        let regex = new RegExp("^[a-zA-Z а-яА-ЯІіЇїЄє]+$");
        if (!regex.test($(this).val())){
            $(".error-name").show();
            $(".order-btn").prop("disabled", true);
        }
        else {
            $(".error-name").hide();
            $(".order-btn").prop("disabled", false);
        }
    })

    $("input[name='email']").change(function () {
        let regex = new RegExp("^([\\w-\.]+@([\\w-]+\\.)+[\\w-]{2,4})?$");
        if (!regex.test($(this).val())){
            $(".error-email").show();
            $(".order-btn").prop("disabled", true);
        }
        else {
            $(".error-email").hide();
            $(".order-btn").prop("disabled", false);
        }
    })

    $("input[name='phoneNumber']").change(function () {
        let regex = new RegExp("^(\\+?\\d{1,3})?\\d{9}$");
        if (!regex.test($(this).val())){
            $(".error-phone").show();
            $(".order-btn").prop("disabled", true);
        }
        else {
            $(".error-phone").hide();
            $(".order-btn").prop("disabled", false);
        }
    })

    $("input[name='street']").change(function () {
        let regex = new RegExp("^[a-zA-Z а-яА-ЯІіЇїЄє0-9]+$");
        if (!regex.test($(this).val())){
            $(".error-street").show();
            $(".order-btn").prop("disabled", true);
        }
        else {
            $(".error-street").hide();
            $(".order-btn").prop("disabled", false);
        }
    })

    $("input[name='house']").change(function () {
        let regex = new RegExp("^[a-zA-Z а-яА-ЯІіЇїЄє0-9]+$");
        if (!regex.test($(this).val())){
            $(".error-house").show();
            $(".order-btn").prop("disabled", true);
        }
        else {
            $(".error-house").hide();
            $(".order-btn").prop("disabled", false);
        }
    })

    $("input[name='entrance']").change(function () {
        let regex = new RegExp("^\\d*$");
        if (!regex.test($(this).val())){
            $(".error-entrance").show();
            $(".order-btn").prop("disabled", true);
        }
        else {
            $(".error-entrance").hide();
            $(".order-btn").prop("disabled", false);
        }
    })

    $("input[name='apartment']").change(function () {
        let regex = new RegExp("^([\\d]+[a-zA-Z а-яА-ЯІіЇїЄє]?)?$");
        if (!regex.test($(this).val())){
            $(".error-flat").show();
            $(".order-btn").prop("disabled", true);
        }
        else {
            $(".error-flat").hide();
            $(".order-btn").prop("disabled", false);
        }
    })
})
//GOOGLE PAY API
const baseRequest = {
    apiVersion: 2,
    apiVersionMinor: 0
};

const allowedCardNetworks = ["AMEX", "DISCOVER", "INTERAC", "JCB", "MASTERCARD", "VISA"];

const allowedCardAuthMethods = ["PAN_ONLY", "CRYPTOGRAM_3DS"];

const tokenizationSpecification = {
    type: 'PAYMENT_GATEWAY',
    parameters: {
        'gateway': 'example',
        'gatewayMerchantId': 'exampleGatewayMerchantId'
    }
};

const baseCardPaymentMethod = {
    type: 'CARD',
    parameters: {
        allowedAuthMethods: allowedCardAuthMethods,
        allowedCardNetworks: allowedCardNetworks
    }
};

const cardPaymentMethod = Object.assign(
    {},
    baseCardPaymentMethod,
    {
        tokenizationSpecification: tokenizationSpecification
    }
);

let paymentsClient = null;

function getGoogleIsReadyToPayRequest() {
    return Object.assign(
        {},
        baseRequest,
        {
            allowedPaymentMethods: [baseCardPaymentMethod]
        }
    );
}

function getGooglePaymentDataRequest() {
    const paymentDataRequest = Object.assign({}, baseRequest);
    paymentDataRequest.allowedPaymentMethods = [cardPaymentMethod];
    paymentDataRequest.transactionInfo = getGoogleTransactionInfo();
    paymentDataRequest.merchantInfo = {
        merchantName: 'Example Merchant'
    };

    paymentDataRequest.callbackIntents = ["PAYMENT_AUTHORIZATION"];

    return paymentDataRequest;
}

function getGooglePaymentsClient() {
    if ( paymentsClient === null ) {
        paymentsClient = new google.payments.api.PaymentsClient({
            environment: 'TEST',
            paymentDataCallbacks: {
                onPaymentAuthorized: onPaymentAuthorized
            }
        });
    }
    return paymentsClient;
}

function onPaymentAuthorized(paymentData) {
    return new Promise(function(resolve, reject){
        // handle the response
        processPayment(paymentData)
            .then(function() {
                resolve({transactionState: 'SUCCESS'});
                $(".order-btn").prop("disabled", false);
                $(".order-btn[type='submit']").click();
            })
            .catch(function() {
                resolve({
                    transactionState: 'ERROR',
                    error: {
                        intent: 'PAYMENT_AUTHORIZATION',
                        message: 'Insufficient funds, try again. Next attempt should work.',
                        reason: 'PAYMENT_DATA_INVALID'
                    }
                });
            });
    });
}


$(".pay-online").click(function () {
    let valid = true;
    $("input:required").each(function () {
        if ($(this).val() === undefined || $(this).val().length === 0)
            valid = false;
    })
    if (valid) {
        onGooglePaymentButtonClicked();
        $(".error-required").hide();
    }
    else
        $(".error-required").show();
})

function getGoogleTransactionInfo() {
    return {
        countryCode: 'UA',
        currencyCode: "UAH",
        totalPriceStatus: "FINAL",
        totalPrice: "" + $("#payment").data("price"),
        totalPriceLabel: "Total"
    };
}


function onGooglePaymentButtonClicked() {
    const paymentDataRequest = getGooglePaymentDataRequest();
    paymentDataRequest.transactionInfo = getGoogleTransactionInfo();

    const paymentsClient = getGooglePaymentsClient();
    paymentsClient.loadPaymentData(paymentDataRequest);
}

function processPayment(paymentData) {
    return new Promise(function(resolve, reject) {
        setTimeout(function() {
            paymentToken = paymentData.paymentMethodData.tokenizationData.token;


            resolve({});
        }, 500);
    });
}
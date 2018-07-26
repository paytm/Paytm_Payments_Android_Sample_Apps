# 1. Gradle Dependency: 



Add the below dependency in app module build.gradle file.


#### implementation 'com.paytm.intentupi:paytmintentupi:1.1.2'

#### implementation 'com.android.support:design:27.1.1' (if not already added in gradle)


# 2. How to initiate SDK:

* Obtain the PaytmIntentUpiSdk instance from it’s builder class PaytmIntentUpiSdkBuilder with parameterised constructor:

* Parameters of PaytmIntentUpiSdkBuilder  constructor are:

#### -> vpa: Payee VPA.

#### -> vpaName: Payee Name

#### -> merchantCode: Payee merchant code (0000 in case of individual)

#### -> transactionRefId: This could be Order number, Subscription number, Bill id, Booking id, Insurance reference, etc. This field is mandatory for all merchant transactions.

#### -> amount: Amount of transaction.

#### -> SetPaytmUpiSdkListener: Listener to get callbacks from SDK.


# 3. Other optional Builder method can be used:

#### -> setPspGeneratedId: This must be PSP generated id when present. In case of merchant payments, merchant may acquire the txn id form his PSP.

#### -> setTransactionNote: Transaction note providing a short description of the transaction.

#### -> setMinimumAmount: Minimum amount to be paid if different from transaction amount

#### -> setCurrency: Currency of amount. Currently supporting only INR.

#### -> setTxnRefUrl: This should be a URL when clicked provides customer with further transaction details like complete bill details, bill copy, order copy, ticket details, etc. This can also be used to deliever digital goods such as mp3 files etc. after payment. This URL, when used, MUST BE related to the particular transaction and MUST NOT be used to send unsolicited information that are not relevant to the transaction.

#### -> setGenericDeepLink: This method is used to make UPI deeplink. If this method is used then all other builder methods will be ignored.


* Call the startTransaction() method to proceed to payment flow, and wait for the results in callback.


# Below is the Code Snippet:

PaytmIntentUpiSdk paytmIntentUpiSdk = new PaytmIntentUpiSdk.PaytmIntentUpiSdkBuilder(vpa, vpaName, merchantCode, orderid, amount,this).setPspGeneratedId(pspid).setTransactionNote(notes).setMinimumAmount(minAmount).setCurrency(currency).setTxnRefUrl(txnUrl).setGenericDeepLink(deepLink).build();

paytmIntentSdk.startTransaction(context);


# 4. SetPaytmUpiSdkListener: Below are the methods to implement:

#### a. onTransactionComplete : Start Polling for transaction success of failure when this method is called. Calling this method doesn’t mean transaction has been completed

#### b. onError: Calls when any error occurred in validation of input parameters.



# Below is the Code Snippet: 

@Override

public void onTransactionComplete() {

    Toast.makeText(MainActivity.this, "Start Polling", Toast.LENGTH_SHORT).show();

}



@Override

public void onError(PaytmResponseCode paytmResponseCode, String errorMessage) {

Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

}



# 5. Example: Find the attached Demo app with integration of SDK.










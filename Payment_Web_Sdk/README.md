# Android Configurations

1. Obtain the following
	* PGSDK provided in the Android plugin. 
	* Client side certificate (Optional: Refer “Client Certificate and Encrypted password” section given below).
	* Password for the Client side certificate. (Optional: Refer “Client Certificate and Encrypted password” section given below).

2. Add Client side certificate file inside raw folder. If raw folder is not available, then create a raw folder within “res” folder.

3. Add the INTERNET and ACCESS_NETWORK_STATE permissions to your AndroidManifest.xml. These two permissions are required for PGSDK Service to function.
	
	* AndroidManifest.xml
	
		#### <uses¬permission android:name="android.permission.INTERNET"/>
		#### <uses¬permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

4. Add the Declaration of PaytmPGActivity in your AndroidManifest.xml. This activity is available in the PGSDK. Make sure that the manifest file also specifies this activity element.
	
	* AndroidManifest.xml
	
		#### <activity android:name="com.paytm.pgsdk.PaytmPGActivity" android:screenOrientation="portrait" android:configChanges="keyboardHidden|orientation|keyboard"/>

5. For going Live, please add the following to the ProGuard file:

	* AndroidManifest.xml

		#### -keepclassmembers class com.paytm.pgsdk.PaytmWebView$PaytmJavaScriptInterface {
		####   public *;
		#### }

	* Once you have completed the above configuration setup, then you can start using PG Service APIs.

# Steps for Integrating SDK Payment Transaction

1. Obtain the PaytmPGService instance. You can call either the Staging service or the Production service depending on your requirement. 
PaytmPGService.getStagingService() will return the Service Object pointing to Staging Environment. 
PaytmPGService.getProductionService() will return the Service Object pointing to Production Environment.

2. Create a HASHMAP Object that includes the order details. Syntax for creating PaytmOrder Object is as follows:
* ANDROID

	#### Map<String, String> paramMap = new HashMap<String,String>();
	#### paramMap.put( "MID" , "PAYTM_MERCHANT_ID");
	#### paramMap.put( "ORDER_ID" , "ORDER0000000001");
	#### paramMap.put( "CUST_ID" , "10000988111");
	#### paramMap.put( "INDUSTRY_TYPE_ID" , "PAYTM_INDUSTRY_TYPE_ID");
	#### paramMap.put( "CHANNEL_ID" , "WAP");
	#### paramMap.put( "TXN_AMOUNT" , "1");
	#### paramMap.put( "WEBSITE" , "PAYTM_WEBSITE");
	#### paramMap.put( "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=ORDER0000000001");
	#### paramMap.put( "EMAIL" , "abc@gmail.com");
	#### paramMap.put( "MOBILE_NO" , "9999999999");
	#### paramMap.put( "CHECKSUMHASH" , "w2QDRMgp1234567JEAPCIOmNgQvsi+BhpqijfM9KvFfRiPmGSt3Ddzw+oTaGCLneJwxFFq5mqTMwJXdQE2EzK4px2xruDqKZjHupz9yXev4=")

	* where, paramMap is the map containing request parameter names and their respective values.

3. (Optional) Create PaytmClientCertificate object that contains the certificate information. Syntax for creating PaytmClientCertificate object is as follows
* ANDROID

	#### PaytmClientCertificate Certificate = new PaytmClientCertificate(String inPassword, String inFileName);

	* inPassword is the client side certificate password 
	* inFileName is the client side certificate file name. This file must be present in “raw” folder.

4. Create Paytm Order Object
* ANDROID
	
	#### PaytmOrder Order = new PaytmOrder(paramMap);

5. Call the initialize() method to set PaytmOrder and PaytmClientCertificate Objects. Method Signature is as follows:
* ANDROID

	#### void com.paytm.pgsdk.PaytmPGService.initialize(PaytmOrder inOrder, PaytmClientCertificate inCertificate);
	* where,
		*inOrder is the object which contains order details.
		* inCertificate is the object which has certificate information. Pass this as null if no client certificate is used by the merchant (as given above in the Prerequisites section) or null

6. Call startPaymentTransaction() method to start the Payment Transaction. Method Signature is as follows:
* ANDROID

	#### void com.paytm.pgsdk.PaytmPGService.startPaymentTransaction(Context inCtxt, boolean inbHideHeader, boolean inbSendAllChecksumResponseParametersToPGServer, PaytmPaymentTransactionCallback inPaymentTransactionCallback);
	* where,
		* inCtxt is the activity context in which this method is called.
		* inbHideHeader is a boolean variable used to hide or show header bar.
		* inbSendAllChecksumResponseParametersToPGServer is a boolean variable to determine whether to send all checksum response parameters to PG server or not.
		* inPaymentTransactionCallback is a PaytmPaymentTransactionCallback instance to send callback messages back to merchant application.

7. Implement the callback methods to handle the response upon payment completion 
Callback methods are under PaytmPaymentTransactionCallback
ANDROID
	
	#### public void someUIErrorOccurred(String inErrorMessage) { /*If any UI Erroe Occur*/ }
	#### public void onTransactionResponse(Bundle inResponse) { /*once transaction is completed on paytm you get complete response in json format*/ }
	#### public void networkNotAvailable() {   /* If network is not available, then this method gets called. */ }
	#### public void clientAuthenticationFailed(String inErrorMessage) { /* This method gets called if client
	#### authentication  failed. // Failure may be due to following reasons 1. Server error or downtime.  Error
	#### message describes the reason for failure.*/  }
	#### public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) { }
	#### public void onBackPressedCancelTransaction() {/*on back press event this callback got triggered */}
	#### public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {/*on transaction cancelled this callback got triggered */ }

	# Example
	 
	* Following is a sample implementation of the APIs.
	* Android
		
		#### //Getting the Service Instance. PaytmPGService.getStagingService() will return //the Service pointing to staging environment.
		#### //and PaytmPGService.getProductionService() will return the Service pointing to //production environment.
		#### PaytmPGService Service = null;
		#### Service = PaytmPGService.getStagingService();
		#### or
		#### Service = PaytmPGService.getProductionService();
		#### //Create new order Object having all order information.
		#### Map<String, String> paramMap = new HashMap<String,String>();
		#### paramMap.put( "MID" , "PAYTM_MERCHANT_ID");
		#### paramMap.put( "ORDER_ID" , "ORDER0000000001");
		#### paramMap.put( "CUST_ID" , "10000988111");
		#### paramMap.put( "INDUSTRY_TYPE_ID" , "PAYTM_INDUSTRY_TYPE_ID");
		#### paramMap.put( "CHANNEL_ID" , "WAP");
		#### paramMap.put( "TXN_AMOUNT" , "1");
		#### paramMap.put( "WEBSITE" , "PAYTM_WEBSITE");
		#### paramMap.put( "CALLBACK_URL" , "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=ORDER0000000001");
		#### paramMap.put( "EMAIL" , "abc@gmail.com");
		#### paramMap.put( "MOBILE_NO" , "9999999999");
		#### paramMap.put( "CHECKSUMHASH" , "w2QDRMgp1234567JEAPCIOmNgQvsi+BhpqijfM9KvFfRiPmGSt3Ddzw+oTaGCLneJwxFFq5mqTMwJXdQE2EzK4px2xruDqKZjHupz9yXev4=");
		#### //Create Client Certificate object holding the information related to Client Certificate. Filename must be without .p12 extension.
		#### //For example, if suppose client.p12 is stored in raw folder, then filename must be client.
		#### PaytmClientCertificate Certificate = new PaytmClientCertificate ("password" , "filename" );
		#### //Set PaytmOrder and PaytmClientCertificate Object. Call this method and set both objects before starting transaction.
		#### Service.initialize(Order, Certificate);
		#### //OR
		#### Service.initialize(Order, null);
		#### //Start the Payment Transaction. Before starting the transaction ensure that initialize method is called.
		
		Service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
			@Override
			
			public void someUIErrorOccurred(String inErrorMessage) {
			
				Log.d("LOG", "UI Error Occur.");
			
				Toast.makeText(getApplicationContext(), " UI Error Occur. ", Toast.LENGTH_LONG).show();
			
			}
			
			@Override
			
			public void onTransactionResponse(Bundle inResponse) {
			
				Log.d("LOG", "Payment Transaction : " + inResponse);
			
				Toast.makeText(getApplicationContext(), "Payment Transaction response "+inResponse.toString(), Toast.LENGTH_LONG).show();
			
			}
			
			@Override
			
			public void networkNotAvailable() {
			
				Log.d("LOG", "UI Error Occur.");
			
				Toast.makeText(getApplicationContext(), " UI Error Occur. ", Toast.LENGTH_LONG).show();
			
			}
			
			@Override
			
			public void clientAuthenticationFailed(String inErrorMessage) {
			
				Log.d("LOG", "UI Error Occur.");
			
				Toast.makeText(getApplicationContext(), " Severside Error "+ inErrorMessage, Toast.LENGTH_LONG).show();
			
			}
			
			@Override
			
				public void onErrorLoadingWebPage(int iniErrorCode,
			
				String inErrorMessage, String inFailingUrl) {
			
			}
			
			@Override
			
			public void onBackPressedCancelTransaction() {
			
				// TODO Auto-generated method stub
			
			}
			
			@Override
			
			public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
			
				Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
			
				Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
			
			}
		
		});

# New Feature
#### Web redirect merchant Android SDK with otp reading ability
1. To access this feature upgrade to latest sdk via following gradle dependency in build.gradle file:
	compile('com.paytm:pgplussdk:1.2.3') {

		transitive = true;
	
	}

2. To access OTP reading feature one must have SMS permission:
	
	<uses¬permission android:name="android.permission.READ_SMS"/>
	
	<uses¬permission android:name="android.permission.RECEIVE_SMS"/>
	
	if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
	
	    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
	    
	}

# Paytm_Android_App_Kit

# SDK Documentation
http://paywithpaytm.com/developer/paytm_sdk_doc/

# SDK work flow
http://paywithpaytm.com/developer/paytm_sdk_doc?target=how-paytm-sdk-works

# Android Integration Flow
http://paywithpaytm.com/developer/paytm_sdk_doc?target=android-configurations

# Checksum Utilities

# PHP
https://github.com/Paytm-Payments/Paytm_App_Checksum_Kit_PHP

# Java
https://github.com/Paytm-Payments/Paytm_App_Checksum_Kit_JAVA

# Python
https://github.com/Paytm-Payments/Paytm_App_Checksum_Kit_Python

# Ruby
https://github.com/Paytm-Payments/Paytm_App_Checksum_Kit_Ruby

# NodeJs
https://github.com/Paytm-Payments/Paytm_App_Checksum_Kit_NodeJs

# .Net
https://github.com/Paytm-Payments/Paytm_App_Checksum_Kit_DotNet

# Transaction Status API
http://paywithpaytm.com/developer/paytm_api_doc?target=txn-status-api

Important Note :

We are not supporting PgSdk jar from now onwards. Kindly use the below mentioned Gradle dependency for the same.

Gradle Dependency:

Command : compile 'com.paytm:pgplussdk:1.1.5'

Note:

1. If you have the Jar file please Remove the jar file and jar dependency from the project.

2. Kindly change the import statements accordingly.

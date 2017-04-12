package com.example.merchantapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This is the sample app which will make use of the PG SDK. This activity will
 * show the usage of Paytm PG SDK API's.
 **/

public class MerchantActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchantapp);
		//initOrderId();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}
	
	//This is to refresh the order id: Only for the Sample App's purpose.
	@Override
	protected void onStart(){
		super.onStart();
		//initOrderId();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}
	

	/*private void initOrderId() {
		Random r = new Random(System.currentTimeMillis());
		String orderId = "ORDER" + (1 + r.nextInt(2)) * 10000
				+ r.nextInt(10000);
		EditText orderIdEditText = (EditText) findViewById(R.id.order_id);
		orderIdEditText.setText(orderId);
	}*/

	public void onStartTransaction(View view) {
		PaytmPGService Service = PaytmPGService.getStagingService();


		//Kindly create complete Map and checksum on your server side and then put it here in paramMap.

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("MID" , "WorldP64425807474247");
		paramMap.put("ORDER_ID" , "TestMerchant000111007");
		paramMap.put("CUST_ID" , "mohit.aggarwal@paytm.com");
		paramMap.put("INDUSTRY_TYPE_ID" , "Retail");
		paramMap.put("CHANNEL_ID" , "WAP");
		paramMap.put("TXN_AMOUNT" , "1");
		paramMap.put("WEBSITE" , "worldpressplg");
		paramMap.put("CALLBACK_URL" , "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp");
		paramMap.put("CHECKSUMHASH" , "w2QDRMgp1/BNdEnJEAPCIOmNgQvsi+BhpqijfM9KvFfRiPmGSt3Ddzw+oTaGCLneJwxFFq5mqTMwJXdQE2EzK4px2xruDqKZjHupz9yXev4=");
		PaytmOrder Order = new PaytmOrder(paramMap);


		Service.initialize(Order, null);

		Service.startPaymentTransaction(this, true, true,
				new PaytmPaymentTransactionCallback() {

					@Override
					public void someUIErrorOccurred(String inErrorMessage) {
						// Some UI Error Occurred in Payment Gateway Activity.
						// // This may be due to initialization of views in
						// Payment Gateway Activity or may be due to //
						// initialization of webview. // Error Message details
						// the error occurred.
					}

					@Override
					public void onTransactionResponse(Bundle inResponse) {
						Log.d("LOG", "Payment Transaction : " + inResponse);
						Toast.makeText(getApplicationContext(), "Payment Transaction response "+inResponse.toString(), Toast.LENGTH_LONG).show();
					}

					@Override
					public void networkNotAvailable() {
						// If network is not
						// available, then this
						// method gets called.
					}

					@Override
					public void clientAuthenticationFailed(String inErrorMessage) {
						// This method gets called if client authentication
						// failed. // Failure may be due to following reasons //
						// 1. Server error or downtime. // 2. Server unable to
						// generate checksum or checksum response is not in
						// proper format. // 3. Server failed to authenticate
						// that client. That is value of payt_STATUS is 2. //
						// Error Message describes the reason for failure.
					}

					@Override
					public void onErrorLoadingWebPage(int iniErrorCode,
							String inErrorMessage, String inFailingUrl) {

					}

					// had to be added: NOTE
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
	}
}

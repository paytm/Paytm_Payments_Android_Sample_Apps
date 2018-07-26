package com.paytm.intentupidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.intentupi.PaytmIntentUpiSdk;
import com.paytm.intentupi.callbacks.PaytmResponseCode;
import com.paytm.intentupi.callbacks.SetPaytmUpiSdkListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startTransactionClicked(View view) {
        try {
            String vpa = ((TextView) findViewById(R.id.edtPayeeVpa)).getText().toString();
            String vpaName = ((TextView) findViewById(R.id.edtPayeeName)).getText().toString();
            String merchantCode = ((TextView) findViewById(R.id.edtMerchantCode)).getText().toString();
            String orderid = ((TextView) findViewById(R.id.edtOrderId)).getText().toString();
            String amount = ((TextView) findViewById(R.id.edtAmount)).getText().toString();
            String pspid = ((TextView) findViewById(R.id.edtPspId)).getText().toString();
            String notes = ((TextView) findViewById(R.id.edtNotes)).getText().toString();
            String minAmount = ((TextView) findViewById(R.id.edtMinAmount)).getText().toString();
            String currency = ((TextView) findViewById(R.id.edtCurrency)).getText().toString();
            String txnUrl = ((TextView) findViewById(R.id.edtRefId)).getText().toString();
            String deepLink = ((TextView) findViewById(R.id.edtDeepLink)).getText().toString();

            PaytmIntentUpiSdk paytmIntentUpiSdk = new PaytmIntentUpiSdk.PaytmIntentUpiSdkBuilder(vpa, vpaName, merchantCode, orderid, amount, new SetPaytmUpiSdkListener() {
                @Override
                public void onTransactionComplete() {
                    Toast.makeText(MainActivity.this, "Start Polling", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(PaytmResponseCode paytmResponseCode, String errorMessage) {
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }).setPspGeneratedId(pspid).setTransactionNote(notes).setMinimumAmount(minAmount).setCurrency(currency).setTxnRefUrl(txnUrl).setGenericDeepLink(deepLink).build();



            paytmIntentUpiSdk.startTransaction(MainActivity.this);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}

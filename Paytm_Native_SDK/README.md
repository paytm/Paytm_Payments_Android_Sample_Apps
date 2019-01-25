# Paytm Native SDK Integration


**Step 1a:** Add the below line to ‘repositories’ section of your project level build.gradle file
  
        maven {
            url "https://artifactory.paytm.in/libs-release-local"
        }
	
	
After making these changes your project level gradle will look like this.

![alt text](https://github.com/muditsen/ReadMe/blob/master/images/image1.png)

**Step 1b:** Add below line to ‘dependencies’ section of your app build.gradle

```
 api 'com.paytm.nativesdk:nativesdk:x.x.x
```
 Note: Ignore any warning for now.

 Note: x.x.x must be replaced by the latest version of the SDK as mentioned in release notes. Some older versions are removed, please use `2.5.17` or contact us if you want an older version for testing.

After making these changes your app level gradle will look like this.

![alt text](https://github.com/muditsen/ReadMe/blob/master/images/image2.png)

**Step 3:** In your begin transaction page create PaytmSDK object using PaytmSDK params like
```
PaytmSDK paySDK = new PaytmSDK(boolean isAppInvoke, 
                    Context context, 
                    String transactionJson, 
                    PaytmSDKCallbackListener paytmSDKCallbacksListener,
                    double amount, 
                    String _mid, 
                    String _orderId,
                    String _merchantName, 
                    int merchantLogoDrawable, 
                    Server _server);
```
    Params: 

    1) boolean isAppInVoke default:false; 
    	Should be passed true if want to use AppInvoke feature of the application.
    
    2) Context context: Activity context from where you are beginning the transaction.

    3) String transactionJson: Api Response of Paytm's transaction api's json response.

    4) double amount: Transaction Amount.

    5) String mid: YOUR MID

    6) String orderID:  Generated Order Id

    7) String merchantName:  Merchant name

    8) int merchantLogoDrawable : Resource Id of merchant logo.

    9) Server _server: Defining the value of which server you want to point.

 ***Optional***
 
**Step 4:** Call these methods before initiating SDK

1) If you want enable Paytm Postpaid and Paytm Payments Bank transactions, call this method with required parameters.
```
public void setBankParams(String _rsaKey, 
                              String _authentication, 
                              String _clientId, 
                              String _mobileNumber, 
                              String _ssoToken) 
```


**Step 5:** To initiate transaction call method startTransaction using paySDK instance.
```
paySDK.startTransaction();
```



## Issues
1) If you get `ActionBarActivity class not found exception`, Please create package `android.support.v7.app` in your `java` folder and create a new file `ActionBarActivity` extending `AppCompatActivity` in created package.

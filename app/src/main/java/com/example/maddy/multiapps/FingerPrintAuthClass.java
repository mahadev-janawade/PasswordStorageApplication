package com.example.maddy.multiapps;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Maddy on 4/4/2020.
 */

@TargetApi(Build.VERSION_CODES.M)
public class FingerPrintAuthClass extends FingerprintManager.AuthenticationCallback {

    private Context context;
    public boolean value;

    public FingerPrintAuthClass(Context context) {
        this.context = context;
    }

    public void authenticateUser(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        //super.onAuthenticationError(errorCode, errString);
        this.result("Error: "+errString,false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        //super.onAuthenticationSucceeded(result);
        this.result("Successfully autheticated user",true);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        //super.onAuthenticationHelp(helpCode, helpString);
        this.result("Help: " + helpString,false);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        this.result("Failed to Authenticate. Retry.. ",false);
    }

    public  void result(String resultString,boolean status){

            if(status == false){
                ((TextView)((Activity)context).findViewById(R.id.fingerprintStatus)).setText(resultString);
                ((TextView)((Activity)context).findViewById(R.id.fingerprintStatus)).setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
                value = false;
            }else{
                ((TextView)((Activity)context).findViewById(R.id.fingerprintStatus)).setText(resultString);
                ((TextView)((Activity)context).findViewById(R.id.fingerprintStatus)).setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                value = true;
            }
    }

}


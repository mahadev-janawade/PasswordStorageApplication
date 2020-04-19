package com.example.maddy.multiapps;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Maddy on 4/4/2020.
 */

public class FingerprintAuth extends AppCompatActivity {

    FingerprintManager fingerprintManager;
    KeyguardManager keyguardManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprintauth);

        getSupportActionBar().hide();

        // check if device version is Marshmello and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            //check if fingerprint hardware is available in device
            if (!fingerprintManager.isHardwareDetected()) {
                ProcessData.printvalue(getApplicationContext(),"Not detected");
            }

            //check if fingerprint permission is provided by app
            else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ProcessData.printvalue(getApplicationContext(),"Not given permission");
            }

            //check if mobile has lock.
            else if(!keyguardManager.isKeyguardSecure()){
                ProcessData.printvalue(getApplicationContext(),"Add lock to mobile in settings");
            }

            //check if atleast 1 fingerprint is registered
            else if(!fingerprintManager.hasEnrolledFingerprints()){
                ProcessData.printvalue(getApplicationContext(),"you should have Atleast 1 fingerprint");
            }

            //authenticate viz fingerprint
            else{
                //ProcessData.printvalue(getApplicationContext(),"Place your finger");
                FingerPrintAuthClass f = new FingerPrintAuthClass(this);
                f.authenticateUser(fingerprintManager,null);
            }

        }
        ((TextView)findViewById(R.id.fingerprintStatus)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("Successfully autheticated user")){
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("fingerprintAuth",true);
                    editor.commit();
                    Intent intent =  new Intent();
                    intent.putExtra("result","yes");
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else if(charSequence.toString().equals("Error: Fingerprint operation canceled.")){
                    Intent intent =  new Intent();
                    intent.putExtra("result","no");
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void onRestart() {
        Intent intent =  new Intent();
        intent.putExtra("result","no");
        setResult(RESULT_OK,intent);
        finish();
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent();
        intent.putExtra("result","no");
        setResult(RESULT_OK,intent);
        finish();
    }
}

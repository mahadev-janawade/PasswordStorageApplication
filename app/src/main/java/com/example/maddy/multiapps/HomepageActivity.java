package com.example.maddy.multiapps;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Maddy on 3/27/2020.
 */

public class HomepageActivity extends Activity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LinearLayout l;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(data.getStringExtra("result").equals("yes")){
                l.setAlpha(1.0f);
                editor = sharedPreferences.edit();
                editor.putString("defaultKey",data.getStringExtra("key"));
                editor.commit();
            }
            else{
                l.setAlpha(1.0f);
            }
        }

        if(requestCode == 2){
            if(data.getStringExtra("result").equals("yes")){
                if(!sharedPreferences.getBoolean("terms_conditions",false)){
                    Intent intent = new Intent(this,TermsConditions.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    setContentView(R.layout.password_homepage);
                    l = (LinearLayout) findViewById(R.id.homepage);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    l.setLayoutParams(p);
                    create();
                }
            }
            else if(data.getStringExtra("result").equals("no")){
                Intent intent = new Intent(this,HomepageActivity.class);
                startActivity(intent);
                finish();
            }
        }
        if(requestCode == 3) {
            if(data.getStringExtra("result").equals("yes")) {
                l.setAlpha(0.5f);
                Intent intent = new Intent(getApplicationContext(), SecurityKeyPopUp.class);
                intent.putExtra("message", "Please set default securityKey...");
                intent.putExtra("cancelButton", "no");
                startActivityForResult(intent, 1);
            }
            else if(data.getStringExtra("result").equals("no")){
                Intent intent = new Intent(this,HomepageActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void create(){
        if(!sharedPreferences.getBoolean("createDefaultSecurityKey",false)){
            l.setAlpha(0.5f);
            editor = sharedPreferences.edit();
            editor.putBoolean("createDefaultSecurityKey",true);
            editor.commit();
            Intent intent = new Intent(this,SecurityKeyPopUp.class);
            intent.putExtra("message","Please set default securityKey...");
            intent.putExtra("cancelButton","no");
            startActivityForResult(intent,1);
        }

        findViewById(R.id.addPasswordButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddUserActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.viewPasswordButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ViewPageActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.defaultSecuityKeyButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FingerprintAuth.class);
                startActivityForResult(intent,3);

            }
        });

        findViewById(R.id.applyDefaultSecuityKeyButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ApplyDefaultSecurity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent1 = new Intent(this,ServiceClass.class);
        startService(intent1);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(!sharedPreferences.getBoolean("fingerprintAuth",false)){
            Intent intent = new Intent(this,FingerprintAuth.class);
            startActivityForResult(intent,2);
        } else if(!sharedPreferences.getBoolean("terms_conditions",false)){
            Intent intent = new Intent(this,TermsConditions.class);
            startActivity(intent);
            finish();
        }
        else{
            setContentView(R.layout.password_homepage);
            l = (LinearLayout) findViewById(R.id.homepage);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            l.setLayoutParams(p);
            create();
        }
    }
/*
    @Override
    protected void onResume() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(!sharedPreferences.getBoolean("fingerprintAuth",false)){
            Intent intent = new Intent(this,FingerprintAuth.class);
            startActivityForResult(intent,2);
        } else if(!sharedPreferences.getBoolean("terms_conditions",false)){
            Intent intent = new Intent(this,TermsConditions.class);
            startActivity(intent);
            finish();
        }
        else{
            create();
        }
        super.onResume();
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

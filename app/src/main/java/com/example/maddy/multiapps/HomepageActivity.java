package com.example.maddy.multiapps;

import android.app.Activity;
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

    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_homepage);

        l = (LinearLayout) findViewById(R.id.homepage);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l.setLayoutParams(p);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
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
                l.setAlpha(0.5f);
                Intent intent = new Intent(getApplicationContext(),SecurityKeyPopUp.class);
                intent.putExtra("message","Please set default securityKey...");
                startActivityForResult(intent,1);
            }
        });

    }
}

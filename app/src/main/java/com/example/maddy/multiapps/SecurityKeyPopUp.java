package com.example.maddy.multiapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.Policy;

/**
 * Created by Maddy on 3/25/2020.
 */

public class SecurityKeyPopUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.keypopup);

        LinearLayout l = (LinearLayout) findViewById(R.id.linearLayoutKeyPopup);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.setMargins(40, 40, 40, 40);
        l.setLayoutParams(p);

        ((LinearLayout)findViewById(R.id.parentLinearLayoutKeyPopup)).setBackground(getResources().getDrawable(R.drawable.textviewborder));

        findViewById(R.id.securityKeyspan).setVisibility(View.INVISIBLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height =  dm.heightPixels;

        getWindow().setLayout((int)(width*.6),(int)(height*.4) );

        Intent intent = getIntent();
        String value = intent.getStringExtra("message");
        ((TextView) findViewById(R.id.messageSecurity)).setText(value);

        Button b1;
        Button b2;

        b1 = (Button)findViewById(R.id.okay);
        b2 = (Button)findViewById(R.id.cancel);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        if(intent.getStringExtra("cancelButton").equals("no")){
            b2.setVisibility(View.GONE);
            b2.setLayoutParams(params);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                EditText editText = (EditText) findViewById(R.id.securityKey);
                if(editText.getText().toString().equals("")){
                    intent.putExtra("result","no");
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else{
                    boolean status = ValidationClass.checkSecurityKey(editText.getText().toString());
                    if(status) {
                        intent.putExtra("result", "yes");
                        intent.putExtra("key", editText.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else{
                        ((TextView)findViewById(R.id.securityKeyspan)).setText("Security Key length min.4 charaters, max.6 characters");
                        findViewById(R.id.securityKeyspan).setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result","no");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public SecurityKeyPopUp() {
        super();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("result","no");
        setResult(RESULT_OK,intent);
        finish();
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

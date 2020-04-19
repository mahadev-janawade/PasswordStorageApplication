package com.example.maddy.multiapps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by Maddy on 4/18/2020.
 */

public class TermsConditions extends AppCompatActivity {

    Button button;
    TextView title,content;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.informationpage);
        checkBox = (CheckBox) findViewById(R.id.infopage_checkbox);
        button = (Button) findViewById(R.id.infopage_button);
        title = (TextView) findViewById(R.id.infopage_title);
        content = (TextView) findViewById(R.id.infopage_content);

        button.setEnabled(false);

        String titleText = "Important information!!";
        title.setText(titleText);

        String contentText = "\n\nSecurityKey plays major role in this application. \n\nYou can either set a default security key \'Change Default Security Key\' tab in the Homepage, or page will pop-up after reading this Terms and conditions.\n\n Security Key plays major role in encrypting the data, so that only you can view the data/passwords.";
        content.setText(contentText);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==false){
                    button.setEnabled(false);
                }
                else{
                    button.setEnabled(true);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor ;
                editor = sharedPreferences.edit();
                editor.putBoolean("terms_conditions",true);
                editor.commit();

                Intent intent = new Intent(getApplicationContext(),HomepageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}

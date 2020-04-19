package com.example.maddy.multiapps;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class AddUserActivity extends AppCompatActivity {

    TextView passwordCreated, passwordExpiry,clickpasswordCreated, clickpasswordExpiry, title;
    Calendar calender;
    Button button1,button2;
    UserDetails userDetails;
    String id;
    String []applications;
    AutoCompleteTextView application;

    private void create(){
        setContentView(R.layout.userdetails);
        getSupportActionBar().hide();
        findViewById(R.id.app_bar1).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.texttoolbar)).setText("Add Password Details");

        ((ImageButton)findViewById(R.id.backbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        SQLiteDatabase sql = ProcessData.getReadableApplication(getApplicationContext());
        String cols[] = {"application"};

        Cursor c = sql.query("Application",cols,null,null,null,null,null);
        applications = new String[c.getCount()];

        c.moveToFirst();
        for(int i=0;i<c.getCount();i++){
            applications[i] = c.getString(0);
            c.moveToNext();
        }

        application = (AutoCompleteTextView) findViewById(R.id.application);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, applications);
        application.setAdapter(adapter);

        button1 = (Button) findViewById(R.id.button);
        button1.setText("Add");
        button2 = (Button) findViewById(R.id.clear);
        button2.setText("Clear");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String securityKey = sharedPreferences.getString("defaultKey","");
        if(!securityKey.equals("")){
            ((EditText)findViewById(R.id.security)).setText(securityKey);
            ((EditText)findViewById(R.id.security)).setEnabled(false);
            LinearLayout l = (LinearLayout) findViewById(R.id.securityKeyChange);
           LinearLayout l1 = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            l1.setLayoutParams(layoutParams);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText("WARNING: Check to change default Security Key");
            l1.addView(checkBox);
            l.addView(l1);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b==false){
                        ((EditText)findViewById(R.id.security)).setEnabled(false);
                    }
                    else{
                        ((EditText)findViewById(R.id.security)).setEnabled(true);
                    }
                }
            });
        }

        if(!ValidationClass.checkSecurityKey(((EditText) findViewById(R.id.security)).getText().toString())){
            findViewById(R.id.securityspan).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.securityspan).setVisibility(View.INVISIBLE);
        }

        ((AutoCompleteTextView) findViewById(R.id.application)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!ValidationClass.checkApplicationType(charSequence.toString())) {
                    findViewById(R.id.idspan).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.idspan).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ((EditText) findViewById(R.id.email)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!ValidationClass.checkEmail(charSequence.toString())) {
                    findViewById(R.id.emailspan).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.emailspan).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ((EditText) findViewById(R.id.password)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!ValidationClass.checkPassword(charSequence.toString())) {
                    findViewById(R.id.passwordspan).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.passwordspan).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ((EditText) findViewById(R.id.security)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!ValidationClass.checkSecurityKey(charSequence.toString())) {
                    findViewById(R.id.securityspan).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.securityspan).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = v.getRootView();
                userDetails = new UserDetails();
                userDetails.setEmail(((EditText)view.findViewById(R.id.email)).getText().toString());
                userDetails.setApplicationType(((AutoCompleteTextView)view.findViewById(R.id.application)).getText().toString());
                userDetails.setUserName(((EditText)view.findViewById(R.id.username)).getText().toString());
                userDetails.setPassword(((EditText)view.findViewById(R.id.password)).getText().toString());
                userDetails.setNumber(((EditText)view.findViewById(R.id.number)).getText().toString());
                userDetails.setPasswordCreatedDate(((TextView)view.findViewById(R.id.passwordCreatedDate)).getText().toString());
                userDetails.setPasswordExpiryDate(((TextView)view.findViewById(R.id.passwordExpiryDate)).getText().toString());
                userDetails.setDescription(((EditText)view.findViewById(R.id.description)).getText().toString());
                userDetails.setSecurityKey(((EditText)view.findViewById(R.id.security)).getText().toString());
                calender = Calendar.getInstance();
                int month = calender.get(Calendar.MONTH);
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int year = calender.get(Calendar.YEAR);
                int hour = calender.get(Calendar.HOUR_OF_DAY);
                int minute = calender.get(Calendar.MINUTE);
                int sec = calender.get(Calendar.SECOND);
                id = String.valueOf(year+""+month+""+day+""+hour+""+minute+""+sec);
                userDetails.setId(id);


                boolean checks = ValidationClass.checksAdd(userDetails,getApplicationContext());

                if(checks) {
                    boolean status = ValidationClass.checkId(userDetails.applicationType,userDetails.email,getApplicationContext());
                    if (status == true) {
                        boolean add_status = ProcessData.addDetails(userDetails, getApplicationContext());
                        if(add_status == true) {
                            ProcessData.printvalue(getApplicationContext(), "Password Details saved!!");
                            Intent i = new Intent(getApplicationContext(),HomepageActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            ProcessData.printvalue(getApplicationContext(), "Unable to Add Password details!!");
                        }
                    } else {
                        ProcessData.printvalue(getApplicationContext(), "Already " + userDetails.email + " for " + userDetails.applicationType + " is present. You can edit details in View_details->Edit!!");
                    }
                }
                else{
                    ProcessData.printvalue(getApplicationContext(),"Please fill mandatory fields as per constraints");
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddUserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        passwordCreated = (TextView) findViewById(R.id.passwordCreatedDate);
        clickpasswordCreated = (TextView) findViewById(R.id.clickPasswordCreatedDate);
        clickpasswordCreated.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                calender = Calendar.getInstance();
                int month = (calender.get(Calendar.MONTH))+1;
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int year = calender.get(Calendar.YEAR);


                DatePickerDialog dialog = new DatePickerDialog(AddUserActivity.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        passwordCreated.setText(year+"/"+month+"/"+day);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        passwordExpiry = (TextView) findViewById(R.id.passwordExpiryDate);
        clickpasswordExpiry = (TextView) findViewById(R.id.clickPasswordExpiryDate);
        clickpasswordExpiry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                calender = Calendar.getInstance();
                int month = (calender.get(Calendar.MONTH))+1;
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int year = calender.get(Calendar.YEAR);


                DatePickerDialog dialog = new DatePickerDialog(AddUserActivity.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        passwordExpiry.setText(year+"/"+month+"/"+day);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create();
    }
}

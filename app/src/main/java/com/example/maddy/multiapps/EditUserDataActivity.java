package com.example.maddy.multiapps;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
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

/**
 * Created by Maddy on 3/27/2020.
 */

public class EditUserDataActivity extends AppCompatActivity {

    Calendar calender;
    TextView title;
    Button button1,button2;
    AutoCompleteTextView application;
    EditText username, email, password, number, description, security;
    TextView passwordCreated, passwordExpiry,clickpasswordCreated, clickpasswordExpiry;
    UserDetails userDetails, editedUserDetails;
    String securitykey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdetails);

        getSupportActionBar().hide();
        findViewById(R.id.app_bar1).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.texttoolbar)).setText("Edit Details");

        ((ImageButton)findViewById(R.id.backbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button1 = (Button) findViewById(R.id.button);
        button1.setText("Update");
        button2 = (Button) findViewById(R.id.clear);
        button2.setText("Back");

        Intent intent = getIntent();
        userDetails = intent.getParcelableExtra("userdetailsEdit");

        application = ((AutoCompleteTextView)findViewById(R.id.application));
        application.setText(userDetails.applicationType);
        application.setEnabled(false);

        TextView viewId = (TextView)findViewById(R.id.idspan);
        viewId.setVisibility(View.GONE);

        username = (EditText)findViewById(R.id.username);
        username.setText(userDetails.userName);

        email = (EditText)findViewById(R.id.email);
        email.setText(userDetails.email);
        email.setEnabled(false);
        CheckBox emailCheckBox = new CheckBox(this);
        emailCheckBox.setText("Not Recommended to change email");
        ((LinearLayout)findViewById(R.id.emailCheckbox)).addView(emailCheckBox);
        emailCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    email.setEnabled(true);
                }else{
                    email.setEnabled(false);
                }
            }
        });
        if(!ValidationClass.checkEmail(userDetails.email)) {
            findViewById(R.id.emailspan).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.emailspan).setVisibility(View.INVISIBLE);
        }
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

        password = (EditText)findViewById(R.id.password);
        password.setText(userDetails.password);
        if(!ValidationClass.checkPassword(userDetails.password)) {
            findViewById(R.id.passwordspan).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.passwordspan).setVisibility(View.INVISIBLE);
        }
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

        description = (EditText)findViewById(R.id.description);
        description.setText(userDetails.description);

        number = (EditText)findViewById(R.id.number);
        number.setText(userDetails.number);

        security = (EditText)findViewById(R.id.security);
        security.setText(userDetails.securityKey);
        security.setEnabled(false);
        CheckBox securityCheckBox = new CheckBox(this);
        securityCheckBox.setText("Not Recommended to change Secuirty");
        ((LinearLayout)findViewById(R.id.secuityCheckBox)).addView(securityCheckBox);
        securityCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    security.setEnabled(true);
                }else{
                    security.setEnabled(false);
                }
            }
        });
        if(!ValidationClass.checkSecurityKey(userDetails.securityKey)) {
            findViewById(R.id.securityspan).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.securityspan).setVisibility(View.INVISIBLE);
        }
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
        securitykey = intent.getStringExtra("KeySecurity");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = (View) v.getRootView();
                editedUserDetails = new UserDetails();
                editedUserDetails.setId(userDetails.id);
                editedUserDetails.setApplicationType(userDetails.applicationType);
                editedUserDetails.setEmail(((EditText)view.findViewById(R.id.email)).getText().toString());
                editedUserDetails.setUserName(((EditText)view.findViewById(R.id.username)).getText().toString());
                editedUserDetails.setPassword(((EditText)view.findViewById(R.id.password)).getText().toString());
                editedUserDetails.setNumber(((EditText)view.findViewById(R.id.number)).getText().toString());
                editedUserDetails.setDescription(((EditText)view.findViewById(R.id.description)).getText().toString());
                editedUserDetails.setPasswordCreatedDate(((TextView)view.findViewById(R.id.passwordCreatedDate)).getText().toString());
                editedUserDetails.setPasswordExpiryDate(((TextView)view.findViewById(R.id.passwordExpiryDate)).getText().toString());
                editedUserDetails.setSecurityKey(((EditText)view.findViewById(R.id.security)).getText().toString());

                boolean checks = ValidationClass.checksAdd(editedUserDetails,getApplicationContext());
                if(checks) {

                    boolean emailStatus = false;
                    if(!userDetails.email.equals(editedUserDetails.email)){
                        SQLiteDatabase sql = ProcessData.getReadable(getApplicationContext());
                        Cursor c = sql.rawQuery("Select id from Password where applicationType=? and email=?",new String[]{editedUserDetails.applicationType,editedUserDetails.email});
                        if(c.getCount()>0){
                            emailStatus = false;
                        }else
                        {
                            emailStatus = true;
                        }
                    }
                    else{
                        emailStatus = true;
                    }
                    if(emailStatus) {
                        boolean add_status = ProcessData.editDetails(editedUserDetails, getApplicationContext());
                        if (add_status == true) {
                            ProcessData.printvalue(getApplicationContext(), "Database updated!!");
                            finish();
                        } else {
                            ProcessData.printvalue(getApplicationContext(), "Unable to store the edited details!!");
                        }
                    }
                    else{
                        ProcessData.printvalue(getApplicationContext(), "Email already used for this application!!");
                    }
                }
                else {
                    ProcessData.printvalue(getApplicationContext(), "please fill mandatory fields as per constraints");
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),ViewPageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        passwordCreated = (TextView) findViewById(R.id.passwordCreatedDate);
        passwordCreated.setText(userDetails.passwordCreatedDate);
        clickpasswordCreated = (TextView) findViewById(R.id.clickPasswordCreatedDate);
        clickpasswordCreated.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                calender = Calendar.getInstance();
                int month = (calender.get(Calendar.MONTH));
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int year = calender.get(Calendar.YEAR);


                DatePickerDialog dialog = new DatePickerDialog(EditUserDataActivity.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        passwordCreated.setText(year+"/"+(month+1)+"/"+day);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        passwordExpiry = (TextView) findViewById(R.id.passwordExpiryDate);
        passwordExpiry.setText(userDetails.passwordExpiryDate);
        clickpasswordExpiry = (TextView) findViewById(R.id.clickPasswordExpiryDate);
        clickpasswordExpiry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                calender = Calendar.getInstance();
                int month = (calender.get(Calendar.MONTH));
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int year = calender.get(Calendar.YEAR);


                DatePickerDialog dialog = new DatePickerDialog(EditUserDataActivity.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        passwordExpiry.setText(year+"/"+(month+1)+"/"+day);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,ViewPageActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

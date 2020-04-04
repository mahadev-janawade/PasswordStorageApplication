package com.example.maddy.multiapps;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Maddy on 3/27/2020.
 */

public class EditUserDataActivity extends Activity {

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

        title = (TextView) findViewById(R.id.title);
        title.setText("Edit Details");

        button1 = (Button) findViewById(R.id.button);
        button1.setText("Edit");
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

        TextView viewEmail = (TextView)findViewById(R.id.emailspan);
        viewEmail.setVisibility(View.GONE);

        password = (EditText)findViewById(R.id.password);
        password.setText(userDetails.password);

        description = (EditText)findViewById(R.id.description);
        description.setText(userDetails.description);

        number = (EditText)findViewById(R.id.number);
        number.setText(userDetails.number);

        security = (EditText)findViewById(R.id.security);
        security.setEnabled(false);
        TextView viewSecurity = (TextView)findViewById(R.id.securityspan);
        viewSecurity.setVisibility(View.GONE);

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
                editedUserDetails.setSecurityKey(userDetails.securityKey);

                boolean checks = ValidationClass.checksAdd(editedUserDetails,getApplicationContext());
                if(checks) {

                    if(userDetails.email.equals(editedUserDetails.email)){

                    }

                    boolean add_status = ProcessData.editDetails(editedUserDetails, securitykey, getApplicationContext());
                    if (add_status == true) {
                        ProcessData.printvalue(getApplicationContext(), "Database updated!!");
                        finish();
                    } else {
                        ProcessData.printvalue(getApplicationContext(), "Unable to store the edited details!!");
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
                int month = calender.get(Calendar.MONTH);
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int year = calender.get(Calendar.YEAR);


                DatePickerDialog dialog = new DatePickerDialog(EditUserDataActivity.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        passwordCreated.setText(year+"/"+month+"/"+day);
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
                int month = calender.get(Calendar.MONTH);
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int year = calender.get(Calendar.YEAR);


                DatePickerDialog dialog = new DatePickerDialog(EditUserDataActivity.this, new DatePickerDialog.OnDateSetListener(){

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
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

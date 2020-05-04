package com.example.maddy.multiapps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

/**
 * Created by Maddy on 3/27/2020.
 */

public class ViewPageActivity extends AppCompatActivity {

    UserDetails userDetailsToPass;
    String emailToDelete,applicationToDelete,securityDelete;
    public CursorUserDetails passwords;
    public LinearLayout linearLayout,childLinearLayout;
    private String[] columns_to_view = {"id","applicationType","email","username","password","number","description","passwordcreatedDate","passwordExpiryDate","securityKey"};
    public LinearLayout.LayoutParams p;
    Cursor cursor;
    Spinner spinner;

    private void create(){
        findViewById(R.id.app_bar).setVisibility(View.VISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);

        ((TextView)findViewById(R.id.texttoolbar)).setText("Password Details");
        ((TextView)findViewById(R.id.textDraw)).setVisibility(View.GONE);


        ((ImageButton)findViewById(R.id.backbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        LinearLayout layout_dropdown = new LinearLayout(getApplicationContext());
        p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layout_dropdown.setLayoutParams(p);

        TextView textView = new TextView(getApplicationContext());
        textView.setText("Filter by : ");
        layout_dropdown.addView(textView);

        String[] applicationView = {"application"};
        SQLiteDatabase sql = ProcessData.getReadableApplication(getApplicationContext());
        Cursor cursorApplication = sql.query("APPLICATION",applicationView,null,null,null,null,null);

        String[] list = new String[cursorApplication.getCount()+1];
        list[0] = "Select application";
        cursorApplication.moveToFirst();
        for(int i =0;i<cursorApplication.getCount();i++){
            list[i+1] = cursorApplication.getString(0);
            cursorApplication.moveToNext();
        }

        spinner = new Spinner(getApplicationContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        linearLayout.addView(layout_dropdown);
        linearLayout.addView(spinner);

        cursor = ProcessData.viewDetails(getApplicationContext());

        childLinearLayout = new LinearLayout(this);
        childLinearLayout.setId(R.id.linear+9898);
        childLinearLayout.setOrientation(LinearLayout.VERTICAL);
        p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        childLinearLayout.setLayoutParams(p);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(cursor.getCount()>0) {
                    childLinearLayout.setVisibility(View.GONE);
                    childLinearLayout = new LinearLayout(getApplicationContext());
                    childLinearLayout.setId(R.id.linear+9898);
                    childLinearLayout.setOrientation(LinearLayout.VERTICAL);
                    p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    childLinearLayout.setLayoutParams(p);
                    passwords = new CursorUserDetails();
                    passwords = addUserdetails(cursor);
                    printOnScreen(getApplicationContext());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if(cursor.getCount()>0) {
                    passwords = new CursorUserDetails();
                    passwords = addUserdetails(cursor);

                    printOnScreen(getApplicationContext());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        linearLayout.setAlpha(1.0f);


        if(requestCode == 13){
            if (data.getStringExtra("result").toString().equals("yes")) {
                Intent intent = new Intent(getApplicationContext(), SecurityKeyPopUp.class);
                intent.putExtra("message","Enter SecurityKey to Delete");
                intent.putExtra("cancelButton","yes");
                startActivityForResult(intent, 412);
            }
        }

        if(requestCode == 412){
            if (data.getStringExtra("result").toString().equals("yes")) {
                String security_Key =  data.getStringExtra("key").toString();


                if(ProcessData.Encrypt(security_Key,security_Key).equals(securityDelete)) {

                    SQLiteDatabase sql = ProcessData.getWriteable(getApplicationContext());
                    int del = sql.delete("PASSWORD", "id=?", new String[]{emailToDelete});

                    if (del > 0) {
                        SQLiteDatabase readUniqueApplications = ProcessData.getReadable(getApplicationContext());
                        Cursor cursor = readUniqueApplications.query("PASSWORD", new String[]{"applicationType"}, "applicationType = ?", new String[]{applicationToDelete}, "applicationtype", null, null);
                        if (cursor.getCount() == 0) {
                            SQLiteDatabase delUniqueApplications = ProcessData.getWriteableApplication(getApplicationContext());
                            int appDel = delUniqueApplications.delete("APPLICATION", "application=?", new String[]{applicationToDelete});
                        }
                        SQLiteDatabase sqlSecurity = ProcessData.getWriteableSecurity(getApplicationContext());
                        int isSecDel = sqlSecurity.delete("SECURITY", "id=?", new String[]{emailToDelete});
                    }
                    Intent i = new Intent(getApplicationContext(), ViewPageActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    ProcessData.printvalue(getApplicationContext(),"Wrong Security Key!!");
                }
            }
        }

        if(requestCode == 300){


            if (data.getStringExtra("result").toString().equals("yes")) {
                String security_Key =  data.getStringExtra("key").toString();

                if(ProcessData.Encrypt(security_Key,security_Key).equals(userDetailsToPass.securityKey)) {
                    userDetailsToPass.setId(userDetailsToPass.id);
                    userDetailsToPass.setApplicationType(userDetailsToPass.applicationType);
                    userDetailsToPass.setEmail(userDetailsToPass.email);
                    userDetailsToPass.setUserName(userDetailsToPass.userName);
                    userDetailsToPass.setPassword(ProcessData.Decrypt(userDetailsToPass.password, security_Key));
                    userDetailsToPass.setNumber(ProcessData.Decrypt(userDetailsToPass.number, security_Key));
                    userDetailsToPass.setDescription(ProcessData.Decrypt(userDetailsToPass.description, security_Key));
                    userDetailsToPass.setPasswordCreatedDate(ProcessData.Decrypt(userDetailsToPass.passwordCreatedDate, security_Key));
                    userDetailsToPass.setPasswordExpiryDate(ProcessData.Decrypt(userDetailsToPass.passwordExpiryDate, security_Key));
                    userDetailsToPass.setSecurityKey(ProcessData.Decrypt(userDetailsToPass.securityKey, security_Key));
                    Intent intent = new Intent(getApplicationContext(), EditUserDataActivity.class);
                    intent.putExtra("userdetailsEdit", userDetailsToPass);
                    intent.putExtra("KeySecurity", security_Key);
                    startActivity(intent);
                    finish();
                }
                else{
                    ProcessData.printvalue(getApplicationContext(),"Wrong Security key!!");
                }
            }
        }

        if(requestCode == 15){


            if (data.getStringExtra("result").toString().equals("yes")) {
                String security_Key =  data.getStringExtra("key").toString();

                if(ProcessData.Encrypt(security_Key,security_Key).equals(userDetailsToPass.securityKey)) {
                    userDetailsToPass.setId(ProcessData.Decrypt(userDetailsToPass.id, security_Key));
                    userDetailsToPass.setApplicationType(userDetailsToPass.applicationType);
                    userDetailsToPass.setEmail(userDetailsToPass.email);
                    userDetailsToPass.setUserName(userDetailsToPass.userName);
                    userDetailsToPass.setPassword(ProcessData.Decrypt(userDetailsToPass.password, security_Key));
                    userDetailsToPass.setNumber(ProcessData.Decrypt(userDetailsToPass.number, security_Key));
                    userDetailsToPass.setDescription(ProcessData.Decrypt(userDetailsToPass.description, security_Key));
                    userDetailsToPass.setPasswordCreatedDate(ProcessData.Decrypt(userDetailsToPass.passwordCreatedDate, security_Key));
                    userDetailsToPass.setPasswordExpiryDate(ProcessData.Decrypt(userDetailsToPass.passwordExpiryDate, security_Key));
                    userDetailsToPass.setSecurityKey(ProcessData.Decrypt(userDetailsToPass.securityKey, security_Key));
                    Intent intent = new Intent(getApplicationContext(), ViewPasswordActivity.class);
                    intent.putExtra("userdetailsView", userDetailsToPass);
                    intent.putExtra("KeySecurity", security_Key);
                    startActivity(intent);
                }
                else{
                    ProcessData.printvalue(getApplicationContext(),"Wrong Security key!!");
                }
            }
        }


    }

    private CursorUserDetails addUserdetails(Cursor cursor)
    {
        String data = "";
        cursor.moveToFirst();
        for(int i =0;i<cursor.getCount();i++)
        {
            UserDetails userDetails = new UserDetails();
            UserDetailsViewId userDetailsViewId = new UserDetailsViewId();
            Integer valueId = new Integer(332 + (1500 * i));
            int valId = valueId.intValue();

            userDetails.setId(cursor.getString(0));
            userDetailsViewId.setId(valId+1);

            userDetails.setApplicationType(cursor.getString(1));
            userDetailsViewId.setApplicationType(valId+2);

            userDetails.setEmail(cursor.getString(2));
            userDetailsViewId.setEmail(valId+3);

            userDetails.setUserName(cursor.getString(3));
            userDetailsViewId.setUserName(valId+4);

            userDetails.setPassword(cursor.getString(4));
            userDetailsViewId.setPassword(valId+5);

            userDetails.setNumber(cursor.getString(5));
            userDetailsViewId.setNumber(valId+6);

            userDetails.setDescription(cursor.getString(6));
            userDetailsViewId.setDescription(valId+7);

            userDetails.setPasswordCreatedDate(cursor.getString(7));
            userDetailsViewId.setPasswordCreatedDate(valId+8);

            userDetails.setPasswordExpiryDate(cursor.getString(8));
            userDetailsViewId.setPasswordExpiryDate(valId+9);

            userDetails.setSecurityKey(cursor.getString(9));
            userDetailsViewId.setSecurityKey(valId+10);

            passwords.addDetails(userDetails,userDetailsViewId);
            passwords.addIds(new Integer(i),new Integer(i+500), new Integer(i+1000));
            cursor.moveToNext();

        }
        return passwords;
    }

    public LinearLayout createLayout(Context context,String val, int id, int index){
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(p);
        TextView text1 = new TextView(this);
        text1.setText(columns_to_view[index] + " : ");
        linearLayout.addView(text1);

        TextView value = new TextView(this);
        value.setId(id);
        value.setTextIsSelectable(true);
        value.setPaddingRelative(10, 10, 10, 10);
        value.setText(val);

        linearLayout.addView(value);

        return linearLayout;
    }


    public void printOnScreen(Context context){
        for (int i=0;i<passwords.userDetails.size();i++) {
            if (spinner.getSelectedItem().toString().equals("Select application") || spinner.getSelectedItem().toString().equals(passwords.userDetails.get(i).applicationType)) {
                LinearLayout l = new LinearLayout(context);
                l.setId(R.id.linear + i);
                l.setOrientation(LinearLayout.VERTICAL);
                p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                p.setMargins(10, 10, 10, 10);
                l.setLayoutParams(p);

                l.addView(createLayout(getApplicationContext(), passwords.userDetails.get(i).applicationType,
                        passwords.userDetailsViewIds.get(i).applicationType, 1));

                l.addView(createLayout(getApplicationContext(), passwords.userDetails.get(i).email,
                        passwords.userDetailsViewIds.get(i).email, 2));


                l.addView(createLayout(getApplicationContext(), passwords.userDetails.get(i).userName,
                        passwords.userDetailsViewIds.get(i).userName, 3));

                LinearLayout l1 = new LinearLayout(this);
                l1.setOrientation(LinearLayout.HORIZONTAL);
                l1.setLayoutParams(p);
                TextView t1 = new TextView(this);
                t1.setId((passwords.editId.get(i)).intValue());
                t1.setPadding(20, 0, 0, 0);
                t1.setText("Edit");
                t1.setTextColor(Color.parseColor("#2980B9"));
                t1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        userDetailsToPass = new UserDetails();
                        int i = Integer.parseInt(String.valueOf(view.getId()));

                        userDetailsToPass.setId(passwords.userDetails.get(i).id);
                        userDetailsToPass.setApplicationType(passwords.userDetails.get(i).applicationType);
                        userDetailsToPass.setEmail(passwords.userDetails.get(i).email);
                        userDetailsToPass.setUserName(passwords.userDetails.get(i).userName);
                        userDetailsToPass.setPassword(passwords.userDetails.get(i).password);
                        userDetailsToPass.setNumber(passwords.userDetails.get(i).number);
                        userDetailsToPass.setDescription(passwords.userDetails.get(i).description);
                        userDetailsToPass.setPasswordCreatedDate(passwords.userDetails.get(i).passwordCreatedDate);
                        userDetailsToPass.setPasswordExpiryDate(passwords.userDetails.get(i).passwordExpiryDate);
                        userDetailsToPass.setSecurityKey(passwords.userDetails.get(i).securityKey);

                        Intent intent = new Intent(getApplicationContext(), SecurityKeyPopUp.class);
                        intent.putExtra("message","Enter SecurityKey to Edit the details");
                        intent.putExtra("cancelButton","yes");
                        startActivityForResult(intent, 300);
                    }
                });

                TextView t2 = new TextView(this);
                t2.setId((passwords.deleteId.get(i)).intValue());
                t2.setPadding(120, 0, 0, 0);
                t2.setText("Delete");
                t2.setTextColor(Color.parseColor("#2980B9"));
                t2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int index = Integer.parseInt(String.valueOf(view.getId())) - 500;

                        emailToDelete = passwords.userDetails.get(index).id;
                        applicationToDelete = passwords.userDetails.get(index).applicationType;
                        securityDelete = passwords.userDetails.get(index).securityKey;

                        linearLayout.setAlpha(.5f);
                        Intent intent = new Intent(getApplicationContext(), PopupCLass.class);
                        intent.putExtra("message", "Do you want to delete?" + "\n" + "Application: " + passwords.userDetails.get(index).applicationType + "\n" + "Email: " + passwords.userDetails.get(index).email);
                        intent.putExtra("button1", "Delete");
                        intent.putExtra("button2", "Cancel");
                        startActivityForResult(intent, 13);
                    }
                });


                TextView viewData = new TextView(this);
                viewData.setId(passwords.viewId.get(i));
                viewData.setPadding(200, 0, 0, 0);
                viewData.setText("View");
                viewData.setTextColor(Color.parseColor("#2980B9"));
                viewData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userDetailsToPass = new UserDetails();
                        int i = Integer.parseInt(String.valueOf(view.getId())) - 1000;

                        userDetailsToPass.setId(passwords.userDetails.get(i).id);
                        userDetailsToPass.setApplicationType(passwords.userDetails.get(i).applicationType);
                        userDetailsToPass.setEmail(passwords.userDetails.get(i).email);
                        userDetailsToPass.setUserName(passwords.userDetails.get(i).userName);
                        userDetailsToPass.setPassword(passwords.userDetails.get(i).password);
                        userDetailsToPass.setNumber(passwords.userDetails.get(i).number);
                        userDetailsToPass.setDescription(passwords.userDetails.get(i).description);
                        userDetailsToPass.setPasswordCreatedDate(passwords.userDetails.get(i).passwordCreatedDate);
                        userDetailsToPass.setPasswordExpiryDate(passwords.userDetails.get(i).passwordExpiryDate);
                        userDetailsToPass.setSecurityKey(passwords.userDetails.get(i).securityKey);

                        linearLayout.setAlpha(.5f);
                        Intent intent = new Intent(getApplicationContext(), SecurityKeyPopUp.class);
                        intent.putExtra("message","Please enter Security key to view this password");
                        intent.putExtra("cancelButton","yes");
                        startActivityForResult(intent, 15);

                    }
                });

                l1.addView(t1);
                l1.addView(t2);
                l1.addView(viewData);
                l.addView(l1);

                l.setBackground(getResources().getDrawable(R.drawable.textviewborder));
                childLinearLayout.addView(l);
            }
        }

        linearLayout.addView(childLinearLayout);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page);

        getSupportActionBar().hide();

        create();

    }

}

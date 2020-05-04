package com.example.maddy.multiapps;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.print.PrintDocumentAdapter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maddy on 4/19/2020.
 */

public class ApplyDefaultSecurity extends AppCompatActivity {

    UserDetails userDetailsToPass;
    String emailToDelete,applicationToDelete,securityDelete;
    public CursorUserDetails passwords;
    public LinearLayout linearLayout,childLinearLayout;
    private String[] columns_to_view = {"id","applicationType","email","username","password","number","description","passwordcreatedDate","passwordExpiryDate","securityKey"};
    public LinearLayout.LayoutParams p;
    Cursor cursor;
    private List<Integer> listOfItems,selectedItems;
    Spinner spinner;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 13){
            if (data.getStringExtra("result").toString().equals("yes")) {
                Intent intent = new Intent(getApplicationContext(), FingerprintAuth.class);
                startActivityForResult(intent, 3);
            }
        }

        if(requestCode == 3) {
            if(data.getStringExtra("result").equals("yes")) {
                //ProcessData.printvalue(getApplicationContext(),String.valueOf(selectedItems.size()));
                UserDetails details;
                //String[] columns_to_view = {"id","securityKey"};
                SQLiteDatabase sql = ProcessData.getReadableSecurity(getApplicationContext());
                //Cursor cursor = sql.query("SECURITY",columns_to_view,null,null,null,null,null);

                for(int i=0;i<selectedItems.size();i++){
                    details = new UserDetails();
                    details.id = passwords.userDetails.get(selectedItems.get(i)).id;
                    Cursor c = sql.rawQuery("Select securityKey from Security where id=?",new String[]{details.id});
                    if(c!=null){
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String securityKey = sharedPreferences.getString("defaultKey","");
                        if(!securityKey.equals("")) {
                            c.moveToFirst();
                            String security = c.getString(0);
                            details.applicationType = passwords.userDetails.get(selectedItems.get(i)).applicationType;
                            details.email = passwords.userDetails.get(selectedItems.get(i)).email;
                            details.userName = passwords.userDetails.get(selectedItems.get(i)).userName;
                            details.password = ProcessData.Decrypt(passwords.userDetails.get(selectedItems.get(i)).password, security);
                            details.number = ProcessData.Decrypt(passwords.userDetails.get(selectedItems.get(i)).number, security);
                            details.description = ProcessData.Decrypt(passwords.userDetails.get(selectedItems.get(i)).description, security);
                            details.passwordCreatedDate = ProcessData.Decrypt(passwords.userDetails.get(selectedItems.get(i)).passwordCreatedDate, security);
                            details.securityKey = securityKey;
                            details.passwordExpiryDate = ProcessData.Decrypt(passwords.userDetails.get(selectedItems.get(i)).passwordExpiryDate, security);
                            ProcessData.editDetails(details, getApplicationContext());

                        }

                    }
                }
                ProcessData.printvalue(getApplicationContext(),"Successfully changed Security Keys!!");
                Intent intent = new Intent(this,HomepageActivity.class);
                startActivity(intent);
                finish();

            }
            else if(data.getStringExtra("result").equals("no")){
                Intent intent = new Intent(this,HomepageActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

        private void create(){
        findViewById(R.id.app_bar).setVisibility(View.VISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);

        ((TextView)findViewById(R.id.texttoolbar)).setText("Apply default Security Key");
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
        textView.setText("Select all");
        layout_dropdown.addView(textView);

        listOfItems = new ArrayList<Integer>();
        selectedItems = new ArrayList<Integer>();

        CheckBox checkBox = new CheckBox(this);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton)view).isChecked()){
                    View a = (View) view.getParent();
                    for(int i=0;i<listOfItems.size();i++){
                        if(!((CheckBox)a.findViewById(listOfItems.get(i))).isChecked()){
                            ((CheckBox)a.findViewById(listOfItems.get(i))).setChecked(true);
                        }
                    }
                }
                else{
                    View a = (View) view.getParent();
                    for(int i=0;i<listOfItems.size();i++){
                        if(((CheckBox)a.findViewById(listOfItems.get(i))).isChecked()){
                            ((CheckBox)a.findViewById(listOfItems.get(i))).setChecked(false);
                        }
                    }
                }
            }
        });

        Button b = new Button(this);
        b.setText("Apply");
        b.setGravity(Gravity.LEFT);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(),FingerprintAuth.class);
                //startActivityForResult(intent,3);
                Intent intent = new Intent(getApplicationContext(), PopupCLass.class);
                intent.putExtra("message", "Do you really want to Apply default securityKey for the selected?");
                intent.putExtra("button1", "Apply");
                intent.putExtra("button2", "Cancel");
                startActivityForResult(intent, 13);

            }
        });

        linearLayout.addView(layout_dropdown);
        linearLayout.addView(checkBox);
        linearLayout.addView(b);

        cursor = ProcessData.viewDetails(getApplicationContext());
        //ProcessData.printvalue(this,String.valueOf(cursor.getCount()));

        childLinearLayout = new LinearLayout(this);
        childLinearLayout.setId(R.id.linear+9898);
        childLinearLayout.setOrientation(LinearLayout.VERTICAL);
        p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        childLinearLayout.setLayoutParams(p);

        passwords = new CursorUserDetails();
        passwords = addUserdetails(cursor);

        printOnScreen(getApplicationContext());

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
            CheckBox t1 = new CheckBox(this);
            int id = (passwords.editId.get(i)).intValue();
            t1.setId(id);
            t1.setPadding(20, 0, 0, 0);
            t1.setText("check to apply default securityKey");
            t1.setTextColor(Color.parseColor("#2980B9"));
            listOfItems.add(id);

            t1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b) {
                        selectedItems.add(new Integer(compoundButton.getId()));
                    }
                    else{
                        selectedItems.remove(new Integer(compoundButton.getId()));
                    }
                }
            });
            l1.addView(t1);
            l.addView(l1);

            l.setBackground(getResources().getDrawable(R.drawable.textviewborder));
            childLinearLayout.addView(l);
        }
        linearLayout.addView(childLinearLayout);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page);

        getSupportActionBar().hide();

        create();
    }
}

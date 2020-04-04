package com.example.maddy.multiapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by Maddy on 3/25/2020.
 */

public class ProcessData {

    public static void printvalue(Context context, String data){
        Toast.makeText( context, data, Toast.LENGTH_SHORT).show();
    }

    public static SQLiteDatabase getWriteable(Context context){
        DetailsHelperClass pwdhelper = new DetailsHelperClass(context);
        SQLiteDatabase sql = pwdhelper.getWritableDatabase();
        return sql;
    }

    public static SQLiteDatabase getReadable(Context context){
        DetailsHelperClass pwdhelper = new DetailsHelperClass(context);
        SQLiteDatabase sql = pwdhelper.getReadableDatabase();
        return sql;
    }

    public static SQLiteDatabase getWriteableApplication(Context context){
        ApplicationsTypeHelperClass pwdhelper = new ApplicationsTypeHelperClass(context);
        SQLiteDatabase sql = pwdhelper.getWritableDatabase();
        return sql;
    }

    public static SQLiteDatabase getReadableApplication(Context context){
        ApplicationsTypeHelperClass pwdhelper = new ApplicationsTypeHelperClass(context);
        SQLiteDatabase sql = pwdhelper.getReadableDatabase();
        return sql;
    }


    public static String Encrypt(String data,String securityKey)
    {
        String data1 = "";
        int add = 0;
        for (int i = 0; i < securityKey.length(); i++) {
            add += Integer.parseInt(String.valueOf(securityKey.charAt(i)));
        }
        int maxData = data.length();
        int maxKey = securityKey.length();
        for(int i=0 ; i<data.length();i++){
            int charAscii = (int)((Character)data.charAt(i));
            int ndigit = (Integer.parseInt(String.valueOf((securityKey.charAt(i%(maxKey))))));
            int last2digit = Integer.parseInt(securityKey.substring(maxKey-2, maxKey));
            int first2digit = Integer.parseInt(securityKey.substring(0, 2));
            int defaultVal = 542;
            data1 += Integer.toHexString(((((charAscii+add+ndigit)+last2digit)+defaultVal)-first2digit));
        }
        return data1;
    }

    public static String Decrypt(String data,String securityKey)
    {
        String data1 = "";
        long add = 0;
        for (int i = 0; i < securityKey.length(); i++) {
            add += Integer.parseInt(String.valueOf(securityKey.charAt(i)));
        }
        int maxData = data.length();
        int maxKey = securityKey.length();
        int j=0;
        for(int i=0 ; i<data.length();i+=3){
            String hex;
            try {
                hex = data.substring(i, i + 3);
            }catch (Exception e){
               hex = data.substring(i);
            }
            int charAscii = Integer.parseInt(hex,16);

            int ndigit = (Integer.parseInt(String.valueOf((securityKey.charAt(j%(maxKey))))));
            int last2digit = Integer.parseInt(securityKey.substring(maxKey-2, maxKey));
            int first2digit = Integer.parseInt(securityKey.substring(0, 2));
            int defaultVal = 542;

            data1 +=  String.valueOf((char)((((charAscii+first2digit)-defaultVal)-last2digit)-ndigit-add));
            j++;
        }
        return data1;
    }


    public static boolean addDetails(UserDetails userDetails, Context context)
    {
        SQLiteDatabase sql = getWriteable(context);
        SQLiteDatabase sqlApp = getWriteableApplication(context);
        ContentValues contentValuesApp = new ContentValues();
        contentValuesApp.put("application",userDetails.applicationType);
        long row1 = sqlApp.insert("APPLICATION",null,contentValuesApp);

        ContentValues contentValues = new ContentValues();
        contentValues.put("id",ProcessData.Encrypt(userDetails.id,userDetails.securityKey));
        contentValues.put("applicationType",userDetails.applicationType);
        contentValues.put("email",userDetails.email);
        contentValues.put("username",userDetails.userName);
        contentValues.put("password",ProcessData.Encrypt(userDetails.password,userDetails.securityKey));
        contentValues.put("number",ProcessData.Encrypt(userDetails.number,userDetails.securityKey));
        contentValues.put("description",ProcessData.Encrypt(userDetails.description,userDetails.securityKey));
        contentValues.put("passwordcreatedDate",ProcessData.Encrypt(userDetails.passwordCreatedDate.toString(),userDetails.securityKey));
        contentValues.put("passwordExpiryDate",ProcessData.Encrypt(userDetails.passwordExpiryDate.toString(),userDetails.securityKey));
        contentValues.put("securityKey",ProcessData.Encrypt(userDetails.securityKey,userDetails.securityKey));

        long row = sql.insert("PASSWORD", null, contentValues);

        if(row == -1){
            return false;
        }
        else
            return true;
    }


    public static boolean editDetails(UserDetails userDetails, String securityKey, Context context) {
        SQLiteDatabase sql = getWriteable(context);

        try {
            sql.execSQL("UPDATE PASSWORD SET username='" + userDetails.userName
                    + "' , email='" + userDetails.email
                    + "' , applicationType='" + userDetails.applicationType
                    + "' , password='" + ProcessData.Encrypt(userDetails.password, securityKey)
                    + "' , description='" + ProcessData.Encrypt(userDetails.description, securityKey)
                    + "' , number='" + ProcessData.Encrypt(userDetails.number, securityKey)
                    + "' , passwordcreatedDate='" + ProcessData.Encrypt(userDetails.passwordCreatedDate, securityKey)
                    + "' , passwordExpiryDate='" + ProcessData.Encrypt(userDetails.passwordExpiryDate, securityKey)
                    + "' , securityKey='" + ProcessData.Encrypt(userDetails.securityKey, securityKey)
                    + "' WHERE ID='" + ProcessData.Encrypt(userDetails.id, securityKey) + "'");
            return true;
        }
        catch (Exception e){
            return false;
        }


    }


    public static Cursor viewDetails(Context context)
    {
        String[] columns_to_view = {"id","applicationType","email","username","password","number","description","passwordcreatedDate","passwordExpiryDate","securityKey"};
        SQLiteDatabase sql = getReadable(context);
        Cursor cursor = sql.query("PASSWORD",columns_to_view,null,null,null,null,"applicationType");

        return cursor;
    }


}

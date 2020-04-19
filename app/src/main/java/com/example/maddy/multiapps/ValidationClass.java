package com.example.maddy.multiapps;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Maddy on 3/25/2020.
 */

public class ValidationClass {

    public static boolean checkSecurityKey(String key){
        if(key.length()>3 && key.length()<7){
            return true;
        }
        else
            return false;
    }



    public static boolean checkPassword(String password){
        if(password.length()>3 && password.length()<31){
            return true;
        }
        else
            return false;
    }

    public static boolean checkApplicationType(String application){
        if(application.length()>2 && application.length()<31){
            return true;
        }
        else
            return false;
    }



    public static boolean checkEmail(String email){
        if(email.length()>3 && email.length()<50){
            return true;
        }
        else
            return false;
    }



    public  static boolean checksAdd(UserDetails userDetails, Context context){

        boolean checks = false;
        checks = checkEmail(userDetails.email);
        if(checks == true) {
            checks = checkSecurityKey(userDetails.securityKey);
            if (checks == true) {
                checks = checkPassword(userDetails.password);
                if (checks == true) {
                    checks = checkApplicationType(userDetails.applicationType);
                    return checks;
                }
                else
                    return checks;
            }
            else
                return checks;
        }
        else{
            return checks;
        }

    }

    public static boolean checkId(String applicationType,String email,Context context){
        SQLiteDatabase sql = ProcessData.getReadable(context);

        Cursor c = sql.rawQuery("Select * from PASSWORD where applicationType = ? and email = ?", new String[] {applicationType,email});
        if(c.getCount() > 0){
            return false;
        }
        else
            return true;
    }

}

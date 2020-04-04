package com.example.maddy.multiapps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maddy on 3/22/2020.
 */

public class DetailsHelperClass extends SQLiteOpenHelper {

    DetailsHelperClass(Context context)
    {
        super(context,"password.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE PASSWORD ( id text primary key , applicationType text ,username text, email text , password text, number text, description text, passwordcreatedDate text, passwordExpiryDate text, securityKey text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PASSWORD");
    }
}

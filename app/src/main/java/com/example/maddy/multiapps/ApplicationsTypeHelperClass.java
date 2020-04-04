package com.example.maddy.multiapps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maddy on 3/27/2020.
 */

public class ApplicationsTypeHelperClass extends SQLiteOpenHelper {

    ApplicationsTypeHelperClass(Context context)
    {
        super(context,"application.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE application ( application text primary key)");
        sqLiteDatabase.execSQL("INSERT INTO APPLICATION ( application ) VALUES ( 'Instagram' )");
        sqLiteDatabase.execSQL("INSERT INTO APPLICATION ( application ) VALUES ( 'Facebook' )");
        sqLiteDatabase.execSQL("INSERT INTO APPLICATION ( application ) VALUES ( 'Gmail' )");
        sqLiteDatabase.execSQL("INSERT INTO APPLICATION ( application ) VALUES ( 'Twitter' )");
        sqLiteDatabase.execSQL("INSERT INTO APPLICATION ( application ) VALUES ( 'Yahoo' )");
        sqLiteDatabase.execSQL("INSERT INTO APPLICATION ( application ) VALUES ( 'Amazon' )");
        sqLiteDatabase.execSQL("INSERT INTO APPLICATION ( application ) VALUES ( 'Flipkart' )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS application");
    }

}

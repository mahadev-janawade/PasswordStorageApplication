package com.example.maddy.multiapps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maddy on 4/19/2020.
 */

public class MainDB extends SQLiteOpenHelper {
    MainDB(Context context)
    {
        super(context,"security.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE SECURITY ( id text primary key , securityKey text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SECURITY");
    }
}

package com.example.androidadvanced201819.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "profile.db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String DATABASE_CREATE_PROFILE =
            "CREATE TABLE profile(" +
                    ProfileDatabaseManager.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ProfileDatabaseManager.KEY_PROFILE_NAME + " TEXT, " +
                    ProfileDatabaseManager.KEY_OPTION_SELECTED + " INTEGER, " +
                    ProfileDatabaseManager.KEY_LUMINOSITY + " INTEGER, " +
                    ProfileDatabaseManager.KEY_VOLUME + " INTEGER, " +
                    ProfileDatabaseManager.KEY_BLUETHOOTH + " INTEGER, " +
                    ProfileDatabaseManager.KEY_WIFI + " INTEGER " +
                    ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_PROFILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

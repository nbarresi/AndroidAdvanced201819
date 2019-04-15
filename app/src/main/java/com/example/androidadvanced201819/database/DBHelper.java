package com.example.androidadvanced201819.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "profile.db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String DATABASE_CREATE_PROFILE =
            "CREATE TABLE profile(" +
                    ProfileDatabaseManager.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ProfileDatabaseManager.KEY_PROFILE_NAME + " TEXT, " +
                    ProfileDatabaseManager.KEY_OPTION_SELECTED + " INTEGER, " +
                    ProfileDatabaseManager.KEY_BRIGHTNESS + " INTEGER, " +
                    ProfileDatabaseManager.KEY_VOLUME + " INTEGER, " +
                    ProfileDatabaseManager.KEY_BLUETHOOTH + " INTEGER, " +
                    ProfileDatabaseManager.KEY_COORDINATES + " TEXT DEFAULT'', " +
                    ProfileDatabaseManager.KEY_WIFI + " INTEGER, " +
                    ProfileDatabaseManager.KEY_APPLICATION + " TEXT, " +
                    ProfileDatabaseManager.KEY_APPLICATION_NAME + " TEXT, " +
                    ProfileDatabaseManager.KEY_AUTOBRIGHTNESS + " INTEGER, " +
                    ProfileDatabaseManager.KEY_NFC + " TEXT DEFAULT '' " +
                    ");";

    public static final String DATABASE_CREATE_WIFI =
            "CREATE TABLE wifi(" +
                    WiFiDatabaseManager.KEY_SSID + " TEXT PRIMARY KEY, " +
                    WiFiDatabaseManager.KEY_BSSID + " TEXT, " +
                    WiFiDatabaseManager.KEY_LEVEL + " INTEGER " +
                    ");";

    public static final String DATABASE_CREATE_PROFILE_WIFI =
            "CREATE TABLE profileWifi(" +
                    ProfileWifiDatabaseManager.KEY_IDPROFILE + " INTEGER, " +
                    ProfileWifiDatabaseManager.KEY_BSSID + " TEXT, " +
                    "PRIMARY KEY (" + ProfileWifiDatabaseManager.KEY_IDPROFILE + ", " +
                    ProfileWifiDatabaseManager.KEY_BSSID + ")" +
                    ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_PROFILE);
        db.execSQL(DATABASE_CREATE_WIFI);
        db.execSQL(DATABASE_CREATE_PROFILE_WIFI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 7) {
            //db.execSQL("ALTER TABLE " + ProfileDatabaseManager.DATABASE_TABLE + " ADD COLUMN " + ProfileDatabaseManager.KEY_COORDINATES + " TEXT DEFAULT ''");
            db.execSQL("ALTER TABLE " + ProfileDatabaseManager.DATABASE_TABLE + " ADD COLUMN " + ProfileDatabaseManager.KEY_NFC + " TEXT DEFAULT ''");
        }
    }
}

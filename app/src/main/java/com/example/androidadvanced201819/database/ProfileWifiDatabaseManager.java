package com.example.androidadvanced201819.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProfileWifiDatabaseManager {

    private SQLiteDatabase database;
    private DBHelper databaseHelper;
    private Context context;

    public static final String DATABASE_TABLE = "profile_wifi";
    public static final String KEY_IDPROFILE = "idProfile";
    public static final String KEY_SSID = "ssid";

    public ProfileWifiDatabaseManager(Context context) {
        this.context = context;
    }

    public ProfileWifiDatabaseManager open() throws SQLException {
        databaseHelper = new DBHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    private ContentValues createContentValues(int idProfile, String wifi_ssid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_IDPROFILE, idProfile);
        contentValues.put(KEY_SSID, wifi_ssid);
        return contentValues;
    }

    public long createProfileWifi(int idProfile, String wifiSSID) {
        ContentValues initialValues = createContentValues(idProfile, wifiSSID);
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }

    public Cursor fetchAllProfileWifi() {
        return database.query(DATABASE_TABLE, null, null, null, null, null, null);
    }
}

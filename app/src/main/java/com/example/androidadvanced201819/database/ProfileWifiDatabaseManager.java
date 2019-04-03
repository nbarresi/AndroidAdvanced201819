package com.example.androidadvanced201819.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProfileWifiDatabaseManager {

    private SQLiteDatabase database;
    private DBHelper databaseHelper;
    private Context context;

    public static final String DATABASE_TABLE = "profileWifi";
    public static final String KEY_IDPROFILE = "idProfile";
    public static final String KEY_BSSID = "bssid";

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

    private ContentValues createContentValues(long idProfile, String wifi_bssid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_IDPROFILE, idProfile);
        contentValues.put(KEY_BSSID, wifi_bssid);
        return contentValues;
    }

    public long createProfileWifi(long idProfile, String wifiBSSID) {
        ContentValues initialValues = createContentValues(idProfile, wifiBSSID);
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }

    public void deleteProfileWifi(Integer idProfile) {
        database.delete(DATABASE_TABLE, KEY_IDPROFILE+"=?", new String[]{Integer.toString(idProfile)});
    }

    public Cursor fetchAllProfileWifi() {
        return database.query(DATABASE_TABLE, null, null, null, null, null, null);
    }
}

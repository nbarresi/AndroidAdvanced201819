package com.example.androidadvanced201819.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidadvanced201819.model.WiFi;

public class WiFiDatabaseManager {

    private SQLiteDatabase database;
    private DBHelper databaseHelper;
    private Context context;

    public static final String DATABASE_TABLE = "wifi";
    public static final String KEY_SSID = "ssid";
    public static final String KEY_BSSID = "bssid";
    public static final String KEY_LEVEL = "level";

    public WiFiDatabaseManager(Context context) {
        this.context = context;
    }

    public WiFiDatabaseManager open() throws SQLException {
        databaseHelper = new DBHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    private ContentValues createContentValues(String wifi_ssid, String wifi_bssid, int level) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_SSID, wifi_ssid);
        contentValues.put(KEY_BSSID, wifi_bssid);
        contentValues.put(KEY_LEVEL, level);
        return contentValues;
    }

    public long createWifi(WiFi wifi) {
        ContentValues initialValues = createContentValues(wifi.getSSID(), wifi.getBSSID(), wifi.getLevel());
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }

    public Cursor fetchAllWifi() {
        return database.query(DATABASE_TABLE, null, null, null, null, null, null);
    }
}

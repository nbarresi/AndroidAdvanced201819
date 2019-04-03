package com.example.androidadvanced201819.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidadvanced201819.model.WiFi;

import java.util.ArrayList;
import java.util.List;

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
        List<WiFi> wiFis = new ArrayList<>();
        Cursor cursor = fetchAllWifi();
        cursor.moveToFirst();
        int index = cursor.getCount();

        if (index > 0) {
            int i = 0;
            do {
                WiFi wiFi = new WiFi(
                        cursor.getString(cursor.getColumnIndex(KEY_BSSID)),
                        cursor.getString(cursor.getColumnIndex(KEY_SSID)),
                        cursor.getInt(cursor.getColumnIndex(KEY_LEVEL))
                );
                i++;
                wiFis.add(wifi);
                cursor.moveToNext();
            } while (i < index);
        }

        ContentValues initialValues = createContentValues(wifi.getSSID(), wifi.getBSSID(), wifi.getLevel());
        boolean checked = checkWiFi(wiFis, wifi.getBSSID());
        if (checked) {
            return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
        }
        return 0;
    }

    public Cursor fetchAllWifi() {
        return database.query(DATABASE_TABLE, null, null, null, null, null, null);
    }

    private boolean checkWiFi(List<WiFi> wiFis, String BSSID) {
        for (WiFi wiFi : wiFis) {
            if (wiFi.getBSSID().equals(BSSID)) {
                return false;
            }
        }
        return true;
    }
}

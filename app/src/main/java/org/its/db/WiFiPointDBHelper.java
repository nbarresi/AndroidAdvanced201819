package org.its.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.its.db.entities.Profile;
import org.its.db.entities.WiFiPoint;

import java.util.ArrayList;
import java.util.List;

public class WiFiPointDBHelper extends GenericDBHelper{



    public WiFiPointDBHelper(Context context) {
        super(context);
    }

    public WiFiPoint insertWiFiPoint(WiFiPoint wifiPoint){

        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(WiFiPoint.WifiPointEntry._BSSID, wifiPoint.getBSSID());
        values.put(WiFiPoint.WifiPointEntry._SSID, wifiPoint.getSSID());
        values.put(WiFiPoint.WifiPointEntry._LEVEL, wifiPoint.getLevel());

        // Insert the new row, returning the primary key value of the new row
        db.insert(WiFiPoint.WifiPointEntry.TABLE_NAME, null, values);



        return wifiPoint;
    }

    public boolean deleteWiFiPoint(WiFiPoint wifiPoint){
        String selection = WiFiPoint.WifiPointEntry._BSSID + " == ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { wifiPoint.getBSSID()+"" };
        // Issue SQL statement.
        int deletedRows = this.getWritableDatabase().delete(WiFiPoint.WifiPointEntry.TABLE_NAME, selection, selectionArgs);

        return (deletedRows > 0);

    }

    public boolean updateWiFiPoint(WiFiPoint wifiPoint){

        ContentValues values = new ContentValues();
        values.put(WiFiPoint.WifiPointEntry._SSID, wifiPoint.getSSID());
        values.put(WiFiPoint.WifiPointEntry._LEVEL, wifiPoint.getLevel());

// Which row to update, based on the title
        String selection = WiFiPoint.WifiPointEntry._BSSID+ " == ?";
        String[] selectionArgs = { wifiPoint.getBSSID()+"" };

        int count = this.getWritableDatabase().update(
                WiFiPoint.WifiPointEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return (count >0);
    }

    public List<WiFiPoint> getAllPoints(){

        Cursor cursor = this.getWritableDatabase().query(
                Profile.ProfileEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        return cursorToWiFiPoint(cursor);

    }

    public WiFiPoint getByBSSID(String BSSID){
        String selection = WiFiPoint.WifiPointEntry._BSSID+ " == ?";
        String[] selectionArgs = { BSSID};
        Cursor cursor = this.getWritableDatabase().query(
                Profile.ProfileEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        cursor.moveToFirst();

        WiFiPoint wifiPoint = new WiFiPoint(
                cursor.getString(cursor.getColumnIndexOrThrow(WiFiPoint.WifiPointEntry._BSSID)),
                cursor.getString(cursor.getColumnIndexOrThrow(WiFiPoint.WifiPointEntry._SSID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(WiFiPoint.WifiPointEntry._LEVEL))
        );

        return wifiPoint;
    }

    private List<WiFiPoint> cursorToWiFiPoint(Cursor cursor){
        List<WiFiPoint> wifiPoints = new ArrayList<>();

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {

            WiFiPoint wifiPoint = new WiFiPoint(
                    cursor.getString(cursor.getColumnIndexOrThrow(WiFiPoint.WifiPointEntry._BSSID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(WiFiPoint.WifiPointEntry._SSID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(WiFiPoint.WifiPointEntry._LEVEL))
            );

            wifiPoints.add(wifiPoint);
        }
        return wifiPoints;
    }


}

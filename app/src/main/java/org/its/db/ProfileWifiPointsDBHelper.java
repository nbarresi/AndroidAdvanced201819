package org.its.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.its.db.entities.ProfileWiFiPoints;
import org.its.db.entities.WiFiPoint;

import java.util.ArrayList;
import java.util.List;

public class ProfileWifiPointsDBHelper extends GenericDBHelper {

    WiFiPointDBHelper wifiHelper;

    public ProfileWifiPointsDBHelper(Context context) {
        super(context);
        wifiHelper = new WiFiPointDBHelper(context);
    }

    public ProfileWiFiPoints insertProfileWiFiPoint(ProfileWiFiPoints wiFiPoints){

        SQLiteDatabase db = getWritableDatabase();

        for(WiFiPoint wifiPoint : wiFiPoints.getWiFiPoints()){
            Log.w("BSSID", wifiPoint.getBSSID());
            ContentValues values = new ContentValues();
            values.put(ProfileWiFiPoints.ProfileWiFiPointsEntry._ID, wiFiPoints.getIdProfile());
            values.put(ProfileWiFiPoints.ProfileWiFiPointsEntry._BSSID, wifiPoint.getBSSID());

            if(wifiHelper.getByBSSID(wifiPoint.getBSSID()) == null)
                wifiHelper.insertWiFiPoint(wifiPoint);
            else
                wifiHelper.updateWiFiPoint(wifiPoint);

            db.insert(ProfileWiFiPoints.ProfileWiFiPointsEntry.TABLE_NAME, null, values);

        }

        return wiFiPoints;
    }

    public boolean deleteProfileWifiPoints(ProfileWiFiPoints wiFiPoints){
        String selection = ProfileWiFiPoints.ProfileWiFiPointsEntry._ID + " == ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {wiFiPoints.getIdProfile()+"" };
        // Issue SQL statement.
        int deletedRows = this.getWritableDatabase().delete(WiFiPoint.WifiPointEntry.TABLE_NAME, selection, selectionArgs);

        return (deletedRows > 0);
    }

    public boolean updateProfileWifiPoints(ProfileWiFiPoints wiFiPoints){
        deleteProfileWifiPoints(wiFiPoints);
        insertProfileWiFiPoint(wiFiPoints);
        return true;
    }

    public ProfileWiFiPoints getProfileWiFiPoints(Long idProfile){
        String selection = ProfileWiFiPoints.ProfileWiFiPointsEntry._ID + " == ?";
        String[] selectionArgs = {idProfile+"" };
        Cursor cursor = this.getWritableDatabase().query(
                ProfileWiFiPoints.ProfileWiFiPointsEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        ProfileWiFiPoints profileWiFiPoints = new ProfileWiFiPoints() ;
        profileWiFiPoints.setIdProfile(idProfile);

        List<WiFiPoint> wifiPoints = new ArrayList<>();

        while(cursor.moveToNext()) {
            WiFiPoint wiFiPoint = wifiHelper.getByBSSID(cursor.getString(cursor.getColumnIndexOrThrow(ProfileWiFiPoints.ProfileWiFiPointsEntry._BSSID)));
            wifiPoints.add(wiFiPoint);
        }

        profileWiFiPoints.setWiFiPoints(wifiPoints);

        return profileWiFiPoints;

    }

}

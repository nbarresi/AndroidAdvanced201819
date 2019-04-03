package org.its.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.its.db.entities.ProfileWiFiPoints;
import org.its.db.entities.WiFiPoint;

public class ProfileWifiPointsDBHelper extends GenericDBHelper {

    WiFiPointDBHelper wifiHelper;

    public ProfileWifiPointsDBHelper(Context context) {
        super(context);
        wifiHelper = new WiFiPointDBHelper(context);
    }

    public ProfileWiFiPoints insertProfileWiFiPoint(ProfileWiFiPoints wiFiPoints){

        SQLiteDatabase db = getWritableDatabase();

        for(WiFiPoint wifiPoint : wiFiPoints.getWiFiPoints()){
            ContentValues values = new ContentValues();
            values.put(ProfileWiFiPoints.ProfileWiFiPointsEntry._ID, wiFiPoints.getIdProfile());
            values.put(ProfileWiFiPoints.ProfileWiFiPointsEntry._BSSID, wifiPoint.getBSSID());

            // Insert the new row, returning the primary key value of the new row
            db.insert(ProfileWiFiPoints.ProfileWiFiPointsEntry.TABLE_NAME, null, values);

            wifiHelper.insertWiFiPoint(wifiPoint);
        }

        return wiFiPoints;
    }



}

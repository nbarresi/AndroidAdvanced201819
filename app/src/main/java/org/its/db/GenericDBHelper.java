package org.its.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.its.db.entities.Coordinates;
import org.its.db.entities.Profile;
import org.its.db.entities.WiFiPoint;

public abstract class GenericDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ProfileManager.db";

    private static final String SQL_CREATE_COORDINATES =
            "CREATE TABLE " + Coordinates.CoordinatesEntry.TABLE_NAME + " (" +
                    Coordinates.CoordinatesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Coordinates.CoordinatesEntry._LATITUDE + " TEXT," +
                    Coordinates.CoordinatesEntry._LONGITUDE + " TEXT," +
                    Coordinates.CoordinatesEntry._RADIUS + " INTEGER," +
                    Coordinates.CoordinatesEntry._IDPROFILE + " INTEGER)";

    private static final String SQL_CREATE_PROFILE =
            "CREATE TABLE " + Profile.ProfileEntry.TABLE_NAME + " (" +
                    Profile.ProfileEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Profile.ProfileEntry._NAME + " TEXT," +
                    Profile.ProfileEntry._METODO_RILEVAMENTO + " TEXT," +
                    Profile.ProfileEntry._LUMINOSITA + " INTEGER," +
                    Profile.ProfileEntry._VOLUME + " INTEGER," +
                    Profile.ProfileEntry._BLUETOOTH + " BOOLEAN," +
                    Profile.ProfileEntry._APP + " TEXT)";

    private static final String SQL_CREATE_WIFIPOINT=
            "CREATE TABLE " + WiFiPoint.WifiPointEntry.TABLE_NAME + " (" +
                    WiFiPoint.WifiPointEntry._BSSID + " TEXT PRIMARY KEY ," +
                    WiFiPoint.WifiPointEntry._SSID+ " TEXT," +
                    WiFiPoint.WifiPointEntry._LEVEL + " INTEGER)";

    public GenericDBHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PROFILE);
        db.execSQL(SQL_CREATE_COORDINATES);
        db.execSQL(SQL_CREATE_WIFIPOINT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}

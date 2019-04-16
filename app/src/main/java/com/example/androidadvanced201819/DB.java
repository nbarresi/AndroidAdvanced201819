package com.example.androidadvanced201819;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "profile.db";
    private static final String PROFILE_TABLE_NAME ="settings";
    private static final String PROFILE_COLUMN_ID ="id";
    protected static final String PROFILE_COLUMN_PROFILE_NAME ="profileName";
    private static final String PROFILE_COLUMN_APP="profileSurname";


    public static final String LATLNG_TABLE="latlng";
    public static final String LATLNG_COLUMN_ID="id";
    public static final String LATLNG_COLUMN_LATITUDINE="latitudine";
    public static final String LATLNG_COLUMN_LONGITUDINE="longitudine";
    public static final String LATLNG_COLUMN_RAGGIO="raggio";

    public static final String WIFI_TABLE="wifi";
    public static final String WIFI_COLUMN_ID="id";
    public static final String WIFI_COLUMN_NOME_RETE="nomeRete";
    public static final String WIFI_COLUMN_CODICE_WIFI="codiceWifi";
    public static final String WIFI_COLUMN_SEGNALE_WIFI="segnaleWifi";

    private static final String CREATE_TABLE_WIFI=
            "CREATE TABLE "+WIFI_TABLE +
                    "("+WIFI_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +WIFI_COLUMN_NOME_RETE +" TEXT, "
                    +WIFI_COLUMN_CODICE_WIFI+" TEXT, "
                    +WIFI_COLUMN_SEGNALE_WIFI+ " INTEGER)";

    public static final String CREATE_TABLE_LATLNG=
            "CREATE TABLE "+LATLNG_TABLE +
                    "("+LATLNG_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +LATLNG_COLUMN_LATITUDINE +" TEXT, "
                    +LATLNG_COLUMN_LONGITUDINE +" TEXT, "
                    +LATLNG_COLUMN_RAGGIO +" INTEGER)";


    private static final String CREATE_TABLE_SETTINGS=
            "CREATE TABLE "+PROFILE_TABLE_NAME +
            "("+PROFILE_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +PROFILE_COLUMN_PROFILE_NAME +" TEXT)";

    private static final String CREATE_TABLE_PROVA=
            "CREATE TABLE prova(id INTEGER PRIMARY KEY AUTOINCREMENT,nome text)";

    public static final String SELECT_PROFILE_TABLE_NAME="SELECT * FROM "+PROFILE_TABLE_NAME;
    public static final String SELECT_TABLE_LATLNG="SELECT * FROM "+LATLNG_TABLE;


    public DB(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SETTINGS);
        db.execSQL(CREATE_TABLE_LATLNG);
        db.execSQL(CREATE_TABLE_WIFI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<2){
            db.execSQL(CREATE_TABLE_PROVA);
            onCreate(db);
        }
    }

    public boolean addData(String item){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PROFILE_COLUMN_PROFILE_NAME,item);
        long result=db.insert(PROFILE_TABLE_NAME,null,contentValues);
        return resultAddDb(result);

    }



    public boolean addLatLng(Location location){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        LatLngClass latLngClass = null;
        contentValues.put(LATLNG_COLUMN_LATITUDINE,latLngClass.getLatitudine(location.getLatitude()));
        contentValues.put(LATLNG_COLUMN_LONGITUDINE,latLngClass.getLongitudine(location.getLongitude()));
        long result=db.insert(LATLNG_TABLE,null,contentValues);
        return resultAddDb(result);
    }

    public Cursor getDataLatLng(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data=db.rawQuery(SELECT_TABLE_LATLNG,null);
        return data;
    }

    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data=db.rawQuery(SELECT_PROFILE_TABLE_NAME,null);
        return data;

    }

    private boolean resultAddDb(long result) {
        if (result==-1){
            return false;
        }else{
            return true;
        }
    }



}

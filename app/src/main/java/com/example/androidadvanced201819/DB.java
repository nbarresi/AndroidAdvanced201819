package com.example.androidadvanced201819;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

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

  /*  public static final String WIFI_TABLE="wifi";
    public static final String WIFI_COLUMN_ID="id";*/

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


    public static final String SELECT_PROFILE_TABLE_NAME="SELECT * FROM "+PROFILE_TABLE_NAME;

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SETTINGS);
        db.execSQL(CREATE_TABLE_LATLNG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+PROFILE_TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PROFILE_COLUMN_PROFILE_NAME,item);
        long result=db.insert(PROFILE_TABLE_NAME,null,contentValues);
        if (result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data=db.rawQuery(SELECT_PROFILE_TABLE_NAME,null);
        return data;

    }





}

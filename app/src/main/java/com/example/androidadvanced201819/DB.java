package com.example.androidadvanced201819;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DATABASE_NAME = "profile.db";
    public static final String PROFILE_TABLE_NAME ="settings";
    public static final String PROFILE_COLUMN_ID ="id";
    public static final String PROFILE_COLUMN_PROFILE_NAME ="profileName";
    public static final String PROFILE_COLUMN_APP="profileSurname";

    public static final String CREATE_TABLE_SETTINGS=
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

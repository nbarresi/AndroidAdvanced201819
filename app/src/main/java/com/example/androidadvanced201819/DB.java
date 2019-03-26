package com.example.androidadvanced201819;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

public class DB extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DATABASE_NAME = "profile.db";
    public static final String PROFILE_TABLE_NAME ="settings";
    public static final String PROFILE_COLUMN_ID ="id";
    public static final String PROFILE_COLUMN_PROFILE_NAME ="profileName";
    public static final String PROFILE_COLUMN_APP="profileSurname";
    public DB(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String nuova_tabella=
                "CREATE TABLE "+PROFILE_TABLE_NAME +
                        "("+PROFILE_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                        +PROFILE_COLUMN_PROFILE_NAME +" TEXT)";
        db.execSQL(nuova_tabella);

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
        String query="SELECT * FROM "+PROFILE_TABLE_NAME;
        Cursor data=db.rawQuery(query,null);
        return data;
    }

}

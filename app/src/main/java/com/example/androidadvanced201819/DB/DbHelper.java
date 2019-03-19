package com.example.androidadvanced201819.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.androidadvanced201819.UserProfile;

import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "AndroidAndvanced.db";

    public static final String PROFILE_TABLE_NAME = "profile";
//    public static final String METODO_RILEVAMENTO_TABLE= "metodo_rilevamento";

    public static final String GENERIC_COLUMN_ID = "id";

    public static final String PROFILE_COLUMN_UTENTE = "utente";
    public static final String PROFILE_COLUMN_LUMINOSITA = "luminosita";
    public static final String PROFILE_COLUMN_VOLUME = "volume";
    public static final String PROFILE_COLUMN_BLUETOOTH = "bluetooth";
    public static final String PROFILE_COLUMN_WIFI = "wifi";

//    public static final String METODO_RILEVAMENTO_COLUMN_TIPO = "tipo";
//    public static final String METODO_RILEVAMENTO_COLUMN_VALORE = "valore";
//    public static final String METODO_RILEVAMENTO_COLUMN_ID_PROFILO = "id_profilo";

    private static final String CREATE_TABLE_PROFILO = "CREATE TABLE " + PROFILE_TABLE_NAME + "(" +
            GENERIC_COLUMN_ID + " INTEGER PRIMARY KEY," +
            PROFILE_COLUMN_LUMINOSITA + " INTEGER," +
            PROFILE_COLUMN_VOLUME + " INTEGER," +
            PROFILE_COLUMN_BLUETOOTH + " INTEGER," +
            PROFILE_COLUMN_WIFI + " INTEGER," +
            PROFILE_COLUMN_UTENTE+ " TEXT )";

//    private static final String CREATE_TABLE_METODO_RILEVAMENTO = "CREATE TABLE " + METODO_RILEVAMENTO_TABLE + "(" +
//            GENERIC_COLUMN_ID + " INTEGER PRIMARY KEY," +
//            METODO_RILEVAMENTO_COLUMN_TIPO + " TEXT," +
//            METODO_RILEVAMENTO_COLUMN_VALORE + " TEXT,"+
//            METODO_RILEVAMENTO_COLUMN_ID_PROFILO+" INTEGER, " +
//            "FOREIGN KEY("+METODO_RILEVAMENTO_COLUMN_ID_PROFILO+") REFERENCES "+PROFILE_TABLE_NAME+"("+GENERIC_COLUMN_ID+"))";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROFILO);
//        db.execSQL(CREATE_TABLE_METODO_RILEVAMENTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2){

        }
    }

    public void insertProfile (UserProfile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_COLUMN_UTENTE, "nbarresi");//da cambiare se si inserisce la register
        contentValues.put(PROFILE_COLUMN_LUMINOSITA, profile.getLuminosita());
        contentValues.put(PROFILE_COLUMN_VOLUME, profile.getVolume());
        contentValues.put(PROFILE_COLUMN_BLUETOOTH, profile.isBluetooth()? 1 : 0);
        contentValues.put(PROFILE_COLUMN_WIFI, profile.isWifi()? 1 : 0);
        db.insert(PROFILE_TABLE_NAME, null, contentValues);
    }
}

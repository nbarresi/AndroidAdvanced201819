package com.example.androidadvanced201819.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.androidadvanced201819.DB.Entities.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    public static final String DATABASE_NAME = "AndroidAndvanced.db";

    public static final String PROFILE_TABLE_NAME = "profile";
    public static final String WIFI_TABLE= "wifi";

    public static final String GENERIC_COLUMN_ID = "id";

    public static final String PROFILE_COLUMN_UTENTE = "utente";
    public static final String PROFILE_COLUMN_NOME = "nome";
    public static final String PROFILE_COLUMN_METODO = "metodo";
    public static final String PROFILE_COLUMN_VALORE_METODO = "valoreM";
    public static final String PROFILE_COLUMN_APP = "app";
    public static final String PROFILE_COLUMN_APP_NAME = "appName";
    public static final String PROFILE_COLUMN_LUMINOSITA = "luminosita";
    public static final String PROFILE_COLUMN_VOLUME = "volume";
    public static final String PROFILE_COLUMN_BLUETOOTH = "bluetooth";
    public static final String PROFILE_COLUMN_WIFI = "wifi";

    public static final String WIFI_COLUMN_BSSID = "bssid";
    public static final String WIFI_COLUMN_LEVEL = "level";
    public static final String WIFI_COLUMN_ID_PROFILO = "id_profilo";

    private static final String CREATE_TABLE_PROFILO = "CREATE TABLE " + PROFILE_TABLE_NAME + "(" +
            GENERIC_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PROFILE_COLUMN_NOME+ " TEXT," +
            PROFILE_COLUMN_METODO+ " TEXT," +
            PROFILE_COLUMN_VALORE_METODO+ " TEXT," +
            PROFILE_COLUMN_APP+ " TEXT," +
            PROFILE_COLUMN_LUMINOSITA + " INTEGER," +
            PROFILE_COLUMN_VOLUME + " INTEGER," +
            PROFILE_COLUMN_BLUETOOTH + " INTEGER," +
            PROFILE_COLUMN_WIFI + " INTEGER," +
            PROFILE_COLUMN_UTENTE+ " TEXT)";
//
    private static final String CREATE_TABLE_WIFI = "CREATE TABLE " + WIFI_TABLE + "(" +
            GENERIC_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            WIFI_COLUMN_BSSID + " TEXT," +
            WIFI_COLUMN_LEVEL + " INTEGER,"+
            WIFI_COLUMN_ID_PROFILO+" INTEGER, " +
            "FOREIGN KEY("+WIFI_COLUMN_ID_PROFILO+") REFERENCES "+PROFILE_TABLE_NAME+"("+GENERIC_COLUMN_ID+"))";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROFILO);
        db.execSQL(CREATE_TABLE_WIFI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2){
            String ADD_METODO="ALTER TABLE "+PROFILE_TABLE_NAME+" ADD COLUMN "+PROFILE_COLUMN_METODO+" TEXT;";
            String ADD_VALORE_METODO="ALTER TABLE "+PROFILE_TABLE_NAME+" ADD COLUMN "+PROFILE_COLUMN_VALORE_METODO+" TEXT;";
            String ADD_APP="ALTER TABLE "+PROFILE_TABLE_NAME+" ADD COLUMN "+ PROFILE_COLUMN_APP +" TEXT;";
            db.execSQL(ADD_METODO);
            db.execSQL(ADD_VALORE_METODO);
            db.execSQL(ADD_APP);
        }
        if(oldVersion < 3){
            String ADD_APPNAME = "ALTER TABLE "+PROFILE_TABLE_NAME+" ADD COLUMN "+ PROFILE_COLUMN_APP_NAME +" TEXT;";
            db.execSQL(ADD_APPNAME);
        }
    }

    public void insertProfile (UserProfile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_COLUMN_UTENTE, "nbarresi");
        contentValues.put(PROFILE_COLUMN_NOME, profile.getNome());
        contentValues.put(PROFILE_COLUMN_METODO, profile.getMetodoDiRilevamento());
        contentValues.put(PROFILE_COLUMN_VALORE_METODO, profile.getValoreMetodo());
        contentValues.put(PROFILE_COLUMN_APP_NAME, profile.getAppName());
        contentValues.put(PROFILE_COLUMN_APP, profile.getAppPackage());
        contentValues.put(PROFILE_COLUMN_LUMINOSITA, profile.getLuminosita());
        contentValues.put(PROFILE_COLUMN_VOLUME, profile.getVolume());
        contentValues.put(PROFILE_COLUMN_BLUETOOTH, profile.isBluetooth()? 1 : 0);
        contentValues.put(PROFILE_COLUMN_WIFI, profile.isWifi()? 1 : 0);
        long idProfilo= db.insertOrThrow(PROFILE_TABLE_NAME, null, contentValues);
    }

    public List<UserProfile> getProfiles(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(PROFILE_TABLE_NAME,null,null,null,null,null,null);
        List<UserProfile> profiles = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(GENERIC_COLUMN_ID));
                    String nome = cursor.getString(cursor.getColumnIndex(PROFILE_COLUMN_NOME));
                    String metodo = cursor.getString(cursor.getColumnIndex(PROFILE_COLUMN_METODO));
                    String valMetodo = cursor.getString(cursor.getColumnIndex(PROFILE_COLUMN_VALORE_METODO));
                    String appName = cursor.getString(cursor.getColumnIndex(PROFILE_COLUMN_APP_NAME));
                    String appPackage = cursor.getString(cursor.getColumnIndex(PROFILE_COLUMN_APP));
                    int luminosita = cursor.getInt(cursor.getColumnIndex(PROFILE_COLUMN_LUMINOSITA));
                    int volume = cursor.getInt(cursor.getColumnIndex(PROFILE_COLUMN_VOLUME));
                    boolean bluetooth = cursor.getInt(cursor.getColumnIndex(PROFILE_COLUMN_BLUETOOTH))==1;
                    boolean wifi = cursor.getInt(cursor.getColumnIndex(PROFILE_COLUMN_WIFI))==1;
                    profiles.add(new UserProfile(id,nome,metodo,valMetodo,luminosita,volume,bluetooth,wifi,appPackage,appName));
                } while (cursor.moveToNext());
            }
        }
        return profiles;
    }

    public boolean removeProfile(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PROFILE_TABLE_NAME,GENERIC_COLUMN_ID + "=?", new String[]{id+""})!=-1;
    }

    public void updateProfile(UserProfile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_COLUMN_UTENTE, "nbarresi");
        contentValues.put(PROFILE_COLUMN_NOME, profile.getNome());
        contentValues.put(PROFILE_COLUMN_METODO, profile.getMetodoDiRilevamento());
        contentValues.put(PROFILE_COLUMN_VALORE_METODO, profile.getValoreMetodo());
        contentValues.put(PROFILE_COLUMN_APP_NAME, profile.getAppName());
        contentValues.put(PROFILE_COLUMN_APP, profile.getAppPackage());
        contentValues.put(PROFILE_COLUMN_LUMINOSITA, profile.getLuminosita());
        contentValues.put(PROFILE_COLUMN_VOLUME, profile.getVolume());
        contentValues.put(PROFILE_COLUMN_BLUETOOTH, profile.isBluetooth()? 1 : 0);
        contentValues.put(PROFILE_COLUMN_WIFI, profile.isWifi()? 1 : 0);
        db.update(PROFILE_TABLE_NAME,contentValues,GENERIC_COLUMN_ID + "=?", new String[]{profile.getId()+""});
    }
}

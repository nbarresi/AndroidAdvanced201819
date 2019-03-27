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

    private static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "AndroidAndvanced.db";

    public static final String PROFILE_TABLE_NAME = "profile";
//    public static final String METODO_RILEVAMENTO_TABLE= "metodo_rilevamento";

    public static final String GENERIC_COLUMN_ID = "id";

    public static final String PROFILE_COLUMN_UTENTE = "utente";
    public static final String PROFILE_COLUMN_NOME = "nome";
    public static final String PROFILE_COLUMN_METODO = "metodo";
    public static final String PROFILE_COLUMN_APP = "app";
    public static final String PROFILE_COLUMN_LUMINOSITA = "luminosita";
    public static final String PROFILE_COLUMN_VOLUME = "volume";
    public static final String PROFILE_COLUMN_BLUETOOTH = "bluetooth";
    public static final String PROFILE_COLUMN_WIFI = "wifi";

//    public static final String METODO_RILEVAMENTO_COLUMN_TIPO = "tipo";
//    public static final String METODO_RILEVAMENTO_COLUMN_VALORE = "valore";
//    public static final String METODO_RILEVAMENTO_COLUMN_ID_PROFILO = "id_profilo";

    private static final String CREATE_TABLE_PROFILO = "CREATE TABLE " + PROFILE_TABLE_NAME + "(" +
            GENERIC_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PROFILE_COLUMN_NOME+ " TEXT," +
            PROFILE_COLUMN_METODO+ " TEXT," +
            PROFILE_COLUMN_APP+ " TEXT," +
            PROFILE_COLUMN_LUMINOSITA + " INTEGER," +
            PROFILE_COLUMN_VOLUME + " INTEGER," +
            PROFILE_COLUMN_BLUETOOTH + " INTEGER," +
            PROFILE_COLUMN_WIFI + " INTEGER," +
            PROFILE_COLUMN_UTENTE+ " TEXT)";
//
//    private static final String CREATE_TABLE_METODO_RILEVAMENTO = "CREATE TABLE " + METODO_RILEVAMENTO_TABLE + "(" +
//            GENERIC_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
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
            String ADD_METODO="ALTER TABLE "+PROFILE_TABLE_NAME+" ADD COLUMN "+PROFILE_COLUMN_METODO+" TEXT;";
            String ADD_APP="ALTER TABLE "+PROFILE_TABLE_NAME+" ADD COLUMN "+PROFILE_COLUMN_APP+" TEXT;";
            db.execSQL(ADD_METODO);
            db.execSQL(ADD_APP);
        }
    }

    public void insertProfile (UserProfile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_COLUMN_UTENTE, "nbarresi");//da cambiare se si inserisce la register
        contentValues.put(PROFILE_COLUMN_NOME, profile.getNome());//da cambiare se si inserisce la register
        contentValues.put(PROFILE_COLUMN_METODO, profile.getMetodoDiRilevamento());//da cambiare se si inserisce la register
        contentValues.put(PROFILE_COLUMN_APP, profile.getAppPackage());//da cambiare se si inserisce la register
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
                    String app = cursor.getString(cursor.getColumnIndex(PROFILE_COLUMN_APP));
                    int luminosita = cursor.getInt(cursor.getColumnIndex(PROFILE_COLUMN_LUMINOSITA));
                    int volume = cursor.getInt(cursor.getColumnIndex(PROFILE_COLUMN_VOLUME));
                    boolean bluetooth = cursor.getInt(cursor.getColumnIndex(PROFILE_COLUMN_BLUETOOTH))==1;
                    boolean wifi = cursor.getInt(cursor.getColumnIndex(PROFILE_COLUMN_WIFI))==1;
                    profiles.add(new UserProfile(id,nome,metodo,luminosita,volume,bluetooth,wifi,app));
                } while (cursor.moveToNext());
            }
        }
        return profiles;
    }

    public boolean removeProfile(UserProfile profile){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PROFILE_TABLE_NAME,GENERIC_COLUMN_ID + "=?", new String[]{profile.getId()+""})!=-1;
    }

}

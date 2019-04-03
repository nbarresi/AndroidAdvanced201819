package org.its.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "deviceProfile.db";
    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_CREATE = "CREATE TABLE profilo (id Integer primary key AUTOINCREMENT, nome varchar(50), " +
            "volume integer DEFAULT 50, luminosita integer DEFAULT 50, auto_luminosita integer DEFAULT 0, " +
            " metodo integer NOT NULL, bluetooth integer DEFAULT 0, wifi integer DEFAULT 0, rilevazione varchar(255)," +
            " app varchar(250),isactive integer DEFAULT 0);";
    private static final String DATABASE_CREATE2 = "CREATE TABLE wifi (bssid VARCHAR(50) primary key, ssid VARCHAR(50), potenza integer);";
    private static final String DATABASE_CREATE3 = "CREATE TABLE profilo_wifi(id_profilo integer,bssid varchar(50), " +
            "primary key (id_profilo,bssid), " +
            "FOREIGN KEY(id_profilo) REFERENCES profilo(id), " +
            "FOREIGN KEY(bssid) REFERENCES wifi(bssid)" +
            ")";

    // Costruttore
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
        sqLiteDatabase.execSQL(DATABASE_CREATE2);
        sqLiteDatabase.execSQL(DATABASE_CREATE3);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            sqLiteDatabase.execSQL(DATABASE_CREATE2);
            sqLiteDatabase.execSQL(DATABASE_CREATE3);
        }else if(oldVersion<3){
            sqLiteDatabase.execSQL("ALTER TABLE profilo ADD COLUMN isactive integer DEFAULT 0 ");
        }
    }
}

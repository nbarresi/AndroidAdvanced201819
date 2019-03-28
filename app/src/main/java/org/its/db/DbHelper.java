package org.its.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "deviceProfile.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE profilo (id Integer primary key AUTOINCREMENT, nome varchar(50), volume integer DEFAULT 50, luminosita integer DEFAULT 50, auto_luminosita integer DEFAULT 0,  metodo integer NOT NULL, bluetooth integer DEFAULT 0, wifi integer DEFAULT 0, rilevazione varchar(255) NOT NULL, app varchar(250));";
    //private static final String DATABASE_CREATE4 = "CREATE TABLE list_product (id_list_fk integer, _id integer, quantita integer,PRIMARY KEY(id_list_fk,_id) ,FOREIGN KEY (id_list_fk) REFERENCES list(_id) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY (_id) REFERENCES product(_id) ON UPDATE CASCADE ON DELETE CASCADE);";

    // Costruttore
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion<1){}
    }
}

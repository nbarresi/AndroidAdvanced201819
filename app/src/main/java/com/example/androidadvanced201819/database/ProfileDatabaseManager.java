package com.example.androidadvanced201819.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProfileDatabaseManager {

    private SQLiteDatabase database;
    private DBHelper databaseHelper;
    private Context context;

    public static final String DATABASE_TABLE = "profile";
    public static final String KEY_ID = "id";
    public static final String KEY_PROFILE_NAME = "name";
    public static final String KEY_OPTION_SELECTED = "option";
    public static final String KEY_LUMINOSITY = "luminosita";
    public static final String KEY_VOLUME = "volume";
    public static final String KEY_BLUETHOOTH = "bluethoot";
    public static final String KEY_WIFI = "wifi";

    public ProfileDatabaseManager(Context context){
        this.context = context;
    }

    public ProfileDatabaseManager open() throws SQLException {
        databaseHelper = new DBHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }


    private ContentValues createContentValues(String profile_name, int option, int luminosity, int volume, int bluethoot, int wifi) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PROFILE_NAME, profile_name);
        contentValues.put(KEY_OPTION_SELECTED, option);
        contentValues.put(KEY_LUMINOSITY, luminosity);
        contentValues.put(KEY_VOLUME, volume);
        contentValues.put(KEY_BLUETHOOTH, bluethoot);
        contentValues.put(KEY_WIFI, wifi);

        return contentValues;
    }

    public long createItem(String profile_name, int option, int luminosity, int volume, int bluethoot, int wifi) {
        ContentValues initialValues = createContentValues(profile_name, option, luminosity, volume, bluethoot, wifi);
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }

    public Cursor fetchAllItems() {
        return database.query(DATABASE_TABLE, null, null, null, null, null, null);
    }

    public Cursor readItem(int id) {
        String[] columns = new String[]{"*"};
        return database.query(DATABASE_TABLE, columns, "_id = '" + id + "'", null, null, null, null);
    }

}

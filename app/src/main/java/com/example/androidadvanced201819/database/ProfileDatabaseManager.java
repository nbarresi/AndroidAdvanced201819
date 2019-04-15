package com.example.androidadvanced201819.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidadvanced201819.model.Profile;

public class ProfileDatabaseManager {

    private SQLiteDatabase database;
    private DBHelper databaseHelper;
    private Context context;

    public static final String DATABASE_TABLE = "profile";
    public static final String KEY_ID = "id";
    public static final String KEY_PROFILE_NAME = "name";
    public static final String KEY_OPTION_SELECTED = "option";
    public static final String KEY_BRIGHTNESS = "luminosita";
    public static final String KEY_VOLUME = "volume";
    public static final String KEY_BLUETHOOTH = "bluethoot";
    public static final String KEY_WIFI = "wifi";
    public static final String KEY_APPLICATION = "application";
    public static final String KEY_APPLICATION_NAME = "application_name";
    public static final String KEY_AUTOBRIGHTNESS = "autobrightness";
    public static final String KEY_COORDINATES = "coordinate";
    public static final String KEY_NFC = "nfc";


    public ProfileDatabaseManager(Context context) {
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


    private ContentValues createContentValues(String profile_name, int option, int brightness, int volume, int bluethoot, int wifi, String application, String application_name, int autoBrightness, String coordinates, String nfc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PROFILE_NAME, profile_name);
        contentValues.put(KEY_OPTION_SELECTED, option);
        contentValues.put(KEY_BRIGHTNESS, brightness);
        contentValues.put(KEY_VOLUME, volume);
        contentValues.put(KEY_BLUETHOOTH, bluethoot);
        contentValues.put(KEY_WIFI, wifi);
        contentValues.put(KEY_APPLICATION, application);
        contentValues.put(KEY_APPLICATION_NAME, application_name);
        contentValues.put(KEY_AUTOBRIGHTNESS, autoBrightness);
        contentValues.put(KEY_COORDINATES, coordinates);
        contentValues.put(KEY_NFC, nfc);

        return contentValues;
    }

    public long createProfile(Profile profile) {
        ContentValues initialValues = createContentValues(profile.getNome(), profile.getOption(), profile.getBrightness(), profile.getVolume(), profile.getBluethoot(), profile.getWifi(), profile.getApplication(), profile.getApplicationName(), profile.getAuto_birghtness(), profile.getCoordinate(), profile.getNfc());
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }

    public void deleteProfile(String name) {
        database.delete(DATABASE_TABLE, KEY_PROFILE_NAME + "=?", new String[]{name});
    }

    public Cursor fetchAllProfile() {
        return database.query(DATABASE_TABLE, null, null, null, null, null, null);
    }

    public void editProfile(Profile profile) {
        ContentValues initialValues = createContentValues(profile.getNome(), profile.getOption(), profile.getBrightness(), profile.getVolume(), profile.getBluethoot(), profile.getWifi(), profile.getApplication(), profile.getApplicationName(), profile.getAuto_birghtness(), profile.getCoordinate(), profile.getNfc());
        database.update(DATABASE_TABLE, initialValues, "id=" + profile.getId(), null);
    }

    public Cursor fetchAllWifiProfile() {
        return database.query(DATABASE_TABLE, null, KEY_OPTION_SELECTED + "=?", new String[]{String.valueOf(2)}, null, null, null);
    }
}


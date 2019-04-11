package org.its.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.its.db.entities.Coordinates;
import org.its.db.entities.Profile;
import org.its.db.entities.ProfileWiFiPoints;

import java.util.ArrayList;
import java.util.List;

public class ProfileDBHelper extends GenericDBHelper {

    private static final String SQL_DELETE_PROFILE =
            "DROP TABLE IF EXISTS " + Profile.ProfileEntry.TABLE_NAME;

    public ProfileDBHelper(Context context){
        super(context);
    }

    public Profile insertProfile(Profile profile){

        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Profile.ProfileEntry._NAME, profile.getName());
        values.put(Profile.ProfileEntry._METODO_RILEVAMENTO, profile.getMetodoRilevamento());
        values.put(Profile.ProfileEntry._LUMINOSITA, profile.getLuminosita());
        values.put(Profile.ProfileEntry._VOLUME, profile.getVolume());
        values.put(Profile.ProfileEntry._BLUETOOTH, profile.isBluetooth());
        values.put(Profile.ProfileEntry._APP, profile.getApp());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Profile.ProfileEntry.TABLE_NAME, null, values);

        profile.setId(newRowId);

        return profile;
    }

    public boolean deleteProfile(Profile profile){
        String selection = Profile.ProfileEntry._ID + " == ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { profile.getId()+"" };
        // Issue SQL statement.
        int deletedRows = this.getWritableDatabase().delete(Profile.ProfileEntry.TABLE_NAME, selection, selectionArgs);

        return (deletedRows > 0);

    }

    public boolean updateProfile(Profile profile){

        ContentValues values = new ContentValues();
        values.put(Profile.ProfileEntry._NAME, profile.getName());
        values.put(Profile.ProfileEntry._METODO_RILEVAMENTO, profile.getMetodoRilevamento());
        values.put(Profile.ProfileEntry._LUMINOSITA, profile.getLuminosita());
        values.put(Profile.ProfileEntry._VOLUME, profile.getVolume());
        values.put(Profile.ProfileEntry._BLUETOOTH, profile.isBluetooth());
        values.put(Profile.ProfileEntry._APP, profile.getApp());

// Which row to update, based on the title
        String selection = Profile.ProfileEntry._ID + " == ?";
        String[] selectionArgs = { profile.getId()+"" };

        int count = this.getWritableDatabase().update(
                Profile.ProfileEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return (count >0);
    }

    public List<Profile> getAllProfiles(){

        Cursor cursor = this.getWritableDatabase().query(
                Profile.ProfileEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<Profile> profiles = cursorToProfile(cursor);

        cursor.close();

        return profiles;
    }

    private List<Profile> cursorToProfile(Cursor cursor){
        List<Profile> profiles = new ArrayList<>();

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {

            Profile profile = new Profile(
                    cursor.getLong(cursor.getColumnIndexOrThrow(Profile.ProfileEntry._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Profile.ProfileEntry._NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Profile.ProfileEntry._METODO_RILEVAMENTO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Profile.ProfileEntry._LUMINOSITA)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Profile.ProfileEntry._VOLUME)),
                    (cursor.getInt(cursor.getColumnIndexOrThrow(Profile.ProfileEntry._BLUETOOTH)) == 1)? true : false,
                    cursor.getString(cursor.getColumnIndexOrThrow(Profile.ProfileEntry._APP))
            );

            profiles.add(profile);
        }
        return profiles;
    }


    public Profile getProfileById(Long idProfile){
        String selection = Profile.ProfileEntry._ID + " == ?";
        String[] selectionArgs = {idProfile+"" };

        Cursor cursor = this.getWritableDatabase().query(
                Profile.ProfileEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,           // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        Profile profiles = cursorToProfile(cursor).get(0);

        cursor.close();

        return profiles;
    }


    public int getLastInsertedProfileId(){
        Cursor c = this.getWritableDatabase().rawQuery("SELECT last_insert_rowid()", null);
        c.moveToFirst();
        int id = c.getInt(0);
        return  id;


    }

}

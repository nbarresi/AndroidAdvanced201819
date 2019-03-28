package org.its.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.its.db.entities.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class CoordinatesDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CoordinateManager.db";

    private static final String SQL_CREATE_COORDINATES =
            "CREATE TABLE " + Coordinates.CoordinatesEntry.TABLE_NAME + " (" +
                    Coordinates.CoordinatesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Coordinates.CoordinatesEntry._LATITUDE + " TEXT," +
                    Coordinates.CoordinatesEntry._LONGITUDE + " TEXT," +
                    Coordinates.CoordinatesEntry._RADIUS + " INTEGER," +
                    Coordinates.CoordinatesEntry._IDPROFILE + " INTEGER)";

    private static final String SQL_DELETE_COORDINATES = "DROP TABLE IF EXISTS " + Coordinates.CoordinatesEntry.TABLE_NAME;

    public CoordinatesDBHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_COORDINATES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public SQLiteDatabase getWritableDatabase(){
        return super.getWritableDatabase();
    }

    public Coordinates insertCoordinates(Coordinates coordinates){
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Coordinates.CoordinatesEntry._LATITUDE, coordinates.getLatitude());
        values.put(Coordinates.CoordinatesEntry._LONGITUDE, coordinates.getLongitude());
        values.put(Coordinates.CoordinatesEntry._RADIUS, coordinates.getRadius());
        values.put(Coordinates.CoordinatesEntry._IDPROFILE, coordinates.getIdProfile());
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Coordinates.CoordinatesEntry.TABLE_NAME, null, values);
        coordinates.setId(newRowId);
        return coordinates;
    }

    public boolean deleteCoordinates(Coordinates coordinates){
        String selection = Coordinates.CoordinatesEntry._ID + " == ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { coordinates.getId()+"" };
        // Issue SQL statement.
        int deletedRows = this.getWritableDatabase().delete(Coordinates.CoordinatesEntry.TABLE_NAME, selection, selectionArgs);
        return (deletedRows > 0);
    }

    public boolean updateCoordinates(Coordinates coordinates){
        ContentValues values = new ContentValues();
        values.put(Coordinates.CoordinatesEntry._LATITUDE, coordinates.getLatitude());
        values.put(Coordinates.CoordinatesEntry._LONGITUDE, coordinates.getLongitude());
        values.put(Coordinates.CoordinatesEntry._RADIUS, coordinates.getRadius());
        values.put(Coordinates.CoordinatesEntry._IDPROFILE, coordinates.getIdProfile());
// Which row to update, based on the title
        String selection = Coordinates.CoordinatesEntry._ID + " == ?";
        String[] selectionArgs = { coordinates.getId()+"" };

        int count = this.getWritableDatabase().update(
                Coordinates.CoordinatesEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return (count >0);
    }


    public List<Coordinates> getAllCoordinates(){
        Cursor cursor = this.getWritableDatabase().query(
                Coordinates.CoordinatesEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<Coordinates> coordinates = cursorToCoordinates(cursor);
        cursor.close();
        return coordinates;
    }

    private List<Coordinates> cursorToCoordinates(Cursor cursor){
        List<Coordinates> coordinatesList = new ArrayList<>();
        while(cursor.moveToNext()) {
                Coordinates coordinates = new Coordinates(
                    cursor.getLong(cursor.getColumnIndexOrThrow(Coordinates.CoordinatesEntry._ID)),
                    cursor.getDouble((cursor.getColumnIndexOrThrow(Coordinates.CoordinatesEntry._LATITUDE))),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(Coordinates.CoordinatesEntry._LONGITUDE)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(Coordinates.CoordinatesEntry._RADIUS)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(Coordinates.CoordinatesEntry._IDPROFILE))
            );

            coordinatesList.add(coordinates);
        }
        return coordinatesList;
    }

    public Coordinates getCoordinatesByIdProfile(long idProfile) {
        String selection = Coordinates.CoordinatesEntry._IDPROFILE+" LIKE ?";
        String[] seletion_args={Long.toString(idProfile)};
        Cursor cursorSingleCoordinate = this.getWritableDatabase().query(Coordinates.CoordinatesEntry.TABLE_NAME,null, selection,seletion_args,null,null,null);

        Coordinates coordinates = new Coordinates();
        while(cursorSingleCoordinate.moveToNext()) {
            Coordinates tc = new Coordinates(
                    cursorSingleCoordinate.getLong(cursorSingleCoordinate.getColumnIndexOrThrow(Coordinates.CoordinatesEntry._ID)),
                    cursorSingleCoordinate.getDouble((cursorSingleCoordinate.getColumnIndexOrThrow(Coordinates.CoordinatesEntry._LATITUDE))),
                    cursorSingleCoordinate.getDouble(cursorSingleCoordinate.getColumnIndexOrThrow(Coordinates.CoordinatesEntry._LONGITUDE)),
                    cursorSingleCoordinate.getLong(cursorSingleCoordinate.getColumnIndexOrThrow(Coordinates.CoordinatesEntry._RADIUS)),
                    cursorSingleCoordinate.getLong(cursorSingleCoordinate.getColumnIndexOrThrow(Coordinates.CoordinatesEntry._IDPROFILE))
            );
            coordinates = tc;
        }
        return  coordinates;
    }
}

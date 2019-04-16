package org.its.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.its.db.entities.NfcPoint;
import org.its.db.entities.NfcPoint;

import java.util.ArrayList;
import java.util.List;

public class NfcPointDBHelper extends GenericDBHelper{



    public NfcPointDBHelper(Context context) {
        super(context);
    }

    public NfcPoint insertNfcPoint(NfcPoint nfcPoint){
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(NfcPoint.NfcPointEntry._NFCID, nfcPoint.getNfcId());
        // Insert the new row, returning the primary key value of the new row
        db.insert(NfcPoint.NfcPointEntry.TABLE_NAME, null, values);
        return nfcPoint;
    }

    public boolean deleteNfcPoint(NfcPoint nfcPoint){
        String selection = NfcPoint.NfcPointEntry._NFCID + " == ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { nfcPoint.getNfcId()+"" };
        // Issue SQL statement.
        int deletedRows = this.getWritableDatabase().delete(NfcPoint.NfcPointEntry.TABLE_NAME, selection, selectionArgs);
        return (deletedRows > 0);
    }

    public boolean updateNfcPoint(NfcPoint nfcPoint){
        ContentValues values = new ContentValues();
        values.put(NfcPoint.NfcPointEntry._NFCID, nfcPoint.getNfcId());
// Which row to update, based on the title
        String selection = NfcPoint.NfcPointEntry._NFCID+ " == ?";
        String[] selectionArgs = { nfcPoint.getNfcId()+"" };

        int count = this.getWritableDatabase().update(
                NfcPoint.NfcPointEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return (count >0);
    }

    public List<NfcPoint> getAllPoints(){

        Cursor cursor = this.getWritableDatabase().query(
                NfcPoint.NfcPointEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        return cursorToNfcPoint(cursor);

    }

    public NfcPoint getByNFCId(String NFCId){
        String selection = NfcPoint.NfcPointEntry._NFCID+ " == ?";
        String[] selectionArgs = { NFCId};
        Cursor cursor = this.getWritableDatabase().query(
                NfcPoint.NfcPointEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        cursor.moveToFirst();
        NfcPoint nfcPoint = null;
        if(cursor.getCount() >0) {
            nfcPoint = new NfcPoint(
                    cursor.getString(cursor.getColumnIndexOrThrow(NfcPoint.NfcPointEntry._NFCID))
            );
        }
        return nfcPoint;
    }

    private List<NfcPoint> cursorToNfcPoint(Cursor cursor){
        List<NfcPoint> nfcPoints = new ArrayList<>();

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {

            NfcPoint nfcPoint = new NfcPoint(
                    cursor.getString(cursor.getColumnIndexOrThrow(NfcPoint.NfcPointEntry._NFCID))
            );

            nfcPoints.add(nfcPoint);
        }
        return nfcPoints;
    }


}

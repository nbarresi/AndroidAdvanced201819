package org.its.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.its.db.entities.ProfileNfcPoints;
import org.its.db.entities.NfcPoint;

import java.util.ArrayList;
import java.util.List;

public class ProfileNfcPointsDBHelper extends GenericDBHelper {

    NfcPointDBHelper nfcHelper;

    public ProfileNfcPointsDBHelper(Context context) {
        super(context);
        nfcHelper = new NfcPointDBHelper(context);
    }

    public ProfileNfcPoints insertProfileNfcPoint(ProfileNfcPoints profileNfcPoints) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProfileNfcPoints.ProfileNfcPointsEntry._ID, profileNfcPoints.getIdProfile());
        values.put(ProfileNfcPoints.ProfileNfcPointsEntry._NFCID, profileNfcPoints.getIdNfc());

        // Insert the new row, returning the primary key value of the new row
        db.insert(ProfileNfcPoints.ProfileNfcPointsEntry.TABLE_NAME, null, values);

        NfcPoint nfcPoint = new NfcPoint(profileNfcPoints.getIdNfc());

        if (nfcHelper.getByNFCId(profileNfcPoints.getIdNfc()) == null)
            nfcHelper.insertNfcPoint(nfcPoint);
        else
            nfcHelper.updateNfcPoint(nfcPoint);

        return profileNfcPoints;
    }

    public boolean deleteProfileNfcPoints(ProfileNfcPoints nfcPoints) {
        String selection = ProfileNfcPoints.ProfileNfcPointsEntry._ID + " == ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {nfcPoints.getIdProfile() + ""};
        // Issue SQL statement.
        int deletedRows = this.getWritableDatabase().delete(NfcPoint.NfcPointEntry.TABLE_NAME, selection, selectionArgs);

        return (deletedRows > 0);
    }

    public boolean updateProfileNfcPoints(ProfileNfcPoints nfcPoints) {
        deleteProfileNfcPoints(nfcPoints);
        insertProfileNfcPoint(nfcPoints);
        return true;
    }

    public ProfileNfcPoints getProfileNfcPoints(Long idProfile) {
        String selection = ProfileNfcPoints.ProfileNfcPointsEntry._ID + " == ?";
        String[] selectionArgs = {idProfile + ""};
        Cursor cursor = this.getWritableDatabase().query(
                ProfileNfcPoints.ProfileNfcPointsEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        ProfileNfcPoints profileNfcPoints = new ProfileNfcPoints();
        profileNfcPoints.setIdProfile(idProfile);

        List<NfcPoint> nfcPoints = new ArrayList<>();
        NfcPoint nfcPoint = nfcHelper.getByNFCId(cursor.getString(cursor.getColumnIndexOrThrow(ProfileNfcPoints.ProfileNfcPointsEntry._NFCID)));
        profileNfcPoints.setIdNfc(nfcPoint.getNfcId());

        return profileNfcPoints;

    }

}

package org.its.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.its.db.DbHelper;
import org.its.db.entities.Profilo;
import org.its.utilities.Converters;
import org.its.utilities.StringCollection;

import java.util.ArrayList;
import java.util.List;

public class ProfiloDao extends GenericDao {

    protected ProfiloDao(Context context) {
        super(context);
    }

    public Profilo insertProfile(Profilo profile) throws Exception {
        ContentValues contentValues = new ContentValues();

        contentValues.put(StringCollection.columnLuminosita, profile.getLuminosita());
        contentValues.put(StringCollection.columnAutoLuminosita, Converters.fromBooleanToInt(profile.isAutoLuminosita()));
        contentValues.put(StringCollection.columnWIFI, Converters.fromBooleanToInt(profile.isWifi()));
        contentValues.put(StringCollection.columnBluetooth, Converters.fromBooleanToInt(profile.isBluetooth()));
        contentValues.put(StringCollection.columnVolume, profile.getVolume());
        contentValues.put(StringCollection.columnMetodo, profile.getMetodo().getValue());
        contentValues.put(StringCollection.columnRilevazione, profile.getRilevazione());
        contentValues.put(StringCollection.columnNome, profile.getNome());

        long profileId = database.insert(TABLE_NAME,
                null,
                contentValues);

        profile.setId((int) profileId);
        return profile;
    }

    public List<Profilo> getAllProfiles() {
        ArrayList<Profilo> profili= new ArrayList<>();
        Cursor result = database.query(TABLE_NAME, new String[]{StringCollection.columnID, StringCollection.columnLuminosita,
                StringCollection.columnNome, StringCollection.columnVolume, StringCollection.columnBluetooth, StringCollection.columnAutoLuminosita,
                StringCollection.columnRilevazione, StringCollection.columnMetodo, StringCollection.columnWIFI},
                null, null, null, null, null);

        try {
            while (result.moveToNext()) {
                //da finire
            }
        } finally {
            result.close();
        }
        return profili;
    }

    @Override
    public void openConn() {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void closeConn() {
        dbHelper.close();
    }
}

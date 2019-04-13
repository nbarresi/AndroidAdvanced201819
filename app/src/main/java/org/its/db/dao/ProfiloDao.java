package org.its.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import org.its.db.entities.Profilo;
import org.its.utilities.Converters;
import org.its.utilities.ProfileTypeEnum;
import org.its.utilities.StringCollection;

import java.util.ArrayList;
import java.util.List;

public class ProfiloDao extends GenericDao {

    private static final String TABLE_NAME = "profilo";

    public ProfiloDao() {
        super();
    }

    public Profilo insertProfile(Profilo profile) throws Exception {
        ContentValues contentValues = fromProfileToContentValues(profile);

        long profileId = database.insert(TABLE_NAME,
                null,
                contentValues);

        profile.setId((int) profileId);
        return profile;
    }

    public List<Profilo> getAllProfiles() {
        ArrayList<Profilo> profili = new ArrayList<>();
        Cursor result = database.query(TABLE_NAME, new String[]{StringCollection.columnID, StringCollection.columnLuminosita,
                        StringCollection.columnNome, StringCollection.columnVolume, StringCollection.columnBluetooth, StringCollection.columnAutoLuminosita,
                        StringCollection.columnRilevazione, StringCollection.columnMetodo, StringCollection.columnWIFI, StringCollection.columnApp, StringCollection.columnIsActive},
                null, null, null, null, null);

        try {
            if (result.getCount() > 0) {
                result.moveToFirst();
                do {
                    profili.add(fromCursorToProfile(result));
                } while (result.moveToNext());

            }
        } catch (Exception e) {
            Log.d("getAllProfilesError", e.getMessage());
        } finally {
            result.close();
        }
        return profili;
    }

    public Profilo getById(int id) {
        Profilo profilo = null;
        Cursor result = database.query(TABLE_NAME, new String[]{StringCollection.columnID, StringCollection.columnLuminosita,
                        StringCollection.columnNome, StringCollection.columnVolume, StringCollection.columnBluetooth, StringCollection.columnAutoLuminosita,
                        StringCollection.columnRilevazione, StringCollection.columnMetodo, StringCollection.columnApp,StringCollection.columnWIFI, StringCollection.columnIsActive},
                StringCollection.columnID + "=?", new String[]{"" + id}, null, null, null);

        try {
            if (result.getCount() > 0) {
                result.moveToFirst();
                do {
                    profilo = fromCursorToProfile(result);
                } while (result.moveToNext());

            }
        } catch (Exception e) {
            Log.d("getByIdError", e.getMessage());
        } finally {
            result.close();
        }
        return profilo;
    }

    public List<Profilo> getByMetodo(ProfileTypeEnum metodo) {
        List<Profilo> profili = new ArrayList<>();
        Cursor result = database.query(TABLE_NAME, new String[]{StringCollection.columnID, StringCollection.columnLuminosita,
                        StringCollection.columnNome, StringCollection.columnVolume, StringCollection.columnBluetooth, StringCollection.columnAutoLuminosita,
                        StringCollection.columnRilevazione, StringCollection.columnMetodo, StringCollection.columnWIFI, StringCollection.columnApp,StringCollection.columnIsActive},
                StringCollection.columnMetodo + "=?", new String[]{"" + metodo.getValue()}, null, null, null);

        try {
            if (result.getCount() > 0) {
                result.moveToFirst();
                do {
                    profili.add(fromCursorToProfile(result));
                    Log.d("nel ciclo",""+profili.size());
                } while (result.moveToNext());

            }
        } catch (Exception e) {
            Log.d("getByMetodoError", e.getMessage());
        } finally {
            result.close();
        }
        return profili;
    }

    public void updateProfilo(Profilo profilo) {
        ContentValues contentValues = fromProfileToContentValues(profilo);
        contentValues.put(StringCollection.columnIsActive, Converters.fromBooleanToInt(profilo.isActive()));
        database.update(
                TABLE_NAME,
                contentValues,
                StringCollection.columnID + "=?",
                new String[]{"" + profilo.getId()});

    }

    public boolean deleteProfile(int id) {
        return database.delete(TABLE_NAME, StringCollection.columnID + "=?", new String[]{"" + id}) > 0;
    }

    private ContentValues fromProfileToContentValues(Profilo profilo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StringCollection.columnLuminosita, profilo.getLuminosita());
        contentValues.put(StringCollection.columnAutoLuminosita, Converters.fromBooleanToInt(profilo.isAutoLuminosita()));
        contentValues.put(StringCollection.columnWIFI, Converters.fromBooleanToInt(profilo.isWifi()));
        contentValues.put(StringCollection.columnBluetooth, Converters.fromBooleanToInt(profilo.isBluetooth()));
        contentValues.put(StringCollection.columnVolume, profilo.getVolume());
        contentValues.put(StringCollection.columnMetodo, profilo.getMetodo().getValue());
        contentValues.put(StringCollection.columnRilevazione, profilo.getRilevazione());
        contentValues.put(StringCollection.columnNome, profilo.getNome());
        contentValues.put(StringCollection.columnApp, profilo.getApp());
        return contentValues;
    }

    private Profilo fromCursorToProfile(Cursor result) {
        return new Profilo(result.getInt(result.getColumnIndexOrThrow(StringCollection.columnID)),
                result.getString(result.getColumnIndexOrThrow(StringCollection.columnNome)),
                result.getInt(result.getColumnIndexOrThrow(StringCollection.columnVolume)),
                result.getInt(result.getColumnIndexOrThrow(StringCollection.columnLuminosita)),
                Converters.fromIntToBoolean(result.getInt(result.getColumnIndexOrThrow(StringCollection.columnAutoLuminosita))),
                Converters.fromIntToBoolean(result.getInt(result.getColumnIndexOrThrow(StringCollection.columnWIFI))),
                Converters.fromIntToBoolean(result.getInt(result.getColumnIndexOrThrow(StringCollection.columnBluetooth))),
                ProfileTypeEnum.getEnumFromInt(result.getInt(result.getColumnIndexOrThrow(StringCollection.columnMetodo))),
                result.getString(result.getColumnIndexOrThrow(StringCollection.columnRilevazione)),
                result.getString(result.getColumnIndexOrThrow(StringCollection.columnApp)),
                Converters.fromIntToBoolean(result.getInt(result.getColumnIndexOrThrow((StringCollection.columnIsActive)))));
    }
}

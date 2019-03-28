package org.its.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import org.its.db.entities.WifiConnection;
import org.its.utilities.StringCollection;

import java.util.ArrayList;
import java.util.List;

public class WifiDao extends GenericDao {
    private static final String TABLE_NAME = "wifi";
    private static final String RELATION_TABLE_NAME = "profilo_wifi";

    public WifiDao() {
        super();
    }

    public WifiConnection insertWifi(WifiConnection wifiConnection, int idProfilo) throws Exception {
        ContentValues contentValues = new ContentValues();

        contentValues.put(StringCollection.columnBssid, wifiConnection.getBSSID());
        contentValues.put(StringCollection.columnSsid, wifiConnection.getName());
        contentValues.put(StringCollection.columnPotenza, wifiConnection.getPower());
        if (getWifiByBssid(wifiConnection.getBSSID()) == null) {
            database.insert(TABLE_NAME,
                    null,
                    contentValues);

        }
        contentValues.clear();
        contentValues.put(StringCollection.columnBssid, wifiConnection.getBSSID());
        contentValues.put(StringCollection.columnIdProfilo, idProfilo);
        database.insert(RELATION_TABLE_NAME, null, contentValues);

        return wifiConnection;
    }

    public List<WifiConnection> getAllWiFiConnectionForAProfile(int idProfilo) {
        ArrayList<WifiConnection> wifiList = new ArrayList<>();
        Cursor result = database.rawQuery("SELECT wifi.bssid,ssid,potenza from wifi join profilo_wifi on profilo_wifi.bssid = wifi.bssid where" +
                " profilo_wifi.id_profilo=? ", new String[]{"" + idProfilo});

        try {
            if (result.getCount() > 0) {
                result.moveToFirst();
                do {
                    wifiList.add(new WifiConnection(result.getInt(result.getColumnIndexOrThrow(StringCollection.columnPotenza)),
                            result.getString(result.getColumnIndexOrThrow(StringCollection.columnSsid)),
                            result.getString(result.getColumnIndexOrThrow(StringCollection.columnBssid))));
                } while (result.moveToNext());

            }
        } catch (Exception e) {
            Log.d("getAllProfilesError", e.getMessage());
        } finally {
            result.close();
        }
        return wifiList;
    }

    public WifiConnection getWifiByBssid(String bssid) {
        WifiConnection wifi = null;
        Cursor result = database.query(TABLE_NAME, new String[]{StringCollection.columnBssid, StringCollection.columnSsid,
                        StringCollection.columnPotenza},
                StringCollection.columnBssid + "=?", new String[]{bssid}, null, null, null);

        try {
            if (result.getCount() > 0) {
                result.moveToFirst();
                do {
                    wifi = new WifiConnection(result.getInt(result.getColumnIndexOrThrow(StringCollection.columnPotenza)),
                            result.getString(result.getColumnIndexOrThrow(StringCollection.columnSsid)),
                            result.getString(result.getColumnIndexOrThrow(StringCollection.columnBssid)));
                } while (result.moveToNext());

            }
        } catch (Exception e) {
            Log.d("getAllProfilesError", e.getMessage());
        } finally {
            result.close();
        }
        return wifi;
    }

   /* public void updateProfilo(WifiConnection profilo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StringCollection.columnLuminosita, profilo.getLuminosita());
        contentValues.put(StringCollection.columnAutoLuminosita, Converters.fromBooleanToInt(profilo.isAutoLuminosita()));
        contentValues.put(StringCollection.columnWIFI, Converters.fromBooleanToInt(profilo.isWifi()));
        contentValues.put(StringCollection.columnBluetooth, Converters.fromBooleanToInt(profilo.isBluetooth()));
        contentValues.put(StringCollection.columnVolume, profilo.getVolume());
        contentValues.put(StringCollection.columnMetodo, profilo.getMetodo().getValue());
        contentValues.put(StringCollection.columnRilevazione, profilo.getRilevazione());
        contentValues.put(StringCollection.columnNome, profilo.getNome());
        database.update(
                TABLE_NAME,
                contentValues,
                StringCollection.columnID + "=?",
                new String[]{"" + profilo.getId()});

    }*/

    public boolean deleteListForAProfile(int idProfilo) {
        return database.delete(RELATION_TABLE_NAME, StringCollection.columnIdProfilo + "=?", new String[]{"" + idProfilo}) > 0;
    }


}

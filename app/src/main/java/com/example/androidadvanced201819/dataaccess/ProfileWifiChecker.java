package com.example.androidadvanced201819.dataaccess;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.example.androidadvanced201819.database.ProfileDatabaseManager;
import com.example.androidadvanced201819.database.ProfileWifiDatabaseManager;
import com.example.androidadvanced201819.model.Profile;
import com.example.androidadvanced201819.model.ProfileWiFi;

import java.util.ArrayList;
import java.util.List;

public class ProfileWifiChecker {

    private ProfileDatabaseManager profileDatabaseManager;
    private ProfileWifiDatabaseManager profileWifiDatabaseManager;
    private int old_id = 0;
    private WifiManager wifiManager;
    private Context mContext;
    AudioManager audioManager;

    public ProfileWifiChecker(Context context, AudioManager audioManager) {
        profileDatabaseManager = new ProfileDatabaseManager(context);
        profileWifiDatabaseManager = new ProfileWifiDatabaseManager(context);
        mContext = context;
        this.audioManager = audioManager;
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public void checkProfileWifi(List<ScanResult> wifiList) {
        profileWifiDatabaseManager.open();
        profileDatabaseManager.open();
        Cursor profileCursor = profileDatabaseManager.fetchAllWifiProfile();

        List<Profile> profiles = getProfileFromCursor(profileCursor);

        for (Profile profile : profiles) {
            Cursor profileWifiCursor = profileWifiDatabaseManager.fetchAllProfileWifiByIdProfile(profile.getId());
            List<ProfileWiFi> profilesWifi = getProfileWiFiFromCursor(profileWifiCursor);
            for (ProfileWiFi profileWiFi : profilesWifi) {
                for (ScanResult wifi : wifiList) {
                    if (profileWiFi.getBSSID().equals(wifi.BSSID)) {
                        activeProfile(profile);
                    }
                }
            }
        }
        profileDatabaseManager.close();
        profileWifiDatabaseManager.close();
    }

    private List<ProfileWiFi> getProfileWiFiFromCursor(Cursor profileWifiCursor) {
        List<ProfileWiFi> profilesWifi = new ArrayList<>();
        profileWifiCursor.moveToFirst();
        int index = profileWifiCursor.getCount();

        if (index > 0) {
            int i = 0;
            do {
                ProfileWiFi profileWiFi = new ProfileWiFi(
                        profileWifiCursor.getInt(profileWifiCursor.getColumnIndex(ProfileWifiDatabaseManager.KEY_IDPROFILE)),
                        profileWifiCursor.getString(profileWifiCursor.getColumnIndex(ProfileWifiDatabaseManager.KEY_BSSID))
                );
                i++;
                profilesWifi.add(profileWiFi);
                profileWifiCursor.moveToNext();
            } while (i < index);
        }
        return profilesWifi;
    }

    private List<Profile> getProfileFromCursor(Cursor profileCursor) {
        List<Profile> profiles = new ArrayList<>();
        profileCursor.moveToFirst();
        int index = profileCursor.getCount();

        if (index > 0) {
            int i = 0;
            do {
                Profile profile = new Profile(
                        profileCursor.getInt(profileCursor.getColumnIndex(ProfileDatabaseManager.KEY_ID)),
                        profileCursor.getString(profileCursor.getColumnIndex(ProfileDatabaseManager.KEY_PROFILE_NAME)),
                        profileCursor.getInt(profileCursor.getColumnIndex(ProfileDatabaseManager.KEY_OPTION_SELECTED)),
                        profileCursor.getInt(profileCursor.getColumnIndex(ProfileDatabaseManager.KEY_BRIGHTNESS)),
                        profileCursor.getInt(profileCursor.getColumnIndex(ProfileDatabaseManager.KEY_VOLUME)),
                        profileCursor.getInt(profileCursor.getColumnIndex(ProfileDatabaseManager.KEY_BLUETHOOTH)),
                        profileCursor.getInt(profileCursor.getColumnIndex(ProfileDatabaseManager.KEY_WIFI)),
                        profileCursor.getString(profileCursor.getColumnIndex(ProfileDatabaseManager.KEY_APPLICATION)),
                        profileCursor.getString(profileCursor.getColumnIndex(ProfileDatabaseManager.KEY_APPLICATION_NAME)),
                        profileCursor.getInt(profileCursor.getColumnIndex(ProfileDatabaseManager.KEY_AUTOBRIGHTNESS)),
                        profileCursor.getString(profileCursor.getColumnIndex(ProfileDatabaseManager.KEY_COORDINATES)),
                        profileCursor.getString(profileCursor.getColumnIndex(ProfileDatabaseManager.KEY_NFC))
                );
                i++;
                profiles.add(profile);
                profileCursor.moveToNext();
            } while (i < index);
        }
        return profiles;
    }

    private void activeProfile(Profile profile) {
        //constrain the value of brightness
        if (profile.getId() != old_id) {
            Log.d("active profile", String.valueOf(profile.getId()));
            Log.d("brightness", String.valueOf(profile.getBrightness()));
            Log.d("volume", String.valueOf(profile.getVolume()));
            old_id = profile.getId();
            int brightness = profile.getBrightness();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(mContext)) {
                    if (brightness < 0)
                        brightness = 0;
                    else if (brightness > 255)
                        brightness = 255;

                    ContentResolver cResolver = mContext.getContentResolver();
                    Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, profile.getVolume(), 1);
                    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (bluetoothAdapter != null) {
                        if (profile.getBluethoot() == 1) {
                            bluetoothAdapter.enable();
                        } else {
                            bluetoothAdapter.disable();
                        }
                    }
                    if (profile.getWifi() == 1) {
                        wifiManager.setWifiEnabled(true);
                    } else {
                        wifiManager.setWifiEnabled(false);
                    }
                }
            }
        }
    }

}

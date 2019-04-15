package com.example.androidadvanced201819.service;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.androidadvanced201819.dataaccess.ProfileWifiChecker;
import com.example.androidadvanced201819.database.ProfileDatabaseManager;
import com.example.androidadvanced201819.database.ProfileWifiDatabaseManager;
import com.example.androidadvanced201819.database.WiFiDatabaseManager;
import com.example.androidadvanced201819.model.Profile;
import com.example.androidadvanced201819.model.ProfileWiFi;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WiFiService extends IntentService {

    WifiManager wifiManager;
    List<ScanResult> wifiList;
    AudioManager audioManager;
    ProfileWifiChecker profileWifiChecker;

    @Override
    public void onCreate() {
        super.onCreate();
        final IntentFilter it = new IntentFilter();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        profileWifiChecker = new ProfileWifiChecker(getApplicationContext(), audioManager);
        it.addAction("android.intent.action.BOOT_COMPLETED");
    }

    public WiFiService() {
        super("WiFiService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("12", "service started");
        Timer t = new Timer();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                registerReceiver(new BroadcastReceiver() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onReceive(Context c, Intent intent) {
                        wifiList = wifiManager.getScanResults();
                        profileWifiChecker.checkProfileWifi(wifiList);
                        unregisterReceiver(this);
                    }
                }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                wifiManager.startScan();
            }
        }, 0, 10000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
    }


}



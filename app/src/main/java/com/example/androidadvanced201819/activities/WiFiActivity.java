package com.example.androidadvanced201819.activities;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.activities.adapter.WiFiAdapterActivity;
import com.example.androidadvanced201819.activities.profile.ProfileManagement;
import com.example.androidadvanced201819.database.ProfileWifiDatabaseManager;
import com.example.androidadvanced201819.database.WiFiDatabaseManager;
import com.example.androidadvanced201819.model.WiFi;
import com.example.androidadvanced201819.model.WiFiList;

import java.util.ArrayList;
import java.util.List;

public class WiFiActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    private List<ScanResult> wifiList;
    WiFiAdapterActivity wiFiAdapterActivity;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wiFiAdapterActivity = new WiFiAdapterActivity(getApplicationContext());
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(wiFiAdapterActivity);
        Button saveWifi = findViewById(R.id.setWifi);
        Button saveProfile = findViewById(R.id.accessButton);
        title = findViewById(R.id.main_title);
        title.setText("Lista WiFi");

        saveProfile.setVisibility(View.GONE);
        saveWifi.setVisibility(View.VISIBLE);

        this.scanWiFi();
    }

    private void scanWiFi() {
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        registerReceiver(new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceive(Context c, Intent intent) {
                wifiList = wifiManager.getScanResults();
                wiFiAdapterActivity.updateList(wifiList);
                unregisterReceiver(this);
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }

    public void saveWifi(View view) {
        List<WiFi> wiFis = new ArrayList<>();
        for (ScanResult wifi : wifiList) {
            WiFi newWifi = new WiFi(wifi.BSSID, wifi.SSID, wifi.level);
            wiFis.add(newWifi);
            WiFiDatabaseManager wiFiDatabaseManager = new WiFiDatabaseManager(getApplicationContext());
            wiFiDatabaseManager.open();
            wiFiDatabaseManager.createWifi(newWifi);
            wiFiDatabaseManager.close();
        }

        Intent goToProfile = new Intent(this, ProfileManagement.class);
        goToProfile.putExtra("wifis", new WiFiList(wiFis));
        setResult(3, goToProfile);
        finish();
    }
}

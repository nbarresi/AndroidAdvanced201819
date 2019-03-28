package com.example.androidadvanced201819.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.activities.adapter.WiFiAdapterActivity;

import java.util.List;

public class WiFiActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    private List<ScanResult> wifiList;
    WiFiAdapterActivity wiFiAdapterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wiFiAdapterActivity = new WiFiAdapterActivity(getApplicationContext());
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(wiFiAdapterActivity);
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
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }
}

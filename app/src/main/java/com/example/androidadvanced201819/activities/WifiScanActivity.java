package com.example.androidadvanced201819.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidadvanced201819.DB.DbHelper;
import com.example.androidadvanced201819.DB.Entities.UserProfile;
import com.example.androidadvanced201819.DB.Entities.Wifi;
import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.adapter.ProfileAdapter;
import com.example.androidadvanced201819.adapter.ScanAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WifiScanActivity extends AppCompatActivity {

    private ScanAdapter scanAdapter;
    private ListView listView;
    public static String EXTRA_WIFI_LIST = "wifi_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_scan);

        listView = (ListView) findViewById(R.id.listView);
        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        listView = (ListView) findViewById(R.id.listView);
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0x12345);
        } else {
            if(!wifiManager.isWifiEnabled()){
                Toast.makeText(WifiScanActivity.this, "Attivare il WIFI", Toast.LENGTH_LONG).show();

            }

            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    boolean success = intent.getBooleanExtra(
                            WifiManager.EXTRA_RESULTS_UPDATED, false);
                    List<ScanResult> results = wifiManager.getScanResults();
                    if (success) {
                        scanAdapter = new ScanAdapter(WifiScanActivity.this, wifiManager.getScanResults());
                        listView.setAdapter(scanAdapter);
                    } else {

                    }
                }
            }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        }

        wifiManager.startScan();
        Button createProfile = findViewById(R.id.confirm);

        createProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Wifi> wifiResult = convertScanResult(wifiManager.getScanResults());

                if (!wifiResult.isEmpty()) {
                    Intent toCreate = new Intent();
                    toCreate.putExtra(EXTRA_WIFI_LIST, (Serializable) wifiResult);
                    setResult(CreateProfileActivity.REQUEST_WIFI, toCreate);
                    finish();
                } else {
                    Toast.makeText(WifiScanActivity.this, "Non sono disponibili reti da salvare", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    private List<Wifi> convertScanResult(List<ScanResult> wifiResults) {
        List<Wifi> wifis = new ArrayList<Wifi>();

        for (ScanResult result : wifiResults) {
            wifis.add(new Wifi(result.SSID, result.BSSID, WifiManager.calculateSignalLevel(result.level, 5)));
        }

        return wifis;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0x12345) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    //boolean success = intent.getBooleanExtra(
                    //WifiManager.EXTRA_RESULTS_UPDATED, false);
                    List<ScanResult> results = wifiManager.getScanResults();
                    if (true) {
                        scanAdapter = new ScanAdapter(WifiScanActivity.this, wifiManager.getScanResults());
                        listView.setAdapter(scanAdapter);
                    } else {

                    }
                }
            }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wifiManager.startScan();
        }
    }
}

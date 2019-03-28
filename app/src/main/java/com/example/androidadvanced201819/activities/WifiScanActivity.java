package com.example.androidadvanced201819.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.example.androidadvanced201819.DB.Entities.UserProfile;
import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.adapter.ProfileAdapter;
import com.example.androidadvanced201819.adapter.ScanAdapter;

import java.util.List;

public class WifiScanActivity extends AppCompatActivity {

    ScanAdapter scanAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_scan);

        final ListView listView = (ListView) findViewById(R.id.listView);
        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);


        registerReceiver(new BroadcastReceiver() {
                             @Override
                             public void onReceive(Context context, Intent intent) {
                                 boolean success = intent.getBooleanExtra(
                                         WifiManager.EXTRA_RESULTS_UPDATED, false);
                                 if (success) {
                                     scanAdapter = new ScanAdapter(WifiScanActivity.this,wifiManager.getScanResults());
                                     listView.setAdapter(scanAdapter);
                                 } else {

                                 }
                             }
                         },new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));


                Button createProfile = findViewById(R.id.confirm);

        createProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}

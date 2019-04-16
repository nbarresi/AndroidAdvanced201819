package com.example.androidadvanced201819;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;


public class ListaWifi extends AppCompatActivity {
    Context context;
    WifiManager wifiManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_wifi);

         context=getApplicationContext();
         wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        ListView listaWIFI=findViewById(R.id.Lista_wifi);

        registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context c, Intent intent) {
                boolean success = intent.getBooleanExtra(
                        WifiManager.EXTRA_RESULTS_UPDATED, false);

                List<ScanResult> results = wifiManager.getScanResults();
            }

        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                List lista = new ArrayList();
        CustomAdapterWifi adapterWifi=new CustomAdapterWifi(this, R.layout.lista_wifi,wifiManager.getScanResults());
        listaWIFI.setAdapter(adapterWifi);
        wifiManager.startScan();



    }
}

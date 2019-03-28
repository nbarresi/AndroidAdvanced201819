package org.its.login.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.androidadvanced201819.R;
import com.google.gson.Gson;

import org.its.db.entities.WiFiPoint;
import org.its.login.adapters.WifiListRecyclerAdapter;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class WifiActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private WifiManager wifiManager;
    private WifiListRecyclerAdapter adapter;
    private List<ScanResult> wifiList = new ArrayList<>();
    private Button btnDisplay;
    public static int FILTER_WIFI_LEVEL = 70;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        recyclerView = (RecyclerView) findViewById(R.id.rv_wifi);
        recyclerView.setHasFixedSize(true);
        setImpostaButtonListener();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        registerReceiver(new BroadcastReceiver() {
                             @Override
                             public void onReceive(Context context, Intent intent) {
                                 wifiList = wifiManager.getScanResults();
                                 //TEMPO TOREMOVE MOCK
                                 mockScanResultList();
                                 setRecyclerViewLayoutManager();
                                 setRecyclerAdapter();
                             }
                         },
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        //TEMP TOREMOVE MOCK
        mockScanResultList();
        setRecyclerViewLayoutManager();
        setRecyclerAdapter();
    }

    private void mockScanResultList() {
        Constructor<ScanResult> ctor = null;
        try {
            ctor = ScanResult.class.getDeclaredConstructor(null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        ctor.setAccessible(true);

        ScanResult sr = null;
        try {
            sr = ctor.newInstance(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        sr.BSSID = "foo";
        sr.SSID = "bar";
        sr.level = 90;

        ScanResult ar = null;
        try {
            ar = ctor.newInstance(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        ar.BSSID = "ANTANI";
        ar.SSID = "LOFRANO";
        ar.level = 20;

        wifiList.add(sr);wifiList.add(ar);wifiList.add(sr);wifiList.add(ar);wifiList.add(sr);wifiList.add(sr);wifiList.add(ar);
    }


    public void setRecyclerViewLayoutManager() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setRecyclerAdapter() {
        adapter = new WifiListRecyclerAdapter(wifiList);
        recyclerView.setAdapter(adapter);
    }

    private void setImpostaButtonListener(){
        btnDisplay = (Button) findViewById(R.id.imposta_wifi);
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<WiFiPoint> wiFiPointList = convertToWifiPointListAndFilterForLevel();
                Intent returnWifiIntent = new Intent();
                returnWifiIntent.putExtra("ADD_WIFI_REQUEST_CODE",(Serializable) wiFiPointList);
                setResult(RESULT_OK, returnWifiIntent);
                finish();
            }
        });
    }

    private List<WiFiPoint> convertToWifiPointListAndFilterForLevel(){
        List<WiFiPoint> convertedList = new ArrayList<>();
        for (ScanResult scanResult: wifiList){
            if (scanResult.level >= FILTER_WIFI_LEVEL ){
                WiFiPoint wiFiPoint = new WiFiPoint(scanResult);
                convertedList.add(wiFiPoint);
            }
        }
        return convertedList;
    }
}

package com.example.androidadvanced201819.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidadvanced201819.DB.Entities.MyBeacon;
import com.example.androidadvanced201819.DB.Entities.Wifi;
import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.adapter.BeaconAdapter;
import com.example.androidadvanced201819.adapter.ScanAdapter;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class BeaconScanActivity extends AppCompatActivity implements BeaconConsumer {

    public static String EXTRA_BEACON_LIST = "beacon_list";

    private BluetoothLeScanner bluetoothLeScanner;
    private ScanSettings scanSettings;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothManager bluetoothManager;
    private BeaconManager beaconManager;
    private TextView textViewName;
    private TextView textViewAddress;
    private ListView beaconListView;
    private List<MyBeacon> beaconList = new ArrayList<>();
    public static final String ALTBEACON_LAYOUT = "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25";
    public static final String EDDYSTONE_TLM_LAYOUT = "x,s:0-1=feaa,m:2-2=20,d:3-3,d:4-5,d:6-7,d:8-11,d:12-15";
    public static final String EDDYSTONE_UID_LAYOUT = "s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19";
    public static final String EDDYSTONE_URL_LAYOUT = "s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-21v";
    public static final String URI_BEACON_LAYOUT = "s:0-1=fed8,m:2-2=00,p:3-3:-41,i:4-21v";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_scan);

        beaconListView = findViewById(R.id.beaconList);
        textViewName = findViewById(R.id.nome);
        textViewAddress = findViewById(R.id.indirizzo);
        initializeBeacon();

        Button confirm = findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCreate = new Intent();
                toCreate.putExtra(EXTRA_BEACON_LIST, (Serializable) beaconList);
                setResult(5, toCreate);
                finish();
            }
        });
    }

    private void initializeBeacon() {
        beaconManager = BeaconManager.getInstanceForApplication(this);
//        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(ALTBEACON_LAYOUT));
//        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(EDDYSTONE_TLM_LAYOUT));
//        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(EDDYSTONE_UID_LAYOUT));
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(EDDYSTONE_URL_LAYOUT));
//        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(URI_BEACON_LAYOUT));
        beaconManager.bind(this);
    }

    private void setScanSettings() {
        ScanSettings.Builder mBuilder= new ScanSettings.Builder();
        scanSettings = mBuilder.setReportDelay(0).setScanMode(ScanSettings.SCAN_MODE_LOW_POWER).build();
    }

    private void initializeBluetooth() {
        if (null == bluetoothManager){
            bluetoothManager = (BluetoothManager)this.getSystemService(Context.BLUETOOTH_SERVICE);
        }
        bluetoothAdapter = bluetoothManager.getAdapter();

        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                if (collection.size() > 0) {
                   if(region.equals(new Region("",null,null,null)));
                    {
                        List<Beacon> beacons= new ArrayList<>();
                        for(Beacon beacon:collection){
                            beacons.add(beacon);
                            beaconList.add(new MyBeacon(beacon.getBluetoothName(),beacon.getBluetoothAddress()));
                        }
                        BeaconAdapter beaconAdapter= new BeaconAdapter(BeaconScanActivity.this,beacons);
                        beaconListView.setAdapter(beaconAdapter);

                    }
                }
            }
        });

        try{
            beaconManager.startRangingBeaconsInRegion(new Region("myMonitoringUniqueId",null,null,null));
        } catch(RemoteException e){

        }
    }
}

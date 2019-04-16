package com.example.androidadvanced201819.IntentServices;

import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.example.androidadvanced201819.DB.DbHelper;
import com.example.androidadvanced201819.DB.Entities.MyBeacon;
import com.example.androidadvanced201819.DB.Entities.UserProfile;
import com.example.androidadvanced201819.DB.Entities.Wifi;
import com.example.androidadvanced201819.activities.BeaconScanActivity;
import com.example.androidadvanced201819.activities.CreateProfileActivity;
import com.example.androidadvanced201819.activities.NfcActivity;
import com.example.androidadvanced201819.adapter.BeaconAdapter;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeaconService extends Service implements BeaconConsumer {


    private BeaconManager beaconManager;
    private Tag tag;
    private DbHelper dbHelper;
    private List<UserProfile> profiles;

    public BeaconService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final DbHelper dbHelper= DbHelper.getInstance(getApplicationContext());
        final List<UserProfile> profiles=dbHelper.getProfiles();// TODO: gestire l'inserimento a db di un nuovo profilo, da riavviare il service o cercare altro.

        beaconManager = BeaconManager.getInstanceForApplication(this);

    }

    @Override
    public void onDestroy() {
        Log.w("myapp2","destroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_SHORT).show();
        super.onStartCommand(intent, flags, startId);
        Log.w("myapp2","startcommand");
        return START_STICKY;
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                Log.w("myapp5","sta cercando");
                if (collection.size() > 0) {
                    Log.w("myapp5","trovato");
                    if(region.equals(new Region("",null,null,null)));
                    {
                        List<Beacon> beacons= new ArrayList<>();
                        for(Beacon beacon:collection){
                            beacons.add(beacon);
                        }
                        dbHelper = DbHelper.getInstance(getApplicationContext());
                        profiles = dbHelper.getProfiles();
                        for(UserProfile profile: profiles) {
                            if(profile.getMetodoDiRilevamento().equals(CreateProfileActivity.WIFI)){
                                List<MyBeacon> dBeacons= dbHelper.getBeacon(profile.getId());

                                if(checkBeacons(beacons,dBeacons))
                                {
                                    Log.d("myApp2","trovato");
                                    Toast.makeText(getApplicationContext(),"Funziona",Toast.LENGTH_SHORT).show();
//                                    activateProfile(profile);
//                                serve=false;//da vedere come gestire al posto di chiudere il service
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });

        try{
            beaconManager.startRangingBeaconsInRegion(new Region("myMonitoringUniqueId",null,null,null));
        } catch(RemoteException e){

        }
    }

    private boolean checkBeacons(List<Beacon> beacons, List<MyBeacon> dBeacons) {
        int foundConnections=0;
        for (Beacon result: beacons){
            for (MyBeacon beacon:dBeacons){
                if(result.getBluetoothAddress().equals(beacon.getAddress())){
                    foundConnections++;
                    break;
                }
            }
        }

        return foundConnections>0;
    }
}
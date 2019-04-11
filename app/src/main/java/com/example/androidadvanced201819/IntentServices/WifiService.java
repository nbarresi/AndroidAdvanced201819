package com.example.androidadvanced201819.IntentServices;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.example.androidadvanced201819.DB.DbHelper;
import com.example.androidadvanced201819.DB.Entities.UserProfile;
import com.example.androidadvanced201819.DB.Entities.Wifi;
import com.example.androidadvanced201819.activities.CreateProfileActivity;

import java.util.List;

public class WifiService extends IntentService{

    private boolean serve = true;

    public WifiService() {
        super("");
    }

    public WifiService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("myapp2","onCreate");

        final DbHelper dbHelper= new DbHelper(getApplicationContext());
        final List<UserProfile> profiles=dbHelper.getProfiles();// TODO: gestire l'inserimento a db di un nuovo profilo, da riavviare il service o cercare altro.

        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);


            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    boolean success = intent.getBooleanExtra(
                            WifiManager.EXTRA_RESULTS_UPDATED, false);
                    List<ScanResult> results = wifiManager.getScanResults();
                    Log.d("myApp2","scanCompleted");
                    for(UserProfile profile: profiles) {
                        if(profile.getMetodoDiRilevamento().equals(CreateProfileActivity.WIFI)){
                            List<Wifi> wifis= dbHelper.getWifi(profile.getId());

                            if(checkWifis(results,wifis))
                            {
                                Log.d("myApp2","trovato");
                                Toast.makeText(context,"Funziona",Toast.LENGTH_SHORT).show();
                                //activateProfile(profile);
//                                serve=false;//da vedere come gestire al posto di chiudere il service
                                break;
                            }
                        }
                    }

                }
            }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));


        wifiManager.startScan();

    }

    private boolean checkWifis(List<ScanResult> results, List<Wifi> wifis) {
        int foundConnections=0;
        for (ScanResult result: results){
            for (Wifi wifi:wifis){
                if(result.BSSID.equals(wifi.getBssid())){
                    foundConnections++;
                    break;
                }
            }
        }
        float percentage = 0;
        if(wifis.size()>0) {
            percentage = 100 * foundConnections / wifis.size();
        }

        Log.d("myApp2","percentage: "+percentage);
        Log.d("myApp2","foundConnection: "+foundConnections);
        Log.d("myApp2","wifis: "+wifis.size());
        Log.d("myApp2","wifis: "+results.size());
        return percentage>40;
    }

    @Override
    public void onDestroy() {
        Log.w("myapp2","destroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_SHORT).show();
        super.onStartCommand(intent, flags, startId);
        Log.w("myapp2","startcommand");
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        while(serve){

        }
    }
}
package com.example.androidadvanced201819.IntentServices;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class GpsService extends IntentService {

    public GpsService(String name) {
        super(name);
    }

    public GpsService(){
        super("name");
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_SHORT).show();
        super.onStartCommand(intent, flags, startId);
        Log.w("GESUUU","DAI CAZZO");
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        String dataString = workIntent.getDataString();
        Toast.makeText(getApplicationContext(),"Daje cazzo",Toast.LENGTH_LONG).show();
        Log.d("GESUUU","DAI CAZZO");
    }

//    private boolean containedInRadius(LatLng newCoords,LatLng toCheck,int radius){
//        int R = 6371; // Earth's radius in Km
//        return radius <= Math.acos(Math.sin(newCoords.latitude)*Math.sin(toCheck.latitude) +
//                Math.cos(newCoords.latitude)*Math.cos(toCheck.latitude) *
//                        Math.cos(toCheck.longitude-newCoords.longitude)) * R;
//    }
}
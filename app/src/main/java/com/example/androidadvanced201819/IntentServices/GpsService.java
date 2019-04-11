package com.example.androidadvanced201819.IntentServices;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.androidadvanced201819.DB.DbHelper;
import com.example.androidadvanced201819.DB.Entities.UserProfile;
import com.example.androidadvanced201819.activities.CreateProfileActivity;

import java.util.List;

public class GpsService extends IntentService implements LocationListener{

    private boolean serve= true;

    public GpsService(String name) {
        super(name);
    }

    public GpsService(){
        super("name");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("myapp","onCreate");

        DbHelper dbHelper= new DbHelper(getApplicationContext());
        List<UserProfile> profiles=dbHelper.getProfiles();

        for(UserProfile profile: profiles) {
            if (profile.getMetodoDiRilevamento().equals(CreateProfileActivity.GPS)) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
                }
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.w("myapp","destroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_SHORT).show();
        super.onStartCommand(intent, flags, startId);
        Log.w("myapp","startcommand");
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        while(serve){

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.w("myapp","locationChanged");
        DbHelper dbHelper= new DbHelper(getApplicationContext());
        List<UserProfile> profiles=dbHelper.getProfiles();
        for(UserProfile profile: profiles){
            if (profile.getMetodoDiRilevamento().equals(CreateProfileActivity.GPS)){
                String[] splitted = profile.getValoreMetodo().split(";");

                Location profileLocation=new Location("");
                profileLocation.setLatitude(Double.parseDouble(splitted[0]));
                profileLocation.setLongitude(Double.parseDouble(splitted[1]));
                Log.d("myApp",location.getLatitude()+";"+location.getLongitude()+" trovato");
                Log.d("myApp",profileLocation.toString()+";"+profileLocation.getLongitude()+" ciclato");

                if(Integer.parseInt(splitted[2])>=location.distanceTo(profileLocation)){
                    Log.d("myApp","trovato");
//                  activateProfile(profile);
                    break;
                }
            }
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
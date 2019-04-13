package com.example.androidadvanced201819.IntentServices;

import android.Manifest;
import android.app.Activity;
import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.androidadvanced201819.DB.DbHelper;
import com.example.androidadvanced201819.DB.Entities.UserProfile;
import com.example.androidadvanced201819.activities.CreateProfileActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GpsService extends IntentService implements LocationListener {

    private boolean serve = true;
    private BluetoothAdapter bluetoothAdapter;
    private Timer timer;

    public GpsService(String name) {
        super(name);
    }

    public GpsService() {
        super("name");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("myapp", "onCreate");

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        DbHelper dbHelper = new DbHelper(getApplicationContext());
        List<UserProfile> profiles = dbHelper.getProfiles();

        for (UserProfile profile : profiles) {
            if (profile.getMetodoDiRilevamento().equals(CreateProfileActivity.GPS)) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 20, this);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 20, this);
                }
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.w("myapp", "destroy");
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_SHORT).show();
        super.onStartCommand(intent, flags, startId);
        Log.w("myapp", "startcommand");
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        timer = new Timer();
        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timered");
            }
        };
        timer.scheduleAtFixedRate(t, 5000, 5000);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.w("myapp", "locationChanged");
        DbHelper dbHelper = new DbHelper(getApplicationContext());
        List<UserProfile> profiles = dbHelper.getProfiles();
        for (UserProfile profile : profiles) {
            if (profile.getMetodoDiRilevamento().equals(CreateProfileActivity.GPS)) {
                String[] splitted = profile.getValoreMetodo().split(";");

                Location profileLocation = new Location("");
                profileLocation.setLatitude(Double.parseDouble(splitted[0]));
                profileLocation.setLongitude(Double.parseDouble(splitted[1]));

                Log.d("myApp", Integer.parseInt(splitted[2]) * 10 + " Radius");
                Log.d("myApp", location.distanceTo(profileLocation) + " Distance");

                if ((Integer.parseInt(splitted[2]) * 10) >= location.distanceTo(profileLocation)) {
                    Log.d("myApp", "trovato");
                    activateProfile(profile);
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

    private void activateProfile(UserProfile profile) {



        //FIXME: Brightness, trovare il modo di non aprire i settings per i permessi
        /*if (Settings.System.canWrite(getApplicationContext())) {
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            float floatBright = (float) profile.getLuminosita();
            float floated = (floatBright / 100) * 255;
            Log.d("myApp", "Bright: " + (int) floated);

            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, (int) floated);

        } else {
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }*/

        //Volume
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        float floatVol = (float) profile.getVolume();
        float volume = (floatVol / 100) * 15;
        Log.d("myApp", "Volume: " + volume);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) volume, AudioManager.FLAG_SHOW_UI);


        //Bluetooth Switch - L'emulatore non supporta il bluetooth
        if(bluetoothAdapter != null) {
            boolean bluetoothStatus = bluetoothAdapter.isEnabled();

            if (profile.isBluetooth() && bluetoothStatus) {
                bluetoothAdapter.enable();
            }
            if (!profile.isBluetooth() && !bluetoothStatus) {
                bluetoothAdapter.disable();
            }
        }

        //TODO: Controllare se il service wifi è in uso
        //Wifi Switch
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(profile.isWifi());

        //App open
        if(!profile.getAppPackage().isEmpty()){
            Intent intentToApp = getApplicationContext()
                    .getPackageManager()
                    .getLaunchIntentForPackage(profile.getAppPackage());
            startActivity(intentToApp);
        }

    }
}
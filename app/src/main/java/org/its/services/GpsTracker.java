package org.its.services;

import android.Manifest;
import android.annotation.SuppressLint;
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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.its.db.dao.ProfiloDao;
import org.its.db.entities.Gps;
import org.its.db.entities.Profilo;
import org.its.utilities.ProfileTypeEnum;

import java.util.List;

public class GpsTracker extends IntentService implements LocationListener {

    private ProfiloDao profiloDao = new ProfiloDao();
    private Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 25; // 25 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 15 * 1; // 15 seconds

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GpsTracker() {
        super(null);
    }

    public GpsTracker(String name) {
        super(name);
        new GpsTracker(null, name, null);
    }

    public GpsTracker(Context context, String name, LocationListener locationListener) {
        super(name);
        this.mContext = context;
        getLocation(locationListener);
    }

    public GpsTracker(Context context, String name) {
        super(name);
        new GpsTracker(context, name, null);
    }


    public Location getLocation(LocationListener locationListener) {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                /*if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }*/
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {

                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.NETWORK_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_TIME_BW_UPDATES, locationListener != null ? locationListener : this);
                           /* if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }*/
                        }
                    }
                }
            }

        } catch (Exception e) {
            Log.d("eccezioneLoc", e.getMessage());
        }
        return location;
    }

    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app.
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GpsTracker.this);
        }
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }



    @Override
    public void onLocationChanged(Location location) {
        //Log.d("location change", "dkfhej");
        profiloDao.openConn(getApplicationContext());
        List<Profilo> list = profiloDao.getByMetodo(ProfileTypeEnum.GPS);
        profiloDao.closeConn();
        Log.d("listaSize", "" + list.size());

        boolean findedMatch = false;
        Gson gson = new Gson();

        for (int i = 0; i < list.size() && !findedMatch; i++) {
           /* Logica per capire se un punto è in una mappa

           Gps posizione = new Gson().fromJson(list.get(i).getRilevazione(), Gps.class);
            circle.setCenter(new LatLng(posizione.getLatitudine(),posizione.getLongitudine()));
            google.maps.geometry.spherical.computeDistanceBetween(
                    new google.maps.LatLng( 100, 20 ),
                    new google.maps.LatLng( 101, 21 )
            ) <= 2000*/
        }

    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Log.d("on create","fiods");
            LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //  Log.d("onStartCommand", "comando");
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        while (true) {
        }

    }

}


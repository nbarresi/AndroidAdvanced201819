package org.its.login.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.SeekBar;

import com.example.androidadvanced201819.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.its.db.entities.Coordinates;

public class MapActivity extends Activity {

    private Context context;
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                mLocationManager.removeUpdates(mLocationListener);
            } else {

            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
    private LocationManager mLocationManager;

    LatLng latLng = new LatLng(41.9109, 12.4818);

    int radius = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity_layout);

        this.context = context;

        MapFragment map = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                //Ho già i permessi necessari
            } else {
                //Richiedo i permessi
                checkLocationPermission();
            }}


        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                if(ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

                    Location location = getCurrentLocation();
                    if(location != null){
                        latLng = new LatLng(location.getLatitude(),location.getLongitude());

                    }

                }

                googleMap.setMyLocationEnabled(true);


                final Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng).title("center"));
                final Circle circle = googleMap.addCircle(new CircleOptions().center(latLng).radius(100).strokeColor(Color.RED));

                googleMap.moveCamera(
                        CameraUpdateFactory.newLatLng(latLng));

                googleMap.animateCamera(
                        CameraUpdateFactory.zoomTo(getZoomLevel(circle)));


                SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar);
                seekBar.setProgress(100);
                seekBar.setMax(1000);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                        // TODO Auto-generated method stub
                        circle.setRadius(progress);
                        googleMap.animateCamera(
                                CameraUpdateFactory.zoomTo(getZoomLevel(circle)));
                        radius = progress;
                    }
                });

                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLngMap) {
                        System.out.println("longClick");
                        latLng = latLngMap;
                        marker.setPosition(latLng);
                        circle.setCenter(latLng);
                        googleMap.moveCamera(
                                CameraUpdateFactory.newLatLng(latLng));
                        googleMap.animateCamera(
                                CameraUpdateFactory.zoomTo(getZoomLevel(circle)));
                    }
                });
            }
        });


    }



    @Override
    public void onBackPressed() {
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(latLng.latitude);
        coordinates.setLongitude(latLng.longitude);
        coordinates.setRadius(radius);
        Intent returnCoordinatesIntent = new Intent();
        returnCoordinatesIntent.putExtra("ADD_COORDINATES_REQUEST_CODE", coordinates);
        setResult(NewProfileActivity.RESULT_MAP_ACTIVITY, returnCoordinatesIntent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void checkLocationPermission() {
        if(ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            /*if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Alert prima di richiedere i permessi
            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},99 );
            }*/
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},99 );
        }
    }

    private Location getCurrentLocation() {
        this.checkLocationPermission();
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        if (!(isGPSEnabled || isNetworkEnabled)){
            return null;
        }

        else {
            if (isNetworkEnabled) {

                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        0, 0, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (isGPSEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        0, 0, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        return location;
    }

    public int getZoomLevel(Circle circle) {
        int zoomLevel = 11;
        if (circle != null) {
            double radius = circle.getRadius() + circle.getRadius() / 2;
            double scale = radius / 500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }

}
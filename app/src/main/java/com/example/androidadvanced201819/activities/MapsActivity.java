package com.example.androidadvanced201819.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidadvanced201819.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements LocationListener {

    public static final String[] MAPS_PERMISSION = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    public static final int REQUEST_MAP = 1;

    public static final String EXTRA_MAP_LAT_LNG  = "mapLatLng";

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location location;
    private LatLng latLngProvided= new LatLng(45.464211, 9.191383);//Sulla madonnina
    private SeekBar radius;
    private Circle circle;
    private TextView range;

    @Override
    public void onBackPressed() {
        Intent toCreate = new Intent();
        String formatted = latLngProvided.latitude +";"+ latLngProvided.longitude+";"+radius.getProgress();
        toCreate.putExtra(EXTRA_MAP_LAT_LNG,formatted);
        setResult(2,toCreate);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        radius = (SeekBar) findViewById(R.id.raggio);
        radius.setProgress(10);

        range = (TextView) findViewById(R.id.km);
        range.setText("100");

        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            String coordinates = intent.getStringExtra(CreateProfileActivity.EXTRA_PROFILE_LAT_LNG);

            String[] splitted = coordinates.split(";");
            double lat = Double.parseDouble(splitted[0]);
            double lng = Double.parseDouble(splitted[1]);
            radius.setProgress(Integer.parseInt(splitted[2]));
            range.setText(Integer.parseInt(splitted[2])*10+"");

            latLngProvided = new LatLng(lat, lng);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200,20, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 200, 20, this);
            if(latLngProvided == null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location!=null) {
                    latLngProvided = new LatLng(location.getLatitude(), location.getLongitude());
                }
            }
        }else{
            ActivityCompat.requestPermissions(MapsActivity.this,MAPS_PERMISSION,REQUEST_MAP);
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                mMap.addMarker(new MarkerOptions().position(latLngProvided).title(" Marker"));
                circle = mMap.addCircle(new CircleOptions()
                        .center(latLngProvided)
                        .radius(radius.getProgress()*10)
                        .strokeColor(Color.RED)
                );
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngProvided));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circle.getCenter(),getZoomLevel(circle)));

                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        mMap.clear();
                        updateMarker(latLng);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circle.getCenter(),mMap.getCameraPosition().zoom));
                        latLngProvided = latLng;
                    }
                });

                radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        Integer update = progress * 10;
                        circle.setRadius(update);
                        range.setText(update.toString());
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngProvided));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circle.getCenter(),getZoomLevel(circle)));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

            }
        });


    }

    private void updateMarker(LatLng latLngInput) {
        mMap.addMarker(new MarkerOptions()
                .position(latLngInput)
        );

        circle = mMap.addCircle(new CircleOptions()
                .center(latLngInput)
                .radius(circle.getRadius())
                .strokeColor(Color.RED));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngInput));
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_MAP){
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200,20, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 200, 20, this);
            if(latLngProvided == null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location!=null) {
                    latLngProvided = new LatLng(location.getLatitude(), location.getLongitude());
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mMap != null) {
            mMap.clear();
            latLngProvided = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLngProvided).title(" Marker"));
            circle = mMap.addCircle(new CircleOptions()
                    .center(latLngProvided)
                    .radius(circle.getRadius())
                    .strokeColor(Color.RED)
            );
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngProvided));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circle.getCenter(), getZoomLevel(circle)));
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

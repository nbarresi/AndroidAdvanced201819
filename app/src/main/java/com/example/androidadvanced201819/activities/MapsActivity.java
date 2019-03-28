package com.example.androidadvanced201819.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.androidadvanced201819.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location location;
    private LatLng latLngProvided;
    private SeekBar radius;
    private Circle circle;
    private TextView range;

    //TODO: Permissions Management

    @Override
    public void onBackPressed() {
        Intent toCreate = new Intent();
        String formatted = latLngProvided.latitude +";"+ latLngProvided.longitude;
        toCreate.putExtra("latLng",formatted);
        setResult(2,toCreate);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            String coordinates = intent.getStringExtra("latLng");
            String[] splitted = coordinates.split(";");
            double lat = Double.parseDouble(splitted[0]);
            double lng = Double.parseDouble(splitted[1]);
            latLngProvided = new LatLng(lat, lng);
        }

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        radius = (SeekBar) findViewById(R.id.raggio);
        radius.setProgress(10);

        range = (TextView) findViewById(R.id.km);
        range.setText("100");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if(latLngProvided == null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                latLngProvided = new LatLng(location.getLatitude(), location.getLongitude());
            }
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngProvided, 18.0f));
                mMap.addMarker(new MarkerOptions().position(latLngProvided).title(" Marker"));
                circle = mMap.addCircle(new CircleOptions()
                        .center(latLngProvided)
                        .radius(100)
                        .strokeColor(Color.RED)
                );

                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        //latLngProvided = latLng;
                        mMap.clear();
                        updateMarker(latLng);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circle.getCenter(),mMap.getCameraPosition().zoom));
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
                .radius(100)
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

}

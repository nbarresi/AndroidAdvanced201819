package com.example.androidadvanced201819.activities;

import android.Manifest;
import android.content.Context;
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
import com.google.android.gms.maps.CameraUpdate;
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
    private LatLng latLng;
    private SeekBar radius;
    private Circle circle;
    private TextView range;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        radius = (SeekBar) findViewById(R.id.raggio);
        radius.setProgress(10);

        range = (TextView) findViewById(R.id.km);
        range.setText("100");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));
                mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                circle = mMap.addCircle(new CircleOptions()
                        .center(latLng)
                        .radius(100)
                        .strokeColor(Color.RED)
                );

                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
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

}

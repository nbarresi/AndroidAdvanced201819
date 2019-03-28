package org.its.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.androidadvanced201819.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.its.db.entities.Gps;
import org.its.utilities.ResultsCode;

public class MapActivity extends Activity {

    private final Gps gps = new Gps();
    private Intent receivedIntent;

    @Override
    protected void onCreate(Bundle saBundle) {
        super.onCreate(saBundle);
        setContentView(R.layout.map_layout);


        final MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);


        mapManagement(map);


    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.mapAlertMessage)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void mapManagement(MapFragment map) {


        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {

                receivedIntent = getIntent();
                final TextView radiusMeter = findViewById(R.id.radiusMeter);
                SeekBar seekBar = findViewById(R.id.raggioSeekbar);
                boolean isAnUpdate = receivedIntent.getBooleanExtra("IsUpdate", false);
                if (isAnUpdate) {
                    Gps gettedGps = new Gson().fromJson(receivedIntent.getStringExtra("gps"), Gps.class);
                    seekBar.setProgress((gettedGps.getRaggio() - 500) / 10);
                    radiusMeter.setText("" + gettedGps.getRaggio());
                    gps.setRaggio(gettedGps.getRaggio());
                    gps.setLongitudine(gettedGps.getLongitudine());
                    gps.setLatitudine(gettedGps.getLatitudine());
                } else {
                    LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        buildAlertMessageNoGps();
                    }

                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        gps.setRaggio(500);
                        gps.setLongitudine(location.getLongitude());
                        gps.setLatitudine(location.getLatitude());
                    }

                }


                LatLng currentLatLong = new LatLng(gps.getLatitudine(), gps.getLongitudine());

                final Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(currentLatLong)
                        .title(getString(R.string.currentPositionLabel)));

                final Circle circle = googleMap.addCircle(new CircleOptions()
                        .center(currentLatLong)
                        .radius(gps.getRaggio())
                        .strokeColor(Color.RED));


                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        int radius = 500 + (progress * 10);
                        radiusMeter.setText("" + radius);
                        circle.setRadius(radius);
                        gps.setRaggio(radius);
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(getZoomLevel(circle)));

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);


                CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLatLong).zoom(15.4f).build();

                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        marker.setPosition(latLng);
                        circle.setCenter(latLng);
                        gps.setLatitudine(latLng.latitude);
                        gps.setLongitudine(latLng.longitude);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                circle.getCenter(), getZoomLevel(circle)));

                    }
                });


            }
        });
    }

    private int getZoomLevel(Circle circle) {
        int zoomLevel = 11;
        if (circle != null) {
            double radius = circle.getRadius() + circle.getRadius() / 2;
            double scale = radius / 600;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent returnIntent = new Intent();
        returnIntent.putExtra(ResultsCode.MAP_RESULT, gps);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    /*private void setCircle(int raggio, MapFragment map) {
        Circle circle = googleMap.addCircle(new CircleOptions()
                .center(currentLatLong)
                .radius(500)
                .strokeColor(Color.RED));
    }*/
}

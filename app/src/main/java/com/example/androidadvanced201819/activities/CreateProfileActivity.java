package com.example.androidadvanced201819.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidadvanced201819.DB.DbHelper;
import com.example.androidadvanced201819.DB.Entities.UserProfile;
import com.example.androidadvanced201819.DB.Entities.Wifi;
import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.adapter.ProfileAdapter;

import java.util.List;

public class CreateProfileActivity extends AppCompatActivity {

    public static final String EXTRA_PROFILE_LAT_LNG = "profileLatLng";

    private static final String GPS = "GPS";
    private static final String WIFI = "WIFI";
    private static final String NFC = "NFC";
    private static final String BEACON = "Beacon";

    private static final String[] MAPS_PERMISSION = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int REQUEST_MAP = 1;
    private static final int REQUEST_WIFI = 3;

    private DbHelper dbHelper;
    private TextView appName;
    private UserProfile profilo;

    private String appPackage = "";
    private String appNameVal = "";
    private String methodValue = "";
    private List<Wifi> wifis;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        dbHelper = new DbHelper(getApplicationContext());

        final Intent intent = getIntent();

        final EditText textName = (EditText) findViewById(R.id.name);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.methods);
        final RadioButton gpsButton = (RadioButton) findViewById(R.id.gps);
        final RadioButton nfcButton = (RadioButton) findViewById(R.id.nfc);
        final RadioButton wifiButton = (RadioButton) findViewById(R.id.wifi);
        final RadioButton beaconButton = (RadioButton) findViewById(R.id.beacon);

        final SeekBar luminosita = (SeekBar) findViewById(R.id.luminosita);
        final SeekBar volume = (SeekBar) findViewById(R.id.volume);
        final Switch bluetoothSwitch = (Switch) findViewById(R.id.bluetoothSwitch);
        final Switch wifiSwitch = (Switch) findViewById(R.id.wifiSwitch);
        final TextView appButton = (TextView) findViewById(R.id.appButton);
        appName = (TextView) findViewById(R.id.appName);

        Button addProfileButton = (Button) findViewById(R.id.confirmProfile);

        if (intent.hasExtra(ProfileAdapter.EXTRA_PROFILE)) {

            addProfileButton.setText(getString(R.string.editProfileButton));

            profilo = (UserProfile) intent.getSerializableExtra(ProfileAdapter.EXTRA_PROFILE);

            textName.setText(profilo.getNome());
            luminosita.setProgress(profilo.getLuminosita());
            volume.setProgress(profilo.getVolume());
            bluetoothSwitch.setChecked(profilo.isBluetooth());
            wifiSwitch.setChecked(profilo.isWifi());

            String selectedMethod = profilo.getMetodoDiRilevamento();

            switch (selectedMethod) {
                case NFC:
                    nfcButton.setChecked(true);
                    break;
                case WIFI:
                    wifiButton.setChecked(true);
                    break;
                case BEACON:
                    beaconButton.setChecked(true);
                    break;
                case GPS:
                    gpsButton.setChecked(true);
                    break;
            }

            appName.setText(profilo.getAppName());
            appName.setVisibility(View.VISIBLE);
        }

        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = textName.getText().toString();
                appNameVal = appName.getText().toString();

                RadioButton selected = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                String selectedString = selected != null ? selected.getText().toString() : "";

                boolean bluetooth = bluetoothSwitch.isChecked();
                boolean wifi = wifiSwitch.isChecked();

                UserProfile profile = new UserProfile(null, name, selectedString, methodValue, luminosita.getProgress(), volume.getProgress(), bluetooth, wifi, appPackage, appNameVal);

                if (intent.hasExtra(ProfileAdapter.EXTRA_PROFILE)) {
                    profile.setId(profilo.getId());
                    dbHelper.updateProfile(profile);

                    dbHelper.removeWifiById(profilo.getId());
                    for (Wifi single : wifis) {
                        single.setIdProfilo(profilo.getId());
                        dbHelper.insertWifi(single);
                    }

                    finish();
                } else {

                    if (!profile.getNome().equals("") && !profile.getMetodoDiRilevamento().equals("")) {
                        int idProfilo = (int) dbHelper.insertProfile(profile);
                        for (Wifi single : wifis) {
                            single.setIdProfilo(idProfilo);
                            dbHelper.insertWifi(single);
                        }
                    } else {
                        Toast.makeText(CreateProfileActivity.this, "Inserire dati mancanti", Toast.LENGTH_SHORT).show();
                    }


                    finish();
                }
            }
        });

        textName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    goToMaps();
                } else {
                    ActivityCompat.requestPermissions(CreateProfileActivity.this, MAPS_PERMISSION, REQUEST_MAP);
                }


            }
        });

        nfcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        wifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wifiIntent = new Intent(getApplicationContext(), WifiScanActivity.class);
                startActivityForResult(wifiIntent, REQUEST_WIFI);
            }
        });

        beaconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        appButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAppList = new Intent(getApplicationContext(), ApplicationListActivity.class);
                startActivityForResult(toAppList, 1);
            }
        });
    }

    private void goToMaps() {
        Intent toMap = new Intent(getApplicationContext(), MapsActivity.class);
        if (profilo != null) {
            toMap.putExtra(EXTRA_PROFILE_LAT_LNG, profilo.getValoreMetodo());
        }
        if (!methodValue.isEmpty()) {
            toMap.putExtra(EXTRA_PROFILE_LAT_LNG, methodValue);
        }
        startActivityForResult(toMap, 2);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case 1:
                appNameVal = (String) data.getExtras().getString(ApplicationListActivity.EXTRA_APP_NAME);
                appPackage = (String) data.getExtras().getString(ApplicationListActivity.EXTRA_APP_PACKAGE);

                appName.setText(appNameVal);
                appName.setVisibility(View.VISIBLE);
                break;

            case 2:
                methodValue = (String) data.getExtras().getString(MapsActivity.EXTRA_MAP_LAT_LNG);
                break;
            case 3:
                wifis = (List<Wifi>) data.getExtras().get(WifiScanActivity.EXTRA_WIFI_LIST);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_MAP) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                goToMaps();
            } else {
                Toast.makeText(CreateProfileActivity.this, "Permessi non concessi", Toast.LENGTH_SHORT).show();
            }
        }
    }


}

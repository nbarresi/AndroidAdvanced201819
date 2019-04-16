package com.example.androidadvanced201819.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
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

import org.altbeacon.beacon.Beacon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreateProfileActivity extends AppCompatActivity {

    public static final String EXTRA_PROFILE_LAT_LNG = "profileLatLng";

    public static final String GPS = "GPS";
    public static final String WIFI = "WIFI";
    private static final String NFC = "NFC";
    private static final String BEACON = "Beacon";

    public static final int REQUEST_APP = 1;
    public static final int REQUEST_MAP = 2;
    public static final int REQUEST_WIFI = 3;
    public static final int REQUEST_NFC = 4;

    private SharedPreferences sharedPreferences;
    private DbHelper dbHelper;
    private TextView appName;
    private UserProfile profilo;

    private String appPackage = "";
    private String appNameVal = "";
    private String methodValue = "";
    private List<Wifi> wifis = new ArrayList<Wifi>();
    private List<Beacon> beacons = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        dbHelper = new DbHelper(getApplicationContext());

        sharedPreferences = this.getSharedPreferences("utilities", MODE_PRIVATE);
        boolean nfcSupport = sharedPreferences.getBoolean("support", false);

        final Intent intent = getIntent();

        final EditText textName = (EditText) findViewById(R.id.name);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.methods);
        final RadioButton gpsButton = (RadioButton) findViewById(R.id.gps);

        final RadioButton nfcButton = (RadioButton) findViewById(R.id.nfc);
        nfcButton.setEnabled(nfcSupport);

        final RadioButton wifiButton = (RadioButton) findViewById(R.id.wifi);
        final RadioButton beaconButton = (RadioButton) findViewById(R.id.beacon);

        final SeekBar luminosita = (SeekBar) findViewById(R.id.luminosita);
        final CheckBox autoLuminosita = (CheckBox) findViewById(R.id.autoLuminosita);
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

            if (profilo.getLuminosita() != -1) {
                luminosita.setProgress(profilo.getLuminosita());
            } else {
                autoLuminosita.setChecked(true);
            }

            volume.setProgress(profilo.getVolume());
            bluetoothSwitch.setChecked(profilo.isBluetooth());
            wifiSwitch.setChecked(profilo.isWifi());

            String selectedMethod = profilo.getMetodoDiRilevamento();
            methodValue = profilo.getValoreMetodo();

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

                int checkLuminosita = autoLuminosita.isChecked() ? -1 : luminosita.getProgress();

                boolean bluetooth = bluetoothSwitch.isChecked();
                boolean wifi = wifiSwitch.isChecked();

                UserProfile profile = new UserProfile(null, name, selectedString, methodValue, checkLuminosita, volume.getProgress(), bluetooth, wifi, appPackage, appNameVal);

                if (intent.hasExtra(ProfileAdapter.EXTRA_PROFILE)) {
                    profile.setId(profilo.getId());
                    dbHelper.updateProfile(profile);

                    if (wifi && !wifis.isEmpty()) {
                        dbHelper.removeWifiById(profilo.getId());
                        for (Wifi single : wifis) {
                            single.setIdProfilo(profilo.getId());
                            dbHelper.insertWifi(single);
                        }
                    }

                    finish();
                } else {

                    if (!profile.getNome().equals("") && !profile.getMetodoDiRilevamento().equals("")) {
                        int idProfilo = (int) dbHelper.insertProfile(profile);

                        if (wifi && !wifis.isEmpty()) {
                            for (Wifi single : wifis) {
                                single.setIdProfilo(idProfilo);
                                dbHelper.insertWifi(single);
                            }
                        }
                        finish();
                    } else {
                        Toast.makeText(CreateProfileActivity.this, "Inserire dati mancanti", Toast.LENGTH_SHORT).show();
                    }

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
                goToMaps();
            }
        });

        nfcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nfcIntent = new Intent(getApplicationContext(), NfcActivity.class);
                startActivityForResult(nfcIntent, REQUEST_NFC);
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
                Intent wifiIntent = new Intent(getApplicationContext(), BeaconScanActivity.class);
                startActivityForResult(wifiIntent, REQUEST_WIFI);
            }
        });

        appButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAppList = new Intent(getApplicationContext(), ApplicationListActivity.class);
                startActivityForResult(toAppList, REQUEST_APP);
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
        startActivityForResult(toMap, REQUEST_MAP);
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
            case REQUEST_APP:
                appNameVal = (String) data.getExtras().getString(ApplicationListActivity.EXTRA_APP_NAME);
                appPackage = (String) data.getExtras().getString(ApplicationListActivity.EXTRA_APP_PACKAGE);

                appName.setText(appNameVal);
                appName.setVisibility(View.VISIBLE);
                break;

            case REQUEST_MAP:
                methodValue = (String) data.getExtras().getString(MapsActivity.EXTRA_MAP_LAT_LNG);
                break;
            case REQUEST_WIFI:
                wifis = (List<Wifi>) data.getExtras().get(WifiScanActivity.EXTRA_WIFI_LIST);
                break;

            case REQUEST_NFC:
                methodValue = (String) data.getExtras().getString(NfcActivity.EXTRA_NFC_TAG);
                break;
            case 5:
                beacons = (List<Beacon>) data.getExtras().get(BeaconScanActivity.EXTRA_BEACON_LIST);
                break;
        }
    }


}

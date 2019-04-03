package com.example.androidadvanced201819.activities.profile;

import android.annotation.SuppressLint;
import android.app.Application;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.activities.MapsActivity;
import com.example.androidadvanced201819.activities.WiFiActivity;
import com.example.androidadvanced201819.dataaccess.DataAccessUtils;
import com.example.androidadvanced201819.database.ProfileDatabaseManager;
import com.example.androidadvanced201819.model.Option;
import com.example.androidadvanced201819.model.Profile;
import com.example.androidadvanced201819.model.WiFi;

public class ProfileManagement extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private static final int MY_PERMISSIONS_REQUEST_WIFI = 2;
    EditText name;
    SeekBar brightness, volume;
    Switch bluetooth, wifi;
    RadioGroup optionRadio;
    TextView application, title;
    CheckBox auto_brightness;
    Profile profile;

    int option;
    protected int volumeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        name = findViewById(R.id.profile_name);
        brightness = (SeekBar) findViewById(R.id.seekBarBrightness);
        volume = (SeekBar) findViewById(R.id.seekBarVolume);
        bluetooth = findViewById(R.id.switchBluethoot);
        wifi = findViewById(R.id.switchWifi);
        auto_brightness = findViewById(R.id.checkbox_autoBrightness);
        optionRadio = findViewById(R.id.optionRadio);
        application = findViewById(R.id.applicationName);
        title = findViewById(R.id.profile_title);

        Intent det = getIntent();
        if (det.hasExtra("position")) {
            title.setText("Modifica");
            int position = det.getExtras().getInt("position");
            profile = DataAccessUtils.getItemByPosition(getApplicationContext(), position);
            this.setProfileSettings(position);
        } else {
            title.setText("Aggiungi");
            profile = new Profile();
            profile.setApplication("Application");
            profile.setApplicationName("Application");
        }
    }

    public void onRadioDetectionMethod(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.gpsRadioButton:
                if (checked) {
                    option = Option.GPS.getOption();
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(
                                this, Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
                            Intent goToMap = new Intent(this, MapsActivity.class);
                            startActivityForResult(goToMap, 2);
                            //Ho già i permessi necessari
                        } else {
                            //Richiedo i permessi (vedi slide successiva)
                            checkLocationPermission(MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    }

                }
                break;
            case R.id.wifiRadioButton:
                if (checked) {
                    option = Option.WIFI.getOption();
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(
                                this, Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
                            Intent goToWifi = new Intent(this, WiFiActivity.class);
                            startActivity(goToWifi);
                            //Ho già i permessi necessari
                        } else {
                            //Richiedo i permessi (vedi slide successiva)
                            checkLocationPermission(MY_PERMISSIONS_REQUEST_WIFI);
                        }
                    }
                }
                break;
            case R.id.nfcRadioButton:
                if (checked)
                    option = Option.NFC.getOption();
                break;
            case R.id.beaconRadioButton:
                if (checked)
                    option = Option.BEACON.getOption();
                break;
        }

    }

    public void chooseApplication(View view) {
        Intent chooseApplication = new Intent(ProfileManagement.this, ApplicationActivity.class);
        startActivityForResult(chooseApplication, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            String appPackage = data.getExtras().getString("package");
            String appName = data.getExtras().getString("name");
            profile.setApplication(appPackage);
            profile.setApplicationName(appName);
            application.setText(appName);
        } else if (requestCode == 2 && data != null) {
            profile.setCoordinate(data.getExtras().getString("LatLong"));
        }
    }

    public void createProfile(final View view) {
        final String profileName = name.getText().toString();

        profile.setNome(profileName);
        profile.setOption(option);
        profile.setVolume(volume.getProgress());
        profile.setbrightness(brightness.getProgress());

        if (bluetooth.isChecked()) {
            profile.setBluethoot(1);
        } else {
            profile.setBluethoot(0);
        }

        if (wifi.isChecked()) {
            profile.setWifi(1);
        } else {
            profile.setWifi(0);
        }

        if (auto_brightness.isChecked()) {
            profile.setAuto_birghtness(1);
        } else {
            profile.setAuto_birghtness(0);
        }

        ProfileDatabaseManager profileDatabaseManager = new ProfileDatabaseManager(getApplicationContext());
        profileDatabaseManager.open();
        Long cursor = profileDatabaseManager.createProfile(profile);
        Log.d("cursor", cursor.toString());
        profileDatabaseManager.close();

        Intent backToMain = new Intent(ProfileManagement.this, MainActivity.class);
        startActivity(backToMain);
    }

    @SuppressLint("SetTextI18n")
    private void setProfileSettings(int position) {
        Button editButton = findViewById(R.id.buttonEditProfile);
        Button createButton = findViewById(R.id.buttonCreateProfile);

        editButton.setVisibility(View.VISIBLE);
        createButton.setVisibility(View.GONE);

        option = profile.getOption();
        int brightnessValue = profile.getbrightness();
        volumeValue = profile.getVolume();
        name.setText(profile.getNome());
        brightness.setProgress(brightnessValue);
        volume.setProgress(volumeValue);

        if (profile.getBluethoot() == 1) {
            bluetooth.setChecked(true);
        } else {
            bluetooth.setChecked(false);
        }
        if (profile.getWifi() == 1) {
            wifi.setChecked(true);
        } else {
            wifi.setChecked(false);
        }
        if (profile.getAuto_birghtness() == 1) {
            auto_brightness.setChecked(true);
        } else {
            auto_brightness.setChecked(false);
        }
        if (!profile.getApplicationName().equals("")) {
            application.setText(profile.getApplicationName());
        } else {
            application.setText("Application");
        }

        RadioButton radioButton = null;
        Option optionSelected = Option.getEnumfromValue(option);
        switch (optionSelected) {
            case GPS:
                radioButton = optionRadio.findViewById(R.id.gpsRadioButton);
                break;
            case WIFI:
                radioButton = optionRadio.findViewById(R.id.wifiRadioButton);
                break;
            case NFC:
                radioButton = optionRadio.findViewById(R.id.nfcRadioButton);
                break;
            case BEACON:
                radioButton = optionRadio.findViewById(R.id.beaconRadioButton);
                break;
        }
        radioButton.setChecked(true);
    }

    public void editProfile(View view) {
        final String profileName = name.getText().toString();

        profile.setNome(profileName);
        profile.setOption(option);
        profile.setVolume(volume.getProgress());
        profile.setbrightness(brightness.getProgress());

        if (bluetooth.isChecked()) {
            profile.setBluethoot(1);
        } else {
            profile.setBluethoot(0);
        }

        if (wifi.isChecked()) {
            profile.setWifi(1);
        } else {
            profile.setWifi(0);
        }

        if (auto_brightness.isChecked()) {
            profile.setAuto_birghtness(1);
        } else {
            profile.setAuto_birghtness(0);
        }

        profile.setApplication(profile.getApplication());

        if (profile.getOption() == 1) {
            profile.setCoordinate(profile.getCoordinate());
        } else {
            profile.setCoordinate("");
        }
        ProfileDatabaseManager profileDatabaseManager = new ProfileDatabaseManager(getApplicationContext());
        profileDatabaseManager.open();
        profileDatabaseManager.editProfile(profile);
        profileDatabaseManager.close();

        Intent backToMain = new Intent(ProfileManagement.this, MainActivity.class);
        startActivity(backToMain);
    }

    private void checkLocationPermission(int request) {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//Alert prima di richiedere i permessi
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        request);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        request);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent goToMap = new Intent(this, MapsActivity.class);
                    startActivity(goToMap);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_WIFI: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent goToWiFi = new Intent(this, WiFiActivity.class);
                    startActivity(goToWiFi);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

}

package com.example.androidadvanced201819.activities.profile;

import android.content.Intent;
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

import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.dataaccess.DataAccessUtils;
import com.example.androidadvanced201819.database.ProfileDatabaseManager;
import com.example.androidadvanced201819.model.Option;
import com.example.androidadvanced201819.model.Profile;

public class CreateProfile extends AppCompatActivity {

    EditText name;
    SeekBar brightness, volume;
    Switch bluethoot, wifi;
    RadioGroup optionRadio;
    int option;
    CheckBox auto_brightness;
    private int brightnessValue;
    private int volumeValue;
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        name = findViewById(R.id.profile_name);
        brightness = (SeekBar) findViewById(R.id.seekBarBrightness);
        volume = (SeekBar) findViewById(R.id.seekBarVolume);
        bluethoot = findViewById(R.id.switchBluethoot);
        wifi = findViewById(R.id.switchWifi);
        auto_brightness = findViewById(R.id.checkbox_autoBrightness);
        optionRadio = findViewById(R.id.optionRadioGroup);

        Intent det = getIntent();
        if (det.hasExtra("position")) {
            int position = det.getExtras().getInt("position");
            setTitle("Modifica profilo");
            profile = DataAccessUtils.getItemByPosition(getApplicationContext(), position);
            this.setProfileSettings(position);
        } else {
            setTitle("Crea profilo");
            profile = new Profile();
        }
    }

    protected void onRadioDetectionMethod(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.gpsRadioButton:
                if (checked) {
                    option = Option.GPS.getOption();
                }
                break;
            case R.id.wifiRadioButton:
                if (checked)
                    option = Option.WIFI.getOption();
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

    protected void onCheckboxAutoBrightness(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_autoBrightness:
                if (checked) {

                    // Put some meat on the sandwich
                } else {

                    // Remove the meat
                }
                break;
            // TODO: Veggie sandwich
        }
    }

    public void createProfile(final View view) {
        final String profileName = name.getText().toString();


        profile.setNome(profileName);
        profile.setOption(option);
        profile.setVolume(volume.getProgress());
        profile.setbrightness(brightness.getProgress());

        if (bluethoot.isChecked()) {
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

        profile.setApplication("application");
        ProfileDatabaseManager profileDatabaseManager = new ProfileDatabaseManager(getApplicationContext());
        profileDatabaseManager.open();
        Long cursor = profileDatabaseManager.createProfile(profile);
        Log.d("cursor", cursor.toString());
        profileDatabaseManager.close();

        Intent backToMain = new Intent(CreateProfile.this, MainActivity.class);
        startActivity(backToMain);
    }

    private void setProfileSettings(int position) {
        Button editButton = findViewById(R.id.buttonEditProfile);
        Button createButton = findViewById(R.id.buttonCreateProfile);

        editButton.setVisibility(View.VISIBLE);
        createButton.setVisibility(View.GONE);

        option = profile.getOption();
        brightnessValue = profile.getbrightness();
        volumeValue = profile.getVolume();

        name.setText(profile.getNome());
        brightness.setProgress(brightnessValue);
        volume.setProgress(volumeValue);
        if (profile.getBluethoot() == 1) {
            bluethoot.setChecked(true);
        } else {
            bluethoot.setChecked(false);
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

        if (bluethoot.isChecked()) {
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

        profile.setApplication("application");
        ProfileDatabaseManager profileDatabaseManager = new ProfileDatabaseManager(getApplicationContext());
        profileDatabaseManager.open();
        profileDatabaseManager.editProfile(profile);
        profileDatabaseManager.close();

        Intent backToMain = new Intent(CreateProfile.this, MainActivity.class);
        startActivity(backToMain);
    }

}

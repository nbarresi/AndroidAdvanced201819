package com.example.androidadvanced201819.activities.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;

import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.database.ProfileDatabaseManager;
import com.example.androidadvanced201819.model.Option;
import com.example.androidadvanced201819.model.Profile;

public class CreateProfile extends AppCompatActivity {

    EditText name;
    SeekBar brightness, volume;
    Switch bluethoot, wifi;
    Option option;
    CheckBox auto_brightness;
    private int brightnessValue;
    private int volumeValue;

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

        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brightnessValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumeValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onRadioDetectionMethod(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.gpsRadioButton:
                if (checked) {
                    option = Option.GPS;
                }
                break;
            case R.id.wifiRadioButton:
                if (checked)
                    option = Option.WIFI;
                break;
            case R.id.nfcRadioButton:
                if (checked)
                    option = Option.NFC;
                break;
            case R.id.beaconRadioButton:
                if (checked)
                    option = Option.BEACON;
                break;
        }
    }

    public void onCheckboxAutoBrightness(View view) {
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
        final Profile profile = new Profile();

        final String profileName = name.getText().toString();


        profile.setNome(profileName);
        profile.setOption(option.getValue());
        profile.setVolume(volumeValue);
        profile.setbrightness(brightnessValue);

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
}

package org.its.login.activities;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidadvanced201819.R;

import org.its.db.DBHelper;
import org.its.db.entities.Profile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NewProfileActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private EditText editTextNameProfile;
    private RadioGroup radioConnectivityGroup;
    private RadioButton radioConnectivityButton;
    private int radioGroupId;
    private Button btnDisplay;
    private Profile profile = new Profile();
    private SeekBar barBrightness;
    private CheckBox brightnessCheckbox;
    private SeekBar barVolume;
    private AudioManager audioManager;
    private Switch switchBluetooth;
    private TextView textViewAppList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);

        //check esistenza di un profilo temporaneo
        Intent intentAddApp = getIntent();
        Boolean fromAppList = intentAddApp.getSerializableExtra("tempProfileWithApp") != null;
        if (fromAppList){
            profile = (Profile) intentAddApp.getSerializableExtra("tempProfileWithApp") ;
        }
        //add all listeners
        setCreateUserButtonListener();
        setNameProfileEditTextListener(fromAppList);
        setListenerOnRadioButton(fromAppList);
        setOnBarBrightnessChangeListener(fromAppList);
        setAutoBrightnessCheckboxListener(fromAppList);
        setOnBarVolumeChangeListener(fromAppList);
        setBluetoothSwitchListener(fromAppList);
        onClickAppListText(fromAppList);
    }

    private void setNameProfileEditTextListener(Boolean fromAppList) {
        editTextNameProfile = (EditText) findViewById(R.id.profile_name);
        if (fromAppList) editTextNameProfile.setText(profile.getName());
    }


    //metodo rilevamento
    private void setListenerOnRadioButton(Boolean fromAppList) {
        radioConnectivityGroup = (RadioGroup) findViewById(R.id.radioConnectivity);
        if (fromAppList){
            switch (profile.getMetodoRilevamento().toLowerCase()){
                case "gps":
                    radioConnectivityButton = (RadioButton) findViewById(R.id.gps);
                    break;
                case "wifi":
                    radioConnectivityButton = (RadioButton) findViewById(R.id.wifi);
                    break;
                case "nfc":
                    radioConnectivityButton = (RadioButton) findViewById(R.id.nfc);
                    break;
                case "beacon":
                    radioConnectivityButton = (RadioButton) findViewById(R.id.beacon);
                    break;
            }
            radioConnectivityButton.setChecked(true);
        }
    }



    //luminosità
    private void setOnBarBrightnessChangeListener(Boolean fromAppList) {
        barBrightness = (SeekBar) findViewById(R.id.brightness);

        if (fromAppList) barBrightness.setProgress(profile.getLuminosita());
            else barBrightness.setProgress(1);

        barBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Context context = getApplicationContext();
                boolean canWriteSettings = Settings.System.canWrite(context);
                if (canWriteSettings) {
                    //Because max screen brightness value is 255 But max seekbar value is 100, so need to convert.
                    int screenBrightnessValue = progress * 255 / 100;
                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue);
                } else {
                    // Show Can modify system settings panel to let user add WRITE_SETTINGS permission for this app.
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + context.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    //luminosità auto
    private void setAutoBrightnessCheckboxListener(Boolean fromAppList) {
        brightnessCheckbox = (CheckBox) findViewById(R.id.auto_brightness);
        if (fromAppList && profile.getLuminosita() == 0 ) brightnessCheckbox.setChecked(true); //temp valore arbitrario
    }

    //volume
    private void setOnBarVolumeChangeListener(Boolean fromAppList) {
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        barVolume = (SeekBar) findViewById(R.id.volume);

        barVolume.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        barVolume.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));

        if (fromAppList) barVolume.setProgress(profile.getVolume());

        barVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    //switch bluetooth
    private void setBluetoothSwitchListener(Boolean fromAppList) {
        switchBluetooth = (Switch) findViewById(R.id.bluetooth);
        if (fromAppList) switchBluetooth.setChecked(profile.isBluetooth());
    }

    //lista di app
    private void onClickAppListText(Boolean fromAppList) {
        textViewAppList = (TextView) findViewById(R.id.app_list_text);
        textViewAppList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfileValue();
                Intent intentAppList = new Intent(NewProfileActivity.this, AddAppListActivity.class);
                intentAppList.putExtra("tempProfile", profile);
                startActivity(intentAppList);
            }
        });

        if (fromAppList) textViewAppList.setText(profile.getApp());
    }


    //create User
    private void setCreateUserButtonListener() {
        dbHelper = new DBHelper(getApplicationContext());
        btnDisplay = (Button) findViewById(R.id.crea_profilo);
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfileValue();
                if (profile.getName().isEmpty()) {
                    Toast.makeText(NewProfileActivity.this,
                            "PROFILE NAME VALUE IS NOT DEFINED", Toast.LENGTH_SHORT).show();
                } else if (profile.getApp() == null) { //TEMP
                    Toast.makeText(NewProfileActivity.this,
                            "APP VALUE IS NOT DEFINED", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.insertProfile(profile);
                    Intent intentGoToProfileList = new Intent(NewProfileActivity.this, ProfileListActivity.class );
                    startActivity(intentGoToProfileList);
                }
            }
        });
    }

    private void setProfileValue() {
        profile.setName(editTextNameProfile.getText().toString());

        radioGroupId = radioConnectivityGroup.getCheckedRadioButtonId();
        radioConnectivityButton = (RadioButton) findViewById(radioGroupId);
        profile.setMetodoRilevamento(radioConnectivityButton.getText().toString());

        if (brightnessCheckbox.isChecked()) {
            profile.setLuminosita(0);   //0 quando auto
        } else profile.setLuminosita(barBrightness.getProgress());

        profile.setVolume(barVolume.getProgress());

        profile.setBluetooth(switchBluetooth.isChecked());
    }
}


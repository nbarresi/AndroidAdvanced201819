package org.its.login.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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
    private Boolean isEditUser = false;

    private final int ADD_APP_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);
        Intent editProfile = getIntent();

        isEditUser = editProfile.getSerializableExtra("editedProfile") != null;
        if (isEditUser) {
            profile = (Profile) editProfile.getSerializableExtra("editedProfile");
        }
        //add all listeners
        setCreateUserButtonListener(isEditUser);
        setNameProfileEditTextListener();
        setListenerOnRadioButton();
        setOnBarBrightnessChangeListener();
        setAutoBrightnessCheckboxListener();
        setOnBarVolumeChangeListener();
        setBluetoothSwitchListener();
        onClickAppListText();
    }

    //nome profilo (Obbligatorio)
    private void setNameProfileEditTextListener() {
        editTextNameProfile = (EditText) findViewById(R.id.profile_name);
        if (isEditUser) editTextNameProfile.setText(profile.getName());
    }


    //metodo rilevamento
    private void setListenerOnRadioButton() {
        radioConnectivityGroup = (RadioGroup) findViewById(R.id.radioConnectivity);
        if (isEditUser) {
            switch (profile.getMetodoRilevamento().toLowerCase()) {
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
    private void setOnBarBrightnessChangeListener() {
        barBrightness = (SeekBar) findViewById(R.id.brightness);

        if (isEditUser) barBrightness.setProgress(profile.getLuminosita());
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
    private void setAutoBrightnessCheckboxListener() {
        brightnessCheckbox = (CheckBox) findViewById(R.id.auto_brightness);
                            if (isEditUser && profile.getLuminosita() == 0 ) brightnessCheckbox.setChecked(true); //temp valore arbitrario

    }

    //volume
    private void setOnBarVolumeChangeListener() {
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        barVolume = (SeekBar) findViewById(R.id.volume);

        barVolume.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        barVolume.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));

                            if (isEditUser) barVolume.setProgress(profile.getVolume());


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
    private void setBluetoothSwitchListener() {
        switchBluetooth = (Switch) findViewById(R.id.bluetooth);
                            if (isEditUser) switchBluetooth.setChecked(profile.isBluetooth());

    }

    //lista di app
    private void onClickAppListText() {
        textViewAppList = (TextView) findViewById(R.id.app_list_text);
        if (isEditUser) textViewAppList.setText(setLabelAppName());

        textViewAppList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfileValue();
                Intent intentAddAppList = new Intent(NewProfileActivity.this, AddAppListActivity.class);
                startActivityForResult(intentAddAppList, ADD_APP_REQUEST_CODE);
            }
        });
    }


    //create User
    private void setCreateUserButtonListener(final Boolean isEditUser) {
        dbHelper = new DBHelper(getApplicationContext());
        btnDisplay = (Button) findViewById(R.id.crea_profilo);
        if (isEditUser) btnDisplay.setText("MODIFICA");

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfileValue();
                if (profile.getName().isEmpty()) {
                    Toast.makeText(NewProfileActivity.this,
                            "PROFILE NAME VALUE IS NOT DEFINED", Toast.LENGTH_SHORT).show();
                } else {
                    profile.setApp(setPackageAppName());

                    if (isEditUser) {
                        dbHelper.updateProfile(profile);
                    } else dbHelper.insertProfile(profile);

                    Intent intentGoToProfileList = new Intent(NewProfileActivity.this, ProfileListActivity.class);
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

    private String setPackageAppName() {
        String result = profile.getApp();
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> appList = pm.getInstalledApplications(0);
        for (ApplicationInfo applicationInfo : appList) {
            String label = applicationInfo.loadLabel(pm).toString();
            if (label.equals(profile.getApp())) {
                return applicationInfo.packageName;
            }
        }
        return result;
    }

    private String setLabelAppName() {
        String result = profile.getApp();
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> appList = pm.getInstalledApplications(0);
        for (ApplicationInfo applicationInfo : appList) {
            String packageName = applicationInfo.packageName;
            if (packageName.equals(profile.getApp())) {
                return applicationInfo.loadLabel(pm).toString();
            }
        }
        return result;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //codice per manipolare i dati ritornati
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADD_APP_REQUEST_CODE:
                    profile.setApp(data.getStringExtra("ADD_APP_REQUEST_CODE"));
                        textViewAppList.setText(profile.getApp());
                    break;

                //TODO






            }
        }

    }


}


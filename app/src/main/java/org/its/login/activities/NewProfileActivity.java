package org.its.login.activities;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidadvanced201819.R;

import org.its.db.CoordinatesDBHelper;
import org.its.db.NfcPointDBHelper;
import org.its.db.ProfileDBHelper;
import org.its.db.ProfileNfcPointsDBHelper;
import org.its.db.ProfileWifiPointsDBHelper;
import org.its.db.WiFiPointDBHelper;
import org.its.db.entities.Coordinates;
import org.its.db.entities.Profile;
import org.its.db.entities.ProfileNfcPoints;
import org.its.db.entities.ProfileWiFiPoints;
import org.its.db.entities.WiFiPoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewProfileActivity extends AppCompatActivity {

    private Profile profile = new Profile();
    private Coordinates coordinates = new Coordinates();
    private List<WiFiPoint> wiFiPointList = new ArrayList<>();
    ProfileWiFiPoints profileWiFiPoints = new ProfileWiFiPoints();
    private ProfileNfcPoints profileNfcPoints = new ProfileNfcPoints();

    private ProfileDBHelper profileDbHelper;
    private CoordinatesDBHelper coordinatesDBHelper;
    private ProfileWifiPointsDBHelper profileWifiPointsDBHelper;
    private ProfileNfcPointsDBHelper profileNfcPointsDBHelper;
    private NfcPointDBHelper nfcPointDBHelper;

    private EditText editTextNameProfile;
    private RadioGroup radioConnectivityGroup;
    private RadioButton radioConnectivityButton;
    private int radioGroupId;
    private Button btnDisplay;

    int initialBrightness = 0;
    private SeekBar barBrightness;
    private CheckBox brightnessCheckbox;
    private SeekBar barVolume;
    private AudioManager audioManager;
    private Switch switchBluetooth;
    private TextView textViewAppList;
    private Boolean isEditUser = false;

    public final int ADD_APP_REQUEST_CODE = 0;
    public final int ADD_COORDINATES_REQUEST_CODE = 1;
    private static final int ADD_WIFI_REQUEST_CODE = 2;
    private static final int ADD_NFC_REQUEST_CODE = 3;
    public static int RESULT_MAP_ACTIVITY = 1111;
    private static final int ADD_BEACON_REQUEST_CODE = 4 ;
    private String nfcId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);
        profileDbHelper = new ProfileDBHelper(getApplicationContext());
        coordinatesDBHelper = new CoordinatesDBHelper(getApplicationContext());
        profileWifiPointsDBHelper = new ProfileWifiPointsDBHelper(getApplicationContext());



        Intent editProfile = getIntent();
        isEditUser = editProfile.getSerializableExtra("editedProfile") != null;
        if (isEditUser) {
            profile = (Profile) editProfile.getSerializableExtra("editedProfile");
            if (profile.getMetodoRilevamento().equals("GPS")) {
                coordinates = coordinatesDBHelper.getCoordinatesByIdProfile(profile.getId());
            }
        }
        //add all listeners
        saveInitialBrightness();
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
        radioConnectivityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final RadioButton rb = (RadioButton) findViewById(checkedId);
                rb.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        switch (rb.getText().toString().toLowerCase()) {
                            case "gps":
                                Intent intentAddCoordinates = new Intent(NewProfileActivity.this, MapActivity.class);
                                startActivityForResult(intentAddCoordinates, ADD_COORDINATES_REQUEST_CODE);
                                break;
                            case "wifi":
                                Intent intentAddWifi = new Intent(NewProfileActivity.this, WifiActivity.class);
                                startActivityForResult(intentAddWifi, ADD_WIFI_REQUEST_CODE);
                                radioConnectivityButton = (RadioButton) findViewById(R.id.wifi);
                                break;
                            case "nfc":
                                radioConnectivityButton = (RadioButton) findViewById(R.id.nfc);
                                Intent intentAddNfc = new Intent(NewProfileActivity.this, NFCActivity.class);
                                startActivityForResult(intentAddNfc, ADD_NFC_REQUEST_CODE);
                                break;
                            case "beacon":
                                radioConnectivityButton = (RadioButton) findViewById(R.id.beacon);
                                Intent intentAddBeacon = new Intent(NewProfileActivity.this, BeaconActivity.class);
                                startActivityForResult(intentAddBeacon, ADD_BEACON_REQUEST_CODE);
                                break;
                        }
                    }
                });
            }
        });

        if (isEditUser && profile.getMetodoRilevamento() != null) {
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
                boolean canWriteSettings = false;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    canWriteSettings = Settings.System.canWrite(context);
                }
                if (canWriteSettings) {

                    //Because max screen brightness value is 255 But max seekbar value is 100, so need to convert.
                    int screenBrightnessValue = progress * 255 / 100;
                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue);
                } else {
                    showAlertBrightnessPermission();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Context context = getApplicationContext();
                boolean canWriteSettings = false;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    canWriteSettings = Settings.System.canWrite(context);
                }
                if (canWriteSettings) {
                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, initialBrightness);
                }
            }
        });
    }

    private void saveInitialBrightness() {
        Context context = getApplicationContext();
        boolean canWriteSettings = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            canWriteSettings = Settings.System.canWrite(context);
        }
        if (canWriteSettings) {
            try {
                initialBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlertBrightnessPermission() {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("Permission required")
                .setMessage("You need to confirm permission to dinamically change brightness")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveInitialBrightness();
                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //luminosità auto
    private void setAutoBrightnessCheckboxListener() {
        brightnessCheckbox = (CheckBox) findViewById(R.id.auto_brightness);
        brightnessCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean canWriteSettings = false;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    canWriteSettings = Settings.System.canWrite(getApplicationContext());
                }
                if (canWriteSettings) {

                } else showAlertBrightnessPermission();
            }
        });

        if (isEditUser && profile.getLuminosita() == 0)
            brightnessCheckbox.setChecked(true); //temp valore arbitrario

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
                    saveDataOnDBs();
                    Intent intentGoToProfileList = new Intent(NewProfileActivity.this, ProfileListActivity.class);
                    startActivity(intentGoToProfileList);
                }
            }
        });
    }

    private void saveDataOnDBs() {
        profile.setApp(setPackageAppName());
        if (isEditUser) {
            profileDbHelper.updateProfile(profile);
            switch (profile.getMetodoRilevamento().toLowerCase()) {
                case "gps":
                    coordinates.setIdProfile(profile.getId());
                    coordinatesDBHelper.updateCoordinates(coordinates);
                    break;
                case "wifi":
                    profileWiFiPoints.setIdProfile(profile.getId());
                    profileWiFiPoints.setWiFiPoints(wiFiPointList);
                    profileWifiPointsDBHelper.updateProfileWifiPoints(profileWiFiPoints);
                    break;
                case  "nfc":
                    profileNfcPoints.setIdProfile(profile.getId());
                    profileNfcPoints.setIdNfc(nfcId);
                    profileNfcPointsDBHelper.updateProfileNfcPoints(profileNfcPoints);
                    break;
            }
        }
            else {
            profileDbHelper.insertProfile(profile);
            switch (profile.getMetodoRilevamento().toLowerCase()) {
                case "gps":
                    coordinates.setIdProfile(profileDbHelper.getLastInsertedProfileId());
                    coordinatesDBHelper.insertCoordinates(coordinates);
                    break;
                case "wifi":
                    profileWiFiPoints.setIdProfile(profileDbHelper.getLastInsertedProfileId());
                    profileWiFiPoints.setWiFiPoints(wiFiPointList);
                    profileWifiPointsDBHelper.insertProfileWiFiPoint(profileWiFiPoints);
                    break;
                case "nfc":
                    profileNfcPoints.setIdProfile(profileDbHelper.getLastInsertedProfileId());
                    profileNfcPoints.setIdNfc(nfcId);
                    profileNfcPointsDBHelper.insertProfileNfcPoint(profileNfcPoints);
            }
        }

        //TODO BEACON
    }


    private void setProfileValue() {
        profile.setName(editTextNameProfile.getText().toString());

        radioGroupId = radioConnectivityGroup.getCheckedRadioButtonId();
        radioConnectivityButton = (RadioButton) findViewById(radioGroupId);

        if (radioConnectivityButton != null)
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
        if (profile.getApp() != null) { String result = profile.getApp();
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
        return null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //codice per manipolare i dati ritornati
        if (data != null)
            if (resultCode == RESULT_OK){
                switch (requestCode) {
                    case ADD_APP_REQUEST_CODE:
                        profile.setApp(data.getStringExtra("ADD_APP_REQUEST_CODE"));
                        textViewAppList.setText(profile.getApp());
                        break;
                    case ADD_COORDINATES_REQUEST_CODE:
                        if ( data.getSerializableExtra("ADD_COORDINATES_REQUEST_CODE" )!= null){
                            coordinates = (Coordinates) data.getSerializableExtra("ADD_COORDINATES_REQUEST_CODE");
                        }
                        break;
                    case ADD_WIFI_REQUEST_CODE:
                        if (data.getSerializableExtra("ADD_WIFI_REQUEST_CODE") != null){
                            wiFiPointList =  (List<WiFiPoint>) data.getSerializableExtra("ADD_WIFI_REQUEST_CODE");
                        } else wiFiPointList.clear();
                        break;
                    case ADD_NFC_REQUEST_CODE:
                        if (data.getSerializableExtra("ADD_NFC_REQUEST_CODE") != null){
                            nfcId =  data.getStringExtra("ADD_NFC_REQUEST_CODE");
                        }
                    //TODO beacon
                }
            } else radioConnectivityButton.setChecked(false);
    }
}



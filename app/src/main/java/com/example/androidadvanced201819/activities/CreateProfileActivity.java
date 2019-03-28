package com.example.androidadvanced201819.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;

import com.example.androidadvanced201819.DB.DbHelper;
import com.example.androidadvanced201819.DB.Entities.UserProfile;
import com.example.androidadvanced201819.R;

public class CreateProfileActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private String selectedMethod="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        dbHelper = new DbHelper(getApplicationContext());

        final Intent intent= getIntent();

        final EditText textName = (EditText) findViewById(R.id.name);
        final RadioButton gpsButton = (RadioButton) findViewById(R.id.gps);
        final RadioButton nfcButton = (RadioButton) findViewById(R.id.nfc);
        final RadioButton wifiButton = (RadioButton) findViewById(R.id.wifi);
        final RadioButton beaconButton = (RadioButton) findViewById(R.id.beacon);
        final SeekBar luminosita= (SeekBar) findViewById(R.id.luminosita);
        final SeekBar volume= (SeekBar) findViewById(R.id.volume);
        final Switch bluetoothSwitch = (Switch) findViewById(R.id.bluetoothSwitch);
        final Switch wifiSwitch = (Switch) findViewById(R.id.wifiSwitch);

        Button addProfileButton = (Button) findViewById(R.id.confirmProfile);
        if(intent.hasExtra("PROFILE")){
            addProfileButton.setText("MODIFICA");
            UserProfile profilo = (UserProfile) intent.getSerializableExtra("PROFILE");
            textName.setText(profilo.getNome());
            luminosita.setProgress(profilo.getLuminosita());
            volume.setProgress(profilo.getVolume());
            bluetoothSwitch.setChecked(profilo.isBluetooth());
            wifiSwitch.setChecked(profilo.isWifi());
            switch(profilo.getMetodoDiRilevamento()){
                case "nfc": nfcButton.setChecked(true);break;
                case "wifi": wifiButton.setChecked(true);break;
                case "beacon": beaconButton.setChecked(true);break;
                case "gps": gpsButton.setChecked(true);break;
            }
        }
        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent.hasExtra("PROFILE")){
                    String name = textName.getText().toString();
                    boolean bluetooth = bluetoothSwitch.isChecked();
                    boolean wifi = wifiSwitch.isChecked();

                    UserProfile profile = new UserProfile(name, selectedMethod, "", luminosita.getProgress(), volume.getProgress(), bluetooth, wifi, "");

//                    dbHelper.updateProfile(profile);

                    finish();
                }else {
                    String name = textName.getText().toString();
                    boolean bluetooth = bluetoothSwitch.isChecked();
                    boolean wifi = wifiSwitch.isChecked();

                    UserProfile profile = new UserProfile(name, selectedMethod, "", luminosita.getProgress(), volume.getProgress(), bluetooth, wifi, "");

                    dbHelper.insertProfile(profile);

                    finish();
                }
            }
        });
        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMethod="gps";
            }
        });
        nfcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMethod="nfc";
            }
        });
        wifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMethod="wifi";
            }
        });
        beaconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMethod="beacon";
            }
        });
    }
}

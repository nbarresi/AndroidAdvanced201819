package com.example.androidadvanced201819.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.androidadvanced201819.R;

public class CreateProfileActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private String selectedMethod = "";
    private TextView appName;
    private String appPackage = "";
    private String appNameVal = "";
    private String methodValue = "";
    private UserProfile profilo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        dbHelper = new DbHelper(getApplicationContext());

        final Intent intent = getIntent();

        final EditText textName = (EditText) findViewById(R.id.name);
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
        if (intent.hasExtra("PROFILE")) {
            addProfileButton.setText("MODIFICA");
            profilo = (UserProfile) intent.getSerializableExtra("PROFILE");
            textName.setText(profilo.getNome());
            luminosita.setProgress(profilo.getLuminosita());
            volume.setProgress(profilo.getVolume());
            bluetoothSwitch.setChecked(profilo.isBluetooth());
            wifiSwitch.setChecked(profilo.isWifi());
            selectedMethod=profilo.getMetodoDiRilevamento();
            switch (profilo.getMetodoDiRilevamento()) {
                case "nfc":
                    nfcButton.setChecked(true);
                    break;
                case "wifi":
                    wifiButton.setChecked(true);
                    break;
                case "beacon":
                    beaconButton.setChecked(true);
                    break;
                case "gps":
                    gpsButton.setChecked(true);
                    break;
            }
            appName.setText(profilo.getAppName());
            appName.setVisibility(View.VISIBLE);
        }
        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent.hasExtra("PROFILE")) {
                    String name = textName.getText().toString();
                    appNameVal = appName.getText().toString();
                    boolean bluetooth = bluetoothSwitch.isChecked();
                    boolean wifi = wifiSwitch.isChecked();

                    UserProfile profile = new UserProfile(profilo.getId(),name, selectedMethod, methodValue, luminosita.getProgress(), volume.getProgress(), bluetooth, wifi, appPackage,appNameVal);

                    dbHelper.updateProfile(profile);

                    finish();
                } else {
                    String name = textName.getText().toString();
                    appNameVal = appName.getText().toString();
                    boolean bluetooth = bluetoothSwitch.isChecked();
                    boolean wifi = wifiSwitch.isChecked();

                    UserProfile profile = new UserProfile(name, selectedMethod, methodValue, luminosita.getProgress(), volume.getProgress(), bluetooth, wifi, appPackage,appNameVal);

                    if(!profile.getNome().equals("") && !profile.getMetodoDiRilevamento().equals("")) {
                        dbHelper.insertProfile(profile);
                    }else{
                        Toast.makeText(CreateProfileActivity.this,"Inserire dati mancanti", Toast.LENGTH_SHORT).show();
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
                selectedMethod ="gps";
                Intent toMap = new Intent(getApplicationContext(), MapsActivity.class);
                if(profilo != null && profilo.getValoreMetodo() != null){
                    toMap.putExtra("latLng",profilo.getValoreMetodo());
                }
                if(!methodValue.isEmpty()){
                    toMap.putExtra("latLng",methodValue);
                }
                startActivityForResult(toMap,2);
            }
        });
        nfcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMethod = "nfc";
            }
        });
        wifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMethod = "wifi";
            }
        });
        beaconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMethod = "beacon";
            }
        });

        appButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAppList = new Intent(getApplicationContext(), ApplicationListActivity.class);
                startActivityForResult(toAppList,1);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)ev.getRawX(), (int)ev.getRawY())) {
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

        switch (resultCode){
            case 1:
                appNameVal = (String) data.getExtras().getString("appName");
                appPackage = (String) data.getExtras().getString("appPackage");

                appName.setText(appNameVal);
                appName.setVisibility(View.VISIBLE);
                break;

            case 2:
                if(profilo != null) {
                    profilo.setValoreMetodo((String) data.getExtras().getString("latLng"));
                } else {
                    methodValue = (String) data.getExtras().getString("latLng");
                }
                break;
        }
    }

}

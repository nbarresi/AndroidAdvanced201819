package org.its.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.androidadvanced201819.R;
import com.google.gson.Gson;

import org.its.db.dao.ProfiloDao;
import org.its.db.entities.Gps;
import org.its.db.entities.Profilo;
import org.its.utilities.ProfileTypeEnum;
import org.its.utilities.RequestCodes;
import org.its.utilities.ResultsCode;


public class DetailActivity extends AppCompatActivity {

    private Profilo profiloFromIntent = null;
    private final int PERMISSION_REQUEST_CODE = 115;
    private int idForUpdate = -1;
    private TextView appChoiced = null;
    private Gps gps;


    private ProfiloDao db = new ProfiloDao();

    @Override
    protected void onCreate(Bundle saBundle) {
        super.onCreate(saBundle);
        setContentView(R.layout.detail_layout);

        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final EditText name = (EditText) findViewById(R.id.editProfileName);
        final SeekBar luminosita = findViewById(R.id.detailLuminosita);
        final SeekBar volume = findViewById(R.id.detailVolume);
        final Switch bluetooth = findViewById(R.id.detailBluetooth);
        final Switch wifi = findViewById(R.id.detailWifi);
        final CheckBox autoLuminosita = findViewById(R.id.autoLuminosita);
        final TextView app = findViewById(R.id.choiceApp);
        appChoiced = findViewById(R.id.detailApp);
        Intent currentIntent = getIntent();
        if (currentIntent.getSerializableExtra(ListActivity.PROFILE) != null) {
            profiloFromIntent = (Profilo) currentIntent.getSerializableExtra(ListActivity.PROFILE);
            name.setText(profiloFromIntent.getNome());
            luminosita.setProgress(profiloFromIntent.getLuminosita());
            volume.setProgress(profiloFromIntent.getVolume());
            wifi.setChecked(profiloFromIntent.isWifi());
            bluetooth.setChecked(profiloFromIntent.isBluetooth());
            autoLuminosita.setChecked(profiloFromIntent.isAutoLuminosita());

            switch (profiloFromIntent.getMetodo().getValue()) {
                case 0:
                    radioGroup.check(R.id.detailWIFI);
                    break;
                case 1:
                    radioGroup.check(R.id.detailBeacon);
                    break;
                case 2:
                    radioGroup.check(R.id.detailNFC);
                    break;
                case 3:
                    radioGroup.check(R.id.detailGPS);
                    break;
            }
            appChoiced.setText(profiloFromIntent.getApp());
            this.idForUpdate = profiloFromIntent.getId();
        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.detailGPS:

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            if (ContextCompat.checkSelfPermission(
                                    getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {

                                checkLocationPermission();
                            } else {
                                launchIntentToMap();
                            }
                        }


                        break;
                    case R.id.detailWIFI:
                        System.out.println("premuto radiobutton 2");
                        break;
                    case R.id.detailNFC:
                        System.out.println("premuto radiobutton 3");
                        break;
                    case R.id.detailBeacon:
                        System.out.println("premuto radiobutton 4");
                        break;
                }
            }
        });

        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent choiceAppIntent = new Intent(DetailActivity.this, AppListActivity.class);
                startActivityForResult(choiceAppIntent, RequestCodes.LIST_CODE);
            }
        });

        Button save = findViewById(R.id.saveProfile);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Profilo profiloDaSalvare = new Profilo();
                profiloDaSalvare.setId(idForUpdate);
                profiloDaSalvare.setNome(String.valueOf(name.getText()));
                profiloDaSalvare.setVolume(volume.getProgress());
                profiloDaSalvare.setLuminosita(luminosita.getProgress());
                profiloDaSalvare.setBluetooth(bluetooth.isChecked());
                profiloDaSalvare.setWifi(wifi.isChecked());
                profiloDaSalvare.setRilevazione("sample");
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.detailGPS:
                        profiloDaSalvare.setRilevazione(new Gson().toJson(gps));
                        profiloDaSalvare.setMetodo(ProfileTypeEnum.GPS);
                        break;
                    case R.id.detailWIFI:
                        profiloDaSalvare.setMetodo(ProfileTypeEnum.WIFI);
                        break;
                    case R.id.detailNFC:
                        profiloDaSalvare.setMetodo(ProfileTypeEnum.NFC);
                        break;
                    case R.id.detailBeacon:
                        profiloDaSalvare.setMetodo(ProfileTypeEnum.BEACON);
                        break;
                    default:
                        profiloDaSalvare.setMetodo(null);
                        break;
                }
                profiloDaSalvare.setAutoLuminosita(autoLuminosita.isChecked());
                profiloDaSalvare.setApp(String.valueOf(appChoiced.getText()));

                try {
                    db.openConn(getApplicationContext());
                    if (idForUpdate != -1) {
                        db.updateProfilo(profiloDaSalvare);
                        idForUpdate = -1;
                    } else {
                        db.insertProfile(profiloDaSalvare);
                    }
                    db.closeConn();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(DetailActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

    }

    private void checkLocationPermission() {
        ActivityCompat.requestPermissions(DetailActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i : grantResults) {
                if (i == PackageManager.PERMISSION_GRANTED) {
                    launchIntentToMap();
                }
            }

        }
    }

    private void launchIntentToMap(){
        Intent mapIntent = new Intent(DetailActivity.this, MapActivity.class);
        if (idForUpdate != -1) {
            mapIntent.putExtra("IsUpdate", true);

        } else {
            mapIntent.putExtra("IsUpdate", false);
        }

        if (profiloFromIntent != null) {
            mapIntent.putExtra("gps", profiloFromIntent.getRilevazione());
        }
        startActivityForResult(mapIntent, RequestCodes.MAP_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestCodes.LIST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    appChoiced.setText(data.getStringExtra(ResultsCode.LIST_RESULT));
                }
                break;
            case RequestCodes.MAP_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    gps = (Gps) data.getSerializableExtra(ResultsCode.MAP_RESULT);
                }
                break;
            default:
                break;
        }
    }//onActivityResult

}

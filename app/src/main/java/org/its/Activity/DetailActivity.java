package org.its.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
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

import org.its.db.dao.ProfiloDao;
import org.its.db.entities.Profilo;
import org.its.utilities.ProfileTypeEnum;

import java.util.List;


public class DetailActivity extends AppCompatActivity {


    public static int APP = 156;
    private int update = -1;
    private TextView appChoiced = null;

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

        Intent intent = getIntent();
        if (intent.getSerializableExtra(ListActivity.PROFILE) != null) {
            Profilo profilo = (Profilo) intent.getSerializableExtra(ListActivity.PROFILE);
            name.setText(profilo.getNome());
            luminosita.setProgress(profilo.getLuminosita());
            volume.setProgress(profilo.getVolume());
            wifi.setChecked(profilo.isWifi());
            bluetooth.setChecked(profilo.isBluetooth());
            autoLuminosita.setChecked(profilo.isAutoLuminosita());
            switch (profilo.getMetodo().getValue()) {
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
            appChoiced.setText(profilo.getApp());
            this.update = profilo.getId();
        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Intent intentRadio = new Intent(DetailActivity.this, ListActivity.class);

                switch (checkedId) {
                    case R.id.detailGPS:
                        System.out.println("premuto radiobutton 1");
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
                startActivityForResult(choiceAppIntent, APP);
            }
        });

        Button save = findViewById(R.id.saveProfile);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Profilo profiloDaSalvare = new Profilo();
                profiloDaSalvare.setId(update);
                profiloDaSalvare.setNome(String.valueOf(name.getText()));
                profiloDaSalvare.setVolume(volume.getProgress());
                profiloDaSalvare.setLuminosita(luminosita.getProgress());
                profiloDaSalvare.setBluetooth(bluetooth.isChecked());
                profiloDaSalvare.setWifi(wifi.isChecked());
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.detailGPS:
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
                profiloDaSalvare.setRilevazione("burle");
                profiloDaSalvare.setAutoLuminosita(autoLuminosita.isChecked());
                profiloDaSalvare.setApp(String.valueOf(appChoiced.getText()));

                try {
                    db.openConn(getApplicationContext());
                    if (update != -1) {
                        db.updateProfilo(profiloDaSalvare);
                        update = -1;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP) {
            if (resultCode == Activity.RESULT_OK) {
                appChoiced.setText(data.getStringExtra(AppListActivity.APPRESULT));
            }
        }
    }//onActivityResult

}

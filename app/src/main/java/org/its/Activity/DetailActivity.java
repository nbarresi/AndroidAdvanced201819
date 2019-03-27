package org.its.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;

import com.example.androidadvanced201819.R;

import org.its.db.dao.ProfiloDao;
import org.its.db.entities.Profilo;
import org.its.utilities.ProfileTypeEnum;


public class DetailActivity extends Activity {


    private ProfiloDao db;

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

        Button save = findViewById(R.id.saveProfile);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new ProfiloDao(getApplicationContext());

                Profilo profiloDaSalvare = new Profilo();
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

                try {
                    db.openConn();
                    db.insertProfile(profiloDaSalvare);
                    db.closeConn();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}

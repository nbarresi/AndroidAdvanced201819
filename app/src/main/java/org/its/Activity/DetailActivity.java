package org.its.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;

import com.example.androidadvanced201819.R;


public class DetailActivity extends Activity {



    @Override
    protected void onCreate(Bundle saBundle) {
        super.onCreate(saBundle);
        setContentView(R.layout.detail_layout);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        EditText name = (EditText) findViewById(R.id.editProfileName);
        SeekBar luminosita = findViewById(R.id.detailLuminosita);
        SeekBar volume = findViewById(R.id.detailVolume);
        Switch bluetooth = findViewById(R.id.detailBluetooth);
        Switch  wifi = findViewById(R.id.detailWifi);

        Button save = findViewById(R.id.saveProfile);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }

}

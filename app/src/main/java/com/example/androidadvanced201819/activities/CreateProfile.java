package com.example.androidadvanced201819.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.example.androidadvanced201819.R;

public class CreateProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
    }

    public void onRadioDetectionMerhod(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.gpsRadioButton:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.wifiRadioButton:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.nfcRadioButton:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.beaconRadioButton:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

    public void onCheckboxAutoBrightness(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_autoBrightness:
                if (checked){

                    // Put some meat on the sandwich
                }
                    else{

                    // Remove the meat
                }
                break;
            // TODO: Veggie sandwich
        }
    }
}

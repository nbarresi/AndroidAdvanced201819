package com.example.androidadvanced201819.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.androidadvanced201819.DB.DbHelper;
import com.example.androidadvanced201819.DB.Entities.UserProfile;
import com.example.androidadvanced201819.R;

public class CreateProfileActivity extends AppCompatActivity {

    private DbHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        dbHelper = new DbHelper(getApplicationContext());

        final EditText textName = (EditText) findViewById(R.id.name);
        final Switch bluetoothSwitch = (Switch) findViewById(R.id.bluetoothSwitch);
        final Switch wifiSwitch = (Switch) findViewById(R.id.wifiSwitch);

        Button addProfileButton = (Button) findViewById(R.id.createProfile);

        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textName.getText().toString();
                boolean bluetooth = bluetoothSwitch.isChecked();
                boolean wifi = wifiSwitch.isChecked();

                UserProfile profile = new UserProfile(name,0,0,bluetooth,wifi);

                dbHelper.insertProfile(profile);
            }
        });
    }
}

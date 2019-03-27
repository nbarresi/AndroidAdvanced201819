package com.example.androidadvanced201819.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.androidadvanced201819.R;

public class ListaProfiliActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        Button createProfile = findViewById(R.id.createProfile);

        createProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCreateProfile = new Intent(getApplicationContext(),CreateProfileActivity.class);
                startActivity(intentCreateProfile);
            }
        });


    }

}

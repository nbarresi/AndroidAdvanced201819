package com.example.androidadvanced201819.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.androidadvanced201819.CustomAdapter;
import com.example.androidadvanced201819.DB.Entities.UserProfile;
import com.example.androidadvanced201819.R;

import java.util.ArrayList;
import java.util.List;

public class ListaProfiliActivity extends AppCompatActivity {

    private CustomAdapter customAdapter;

    @Override
    public void onBackPressed() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        List<UserProfile> profileList = new ArrayList<UserProfile>();

        ListView listView = (ListView) findViewById(R.id.listView);

        customAdapter = new CustomAdapter(this,profileList);

        listView.setAdapter(customAdapter);

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

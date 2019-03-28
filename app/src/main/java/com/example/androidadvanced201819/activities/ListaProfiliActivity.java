package com.example.androidadvanced201819.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.androidadvanced201819.adapter.ProfileAdapter;
import com.example.androidadvanced201819.DB.DbHelper;
import com.example.androidadvanced201819.DB.Entities.UserProfile;
import com.example.androidadvanced201819.R;

import java.util.List;

public class ListaProfiliActivity extends AppCompatActivity {

    private ProfileAdapter customAdapter;
    public static final int ADDING_PROFILE_CODE = 666;
    private DbHelper dbHelper;


    @Override
    protected void onResume() {
        super.onResume();
        customAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        dbHelper = new DbHelper(this);

        List<UserProfile> profileList = dbHelper.getProfiles();

        ListView listView = (ListView) findViewById(R.id.listView);

        customAdapter = new ProfileAdapter(this,profileList,dbHelper);
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

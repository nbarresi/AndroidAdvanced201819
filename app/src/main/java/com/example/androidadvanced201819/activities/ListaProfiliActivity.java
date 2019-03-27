package com.example.androidadvanced201819.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.androidadvanced201819.R;

public class ListaProfiliActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        String[] array = new String[]{"Casa","Ufficio"};

        ListView listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item_layout,R.id.itemName,array);

        listView.setAdapter(adapter);

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

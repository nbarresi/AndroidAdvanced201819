package com.example.androidadvanced201819.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.androidadvanced201819.activities.AdapterActivity;
import com.example.androidadvanced201819.dataaccess.DataAccessUtils;
import com.example.androidadvanced201819.R;

public class MainActivity extends AppCompatActivity {

    AdapterActivity adapterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        DataAccessUtils.initDataSource(this);
        adapterActivity = new AdapterActivity(getApplicationContext());
        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapterActivity);
        setTitle("Lista profili");
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void goToCreateProfile(View view) {
        Intent goToCreateProfile = new Intent(MainActivity.this, CreateProfile.class);
        startActivity(goToCreateProfile);
        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateProfile = new Intent(MainActivity.this, CreateProfile.class);
                startActivity(goToCreateProfile);
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterActivity.setValues();
    }
}

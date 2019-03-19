package com.example.androidadvanced201819.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.androidadvanced201819.DataAccess.DataAccessUtils;
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
}

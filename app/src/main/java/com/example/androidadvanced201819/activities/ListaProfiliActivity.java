package com.example.androidadvanced201819.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidadvanced201819.IntentServices.GpsService;
import com.example.androidadvanced201819.adapter.ProfileAdapter;
import com.example.androidadvanced201819.DB.DbHelper;
import com.example.androidadvanced201819.DB.Entities.UserProfile;
import com.example.androidadvanced201819.R;

import java.util.List;

public class ListaProfiliActivity extends AppCompatActivity {

    private ProfileAdapter customAdapter;
    private DbHelper dbHelper;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        customAdapter.resetData();
        customAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        dbHelper = DbHelper.getInstance(this);
        dbHelper = new DbHelper(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || Settings.System.canWrite(getApplicationContext())) {
                requestPermissions(permissions, 0);
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }

        boolean nfcSupport = NfcAdapter.getDefaultAdapter(this) != null;
        sharedPreferences = this.getSharedPreferences("utilities", Context.MODE_PRIVATE);

        if (!nfcSupport && !sharedPreferences.contains("support")) {
            Toast.makeText(ListaProfiliActivity.this, "Il Telefono non supporta Nfc", Toast.LENGTH_LONG).show();
        }

        if (!sharedPreferences.contains("support")) {
            editor = sharedPreferences.edit();
            editor.putBoolean("support", nfcSupport);
            editor.apply();
        }

        List<UserProfile> profileList = dbHelper.getProfiles();

        ListView listView = (ListView) findViewById(R.id.listView);

        customAdapter = new ProfileAdapter(this, profileList, dbHelper);
        listView.setAdapter(customAdapter);

        Button createProfile = findViewById(R.id.createProfile);

        createProfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Intent intentCreateProfile = new Intent(getApplicationContext(), CreateProfileActivity.class);
                startActivity(intentCreateProfile);
            }
        });


    }

}

package org.its.login.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.androidadvanced201819.R;

import org.its.db.ProfileDBHelper;
import org.its.db.entities.Profile;
import org.its.login.adapters.ProfileListRecyclerAdapter;
import org.its.login.services.WifiReceiver;
import org.its.login.services.WifiService;

import java.util.ArrayList;
import java.util.List;

public class ProfileListActivity extends AppCompatActivity {
    private List<Profile> profiles = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProfileListRecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ProfileDBHelper profileDbHelper;
    private WifiReceiver wifiReceiver = new WifiReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileDbHelper = new ProfileDBHelper(getApplicationContext());
        profiles = profileDbHelper.getAllProfiles();

        setContentView(R.layout.activity_profile_list);
        recyclerView =  findViewById(R.id.rv_profile);
        recyclerView.setHasFixedSize(true);
        setRecyclerViewLayoutManager();
        setRecyclerAdapter();
        setAddProfileButton();

   registerReceiver( wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) );

       //MOCk

//        Intent startServiceIntent= new Intent(this, WifiService.class);
//        startServiceIntent.putExtra("test","test wifi service");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            getApplicationContext().startForegroundService(startServiceIntent);
//        } else getApplicationContext().startService(startServiceIntent);
    }

    public void setRecyclerViewLayoutManager() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setRecyclerAdapter() {
        adapter = new ProfileListRecyclerAdapter(profiles);
        recyclerView.setAdapter(adapter);
    }

    public void setAddProfileButton() {
        Button addProfileButton = (Button) findViewById(R.id.aggiungi_profilo);
        addProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ProfileListActivity.this, NewProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}

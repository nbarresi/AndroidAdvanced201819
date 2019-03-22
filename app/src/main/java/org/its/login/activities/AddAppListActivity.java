package org.its.login.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidadvanced201819.R;

import org.its.db.entities.Profile;
import org.its.login.adapters.AppListRecyclerAdapter;
import org.its.login.adapters.ProfileListRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddAppListActivity extends AppCompatActivity {
    private Profile tempProfile;
    private List<String> apps = new ArrayList<>();
    private RecyclerView recyclerView;
    private AppListRecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_app_list);
        Intent intentAppList = getIntent();
        tempProfile = (Profile) intentAppList.getSerializableExtra("tempProfile");

        //MOCK
        apps.add("Ciao");apps.add("voglio");apps.add("fare");apps.add("un");apps.add("gioco");apps.add("con");apps.add("te");

        recyclerView = (RecyclerView) findViewById(R.id.rv_app);
        recyclerView.setHasFixedSize(true);
        setRecyclerViewLayoutManager();
        setRecyclerAdapter();
    }


    public void setRecyclerViewLayoutManager() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setRecyclerAdapter() {
        adapter = new AppListRecyclerAdapter(apps);
        recyclerView.setAdapter(adapter);
        adapter.setProfile(tempProfile);
    }

}

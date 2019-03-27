package org.its.login.activities;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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
    List<ApplicationInfo> applicationInfoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AppListRecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;
    PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_app_list);
        pm = getPackageManager();
        applicationInfoList = pm.getInstalledApplications(0);

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
        adapter = new AppListRecyclerAdapter(applicationInfoList, this);
        recyclerView.setAdapter(adapter);
        adapter.setPackageManager(pm);

    }



}

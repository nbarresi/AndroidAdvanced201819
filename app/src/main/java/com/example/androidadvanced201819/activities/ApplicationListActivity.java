package com.example.androidadvanced201819.activities;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.adapter.AppAdapter;

import java.util.List;

public class ApplicationListActivity extends AppCompatActivity {

    private PackageManager packageManager;
    private AppAdapter appAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_list_layout);

        final ListView appListView = (ListView) findViewById(R.id.appListView);

        packageManager = getPackageManager();
        final List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(0);

        appAdapter = new AppAdapter(this,applicationInfoList,packageManager);

        appListView.setAdapter(appAdapter);

        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplicationInfo appInfo = applicationInfoList.get(position);

                Intent backToList = new Intent();
                backToList.putExtra("appName",appInfo.loadLabel(packageManager));
                backToList.putExtra("appPackage",appInfo.packageName);
                setResult(1,backToList);
                finish();
            }
        });
    }
}

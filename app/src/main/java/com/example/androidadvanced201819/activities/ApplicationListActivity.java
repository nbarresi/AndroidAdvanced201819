package com.example.androidadvanced201819.activities;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.adapter.AppAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationListActivity extends AppCompatActivity {

    public static final String EXTRA_APP_NAME = "appName";
    public static final String EXTRA_APP_PACKAGE = "appPackage";

    private PackageManager packageManager;
    private AppAdapter appAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_list_layout);

        final ListView appListView = (ListView) findViewById(R.id.appListView);

        packageManager = getPackageManager();
        final List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(0);
        final List<ApplicationInfo> applicationInfoListToSet = new ArrayList<>();

        for(ApplicationInfo info:applicationInfoList){
            if(getApplicationContext()
                    .getPackageManager()
                    .getLaunchIntentForPackage(info.packageName)!=null){
                    applicationInfoListToSet.add(info);
            }
        }

        appAdapter = new AppAdapter(this,applicationInfoListToSet,packageManager);

        appListView.setAdapter(appAdapter);

        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplicationInfo appInfo = applicationInfoListToSet.get(position);

                Intent backToList = new Intent();
                backToList.putExtra(EXTRA_APP_NAME, appInfo.loadLabel(packageManager));
                backToList.putExtra(EXTRA_APP_PACKAGE, appInfo.packageName);
                setResult(1,backToList);
                finish();
            }
        });
    }
}

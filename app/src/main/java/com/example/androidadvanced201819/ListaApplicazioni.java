package com.example.androidadvanced201819;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

public class ListaApplicazioni extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_applicazioni);

        ListView listView=findViewById(R.id.listView_app);

        PackageManager pm= getPackageManager();
        List<ApplicationInfo> applicationInfoList= pm.getInstalledApplications(0);

        ArrayAdapter<String> adapter=null;
        for (ApplicationInfo applicationInfo: applicationInfoList)
        {
            String appName = applicationInfo.loadLabel(getPackageManager()).toString();
            adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Collections.singletonList(appName));
        }
        listView.setAdapter(adapter);

    }
}

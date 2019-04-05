package com.example.androidadvanced201819;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaApplicazioni extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_applicazioni);

        ListView listView=findViewById(R.id.listView_app);
        PackageManager packageManager = getPackageManager();

        List appListMainArrayList = new ArrayList<>();
        List<ApplicationInfo> resolveInfoList = packageManager.getInstalledApplications(0);

        ArrayAdapter<String> adapter=null;
        for (ApplicationInfo resolveInfo : resolveInfoList) {
            String appName =resolveInfo.loadLabel(packageManager).toString();
            appListMainArrayList.add(resolveInfo);
            adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Collections.singletonList(appName));
        }
        listView.setAdapter(adapter);

    }
}

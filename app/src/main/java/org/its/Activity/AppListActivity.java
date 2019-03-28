package org.its.Activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidadvanced201819.R;


import org.its.utilities.ResultsCode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppListActivity extends AppCompatActivity {

    private List<String> list = new ArrayList<>();



    private PackageManager pm = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_app_layout);

        pm = this.getPackageManager();
        List<ApplicationInfo> applist = pm.getInstalledApplications(0);

        Iterator<ApplicationInfo> it=applist.iterator();
        while(it.hasNext()){
            ApplicationInfo pk=(ApplicationInfo)it.next();
            String appname = pm.getApplicationLabel(pk).toString();
            list.add(appname);
        }

        ArrayAdapter<String> madapter = new ArrayAdapter<String>(this, R.layout.item_layout, R.id.profileName, list);

        final ListView listView = (ListView) findViewById(R.id.appList);
        listView.setAdapter(madapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String app = list.get(position);
                Intent result = new Intent(AppListActivity.this, DetailActivity.class);
                result.putExtra(ResultsCode.LIST_RESULT, app);
                setResult(DetailActivity.RESULT_OK, result);
                finish();
            }
        });
    }


}

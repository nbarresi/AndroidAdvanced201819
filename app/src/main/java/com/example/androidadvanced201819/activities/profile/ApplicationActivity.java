package com.example.androidadvanced201819.activities.profile;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.activities.adapter.ApplicationAdapterActivity;

public class ApplicationActivity extends AppCompatActivity {

    ApplicationAdapterActivity adapterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapterActivity = new ApplicationAdapterActivity(getApplicationContext());
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapterActivity);

        TextView title = findViewById(R.id.main_title);
        title.setText("Applicazioni");

        Button button = findViewById(R.id.accessButton);
        button.setVisibility(View.GONE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent resultIntent = new Intent(ApplicationActivity.this, ProfileManagement.class);
                ApplicationInfo applicationInfo = adapterActivity.getApplicationByPosition(position);
                PackageManager pm = getApplicationContext().getPackageManager();
                resultIntent.putExtra("package", applicationInfo.packageName);
                resultIntent.putExtra("name", applicationInfo.loadLabel(pm));
                setResult(1, resultIntent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

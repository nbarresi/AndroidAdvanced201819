package com.example.androidadvanced201819.activities.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidadvanced201819.activities.adapter.MainAdapterActivity;
import com.example.androidadvanced201819.dataaccess.DataAccessUtils;
import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.database.ProfileDatabaseManager;

public class MainActivity extends AppCompatActivity {

    MainAdapterActivity adapterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        DataAccessUtils.initDataSource(this);
        adapterActivity = new MainAdapterActivity(getApplicationContext());
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapterActivity);
        Button button = findViewById(R.id.accessButton);

        TextView title = findViewById(R.id.main_title);
        title.setText("Profili");

        button.setVisibility(View.VISIBLE);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, final int pos, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                // set title
                alertDialogBuilder.setTitle("Delete profile?");

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ProfileDatabaseManager profileDatabaseManager;
                                // if this button is clicked, close
                                // current activity
                                profileDatabaseManager = new ProfileDatabaseManager(MainActivity.this);
                                profileDatabaseManager.open();
                                profileDatabaseManager.deleteProfile(DataAccessUtils.getItemByPosition(MainActivity.this, pos).getNome());
                                profileDatabaseManager.close();
                                DataAccessUtils.removeItem(DataAccessUtils.getItemByPosition(MainActivity.this, pos), MainActivity.this);
                                adapterActivity.setValues();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent toDetailActivity = new Intent(MainActivity.this, CreateProfile.class);
                toDetailActivity.putExtra("position", position);
                startActivity(toDetailActivity);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void goToCreateProfile(View view) {
        Intent goToCreateProfile = new Intent(MainActivity.this, CreateProfile.class);
        startActivity(goToCreateProfile);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterActivity.setValues();
    }
}

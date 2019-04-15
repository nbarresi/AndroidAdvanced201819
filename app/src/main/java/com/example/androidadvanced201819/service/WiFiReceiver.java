package com.example.androidadvanced201819.service;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.androidadvanced201819.database.ProfileDatabaseManager;

public class WiFiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, WiFiService.class);
        Log.d("11", "Receiver entered");
        ProfileDatabaseManager profileDatabaseManager = new ProfileDatabaseManager(context);
        profileDatabaseManager.open();
        Cursor cursorProfile = profileDatabaseManager.fetchAllProfile();
        if (cursorProfile.getCount() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(startServiceIntent);
            }
            context.startService(startServiceIntent);
        }
        profileDatabaseManager.close();
    }
}

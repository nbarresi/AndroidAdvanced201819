package com.example.androidadvanced201819.IntentServices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class GpsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Receiver Started", Toast.LENGTH_SHORT).show();
        Log.w("GESUUUXXX","DAI XXX");
        Intent startServiceIntent = new Intent (context, GpsService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(startServiceIntent);
            context.startService(startServiceIntent);
        }else{
            context.startService(startServiceIntent);
        }
    }
}
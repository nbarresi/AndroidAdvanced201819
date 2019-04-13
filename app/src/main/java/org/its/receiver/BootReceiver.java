package org.its.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import org.its.services.GpsTracker;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("avviatoServizio", "finalmente è partito");
        Intent mServiceIntent = new Intent(context, GpsTracker.class);
        // mServiceIntent.setData(Uri.parse(dataUrl));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(mServiceIntent);
        }
        context.startService(mServiceIntent);

    }
}

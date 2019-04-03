package org.its.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.its.services.GpsTracker;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       /* Intent mServiceIntent=new Intent(context, GpsTracker.class);
       // mServiceIntent.setData(Uri.parse(dataUrl));
        context.startService(mServiceIntent);*/
    }
}

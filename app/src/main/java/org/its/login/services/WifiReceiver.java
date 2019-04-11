package org.its.login.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

public class WifiReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
                Toast.makeText(context,
                        "CIAO SONO UN BROADCAST RECEIVER", Toast.LENGTH_SHORT).show();

                Intent startServiceIntent= new Intent(context, WifiService.class);
            startServiceIntent.putExtra("test","test wifi service");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(startServiceIntent);
        } else {
            context.startService(startServiceIntent);
        }
    }
}

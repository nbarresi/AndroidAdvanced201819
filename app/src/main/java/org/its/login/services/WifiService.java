package org.its.login.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.its.db.ProfileDBHelper;
import org.its.db.ProfileWifiPointsDBHelper;
import org.its.db.WiFiPointDBHelper;
import org.its.db.entities.Profile;
import org.its.db.entities.ProfileWiFiPoints;
import org.its.db.entities.WiFiPoint;

import java.util.ArrayList;
import java.util.List;

public class WifiService extends IntentService {

    private ProfileDBHelper profileDBHelper;
    private ProfileWifiPointsDBHelper profileWifiPointsDBHelper;
    private WiFiPointDBHelper wiFiPointDBHelper;
    private WifiManager wifiManager;
    private List<WiFiPoint> currentWifiList = new ArrayList<>();
    List<ProfileWiFiPoints> tuplaProfileWifiList = new ArrayList<>();

    private BroadcastReceiver wifiListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.w("onReceive", "onReceive");

            List<WiFiPoint> tempWifiList = new ArrayList<>();
            currentWifiList.clear();
            for (ScanResult scanResult : wifiManager.getScanResults()) {
                WiFiPoint wiFiPoint = new WiFiPoint(scanResult);
                tempWifiList.add(wiFiPoint);
                Log.w("tempWifiList",wiFiPoint.getBSSID() );
            }
            currentWifiList = tempWifiList;


           if (validateWifiPosition() != null){
               activateProfile(validateWifiPosition());
               onDestroy();
           } else getCurrentWifiList();
        }
    };



    public WifiService(String name) {
        super(name);
    }

    public WifiService() {
        super("WifiService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        String dataString = workIntent.getStringExtra("test");
        Log.w("onHandleIntent", "onHandleIntent");
        while (true){

        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.w("onStartCommand", "onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("onCreate", "onCreate");

        profileDBHelper = new ProfileDBHelper(getApplicationContext());
        profileWifiPointsDBHelper = new ProfileWifiPointsDBHelper(getApplicationContext());
        wiFiPointDBHelper = new WiFiPointDBHelper(getApplicationContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startMyOwnForeground();

            Log.w("startMyOwnForeground", "startMyOwnForeground");
        } else {
            startForeground(1, new Notification());


            Log.w("startForeground", "startForeground");
        }
        checkProfileWithWifi();
        getCurrentWifiList();

    }

    @Override
    public void onDestroy() {
        unregisterReceiver(wifiListener);
        super.onDestroy();
    }

    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    private void checkProfileWithWifi() {

        List<Profile> profileWithWifiList = new ArrayList<>();
        for (Profile profile : profileDBHelper.getAllProfiles()) {
            if (profile.getMetodoRilevamento().equals("Wifi"))
                profileWithWifiList.add(profile);
            Log.w("profileWithWifiList.add", profile.getName());

        }

        for (Profile profile : profileWithWifiList) {
            tuplaProfileWifiList.add(profileWifiPointsDBHelper.getProfileWiFiPoints(profile.getId()));
        }
    }


    private Profile validateWifiPosition() {

        for (ProfileWiFiPoints profileWiFiPoints : tuplaProfileWifiList) {

            List<WiFiPoint> savedWiFiPointList = new ArrayList<>();
            savedWiFiPointList = profileWiFiPoints.getWiFiPoints();

            List<WiFiPoint> confirmedWifiPointList = new ArrayList<>();
            for (WiFiPoint savedWifiPoint : savedWiFiPointList) {
                for (WiFiPoint currentWifiPoint : currentWifiList) {
                    if (savedWifiPoint.getBSSID() == currentWifiPoint.getBSSID()
                            && currentWifiPoint.getLevel() >= savedWifiPoint.getLevel() - 20
                            && currentWifiPoint.getLevel() <= savedWifiPoint.getLevel() + 20) {
                        confirmedWifiPointList.add(currentWifiPoint);
                    }
                }
            }
            if (confirmedWifiPointList.size() >= savedWiFiPointList.size() * 0.7) {
                return profileDBHelper.getProfileById(profileWiFiPoints.getIdProfile()).get(0);
            }
        }
        return null;
    }

    private void getCurrentWifiList() {
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        registerReceiver(wifiListener, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    private void activateProfile(Profile validateWifiPosition) {
    }

}

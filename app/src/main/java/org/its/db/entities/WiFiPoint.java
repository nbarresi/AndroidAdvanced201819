package org.its.db.entities;

import android.net.wifi.ScanResult;
import android.provider.BaseColumns;

import java.io.Serializable;

public class WiFiPoint implements Serializable {

    String BSSID;
    String SSID;
    int level; //dBm

    public WiFiPoint(String BSSID, String SSID, int level) {
        this.BSSID = BSSID;
        this.SSID = SSID;
        this.level = level;
    }

    public WiFiPoint(){
    }

    public WiFiPoint(ScanResult scanResult){
        this.BSSID = scanResult.BSSID;
        this.SSID = scanResult.SSID;
        this.level = scanResult.level;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /* Inner class that defines the table contents */
    public static class WifiPointEntry implements BaseColumns {
        public final static String TABLE_NAME = "wifipoints";

        public final static String _BSSID = "bssid";

        public final static String _SSID = "ssid";

        public final static String _LEVEL= "level";

    }
}

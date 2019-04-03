package com.example.androidadvanced201819.model;

import java.io.Serializable;

public class WiFi implements Serializable {
    private String BSSID;
    private String SSID;
    private int level;

    public WiFi(String BSSID, String SSID, int level) {
        this.BSSID = BSSID;
        this.SSID = SSID;
        this.level = level;
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
}

package com.example.androidadvanced201819.model;

import android.net.wifi.aware.WifiAwareManager;

import java.io.Serializable;
import java.util.List;

public class WiFiList implements Serializable {
    List<WiFi> wiFis;

    public WiFiList(List<WiFi> wiFis) {
        this.wiFis = wiFis;
    }

    public List<WiFi> getWiFis() {
        return wiFis;
    }

    public void setWiFis(List<WiFi> wiFis) {
        this.wiFis = wiFis;
    }
}

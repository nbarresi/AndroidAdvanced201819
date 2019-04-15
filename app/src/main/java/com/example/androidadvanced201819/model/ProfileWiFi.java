package com.example.androidadvanced201819.model;

public class ProfileWiFi {
    private int idProfile;
    private String BSSID;

    public ProfileWiFi(int idProfile, String BSSID) {
        this.idProfile = idProfile;
        this.BSSID = BSSID;
    }

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }
}

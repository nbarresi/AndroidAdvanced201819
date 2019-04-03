package com.example.androidadvanced201819.DB.Entities;

import java.io.Serializable;

public class Wifi implements Serializable {

    private Integer id;
    private String ssid;
    private String bssid;
    private Integer level;
    private Integer idProfilo;

    public Wifi(Integer id,String ssid, String bssid, Integer level, Integer idProfilo) {
        this.id = id;
        this.ssid = ssid;
        this.bssid = bssid;
        this.level = level;
        this.idProfilo = idProfilo;
    }

    public Wifi(String ssid, String bssid, Integer level) {
        this.ssid = ssid;
        this.bssid = bssid;
        this.level = level;
    }

    public String getSsid() {
        return ssid;
    }

    public Integer getId() {
        return id;
    }

    public String getBssid() {
        return bssid;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getIdProfilo() {
        return idProfilo;
    }

    public void setIdProfilo(Integer idProfilo) {
        this.idProfilo = idProfilo;
    }

}

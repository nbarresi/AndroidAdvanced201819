package com.example.androidadvanced201819.DB.Entities;

import android.annotation.SuppressLint;
import android.net.wifi.ScanResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserProfile implements Serializable {
    private Integer id;
    private String metodoDiRilevamento;
    private String valoreMetodo;
    private int luminosita;
    private int volume;
    private boolean bluetooth;
    private boolean wifi;
    private String nome;
    private String appPackage;
    private String appName;
    private ArrayList<ScanResult> wifiValues = new ArrayList<>();

    public UserProfile(Integer id, String nome, String metodoDiRilevamento ,String valoreMetodo,int luminosita, int volume, boolean bluetooth, boolean wifi,String appPackage, String appName) {
        this.metodoDiRilevamento = metodoDiRilevamento;
        this.id = id;
        this.luminosita = luminosita;
        this.volume = volume;
        this.bluetooth = bluetooth;
        this.wifi = wifi;
        this.nome=nome;
        this.appPackage=appPackage;
        this.valoreMetodo=valoreMetodo;
        this.appName = appName;
    }



    public Integer getId() {
        return id;
    }

    public String getValoreMetodo() {
        return valoreMetodo;
    }

    public String getMetodoDiRilevamento() {
        return metodoDiRilevamento;
    }

    public int getLuminosita() {
        return luminosita;
    }

    public int getVolume() {
        return volume;
    }

    public boolean isBluetooth() {
        return bluetooth;
    }

    public boolean isWifi() {
        return wifi;
    }

    public String getNome() {
        return nome;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public String getAppName() {
        return appName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValoreMetodo(String valoreMetodo) {
        this.valoreMetodo = valoreMetodo;
    }
}

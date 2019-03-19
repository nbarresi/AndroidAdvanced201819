package com.example.androidadvanced201819;

public class UserProfile {
    private int id;
//    private MetodoRilevamento metodoDiRilevamento;
    private int luminosita;
    private int volume;
    private boolean bluetooth;
    private boolean wifi;

    public UserProfile(int id/*,MetodoRilevamento metodoDiRilevamento*/, int luminosita, int volume, boolean bluetooth, boolean wifi) {
        this.id = id;
//        this.metodoDiRilevamento = metodoDiRilevamento;
        this.luminosita = luminosita;
        this.volume = volume;
        this.bluetooth = bluetooth;
        this.wifi = wifi;
    }

    public int getId() {
        return id;
    }

//    public MetodoRilevamento getMetodoDiRilevamento() {
//        return metodoDiRilevamento;
//    }

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
}

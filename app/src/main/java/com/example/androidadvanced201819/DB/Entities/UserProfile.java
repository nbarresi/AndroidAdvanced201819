package com.example.androidadvanced201819.DB.Entities;

public class UserProfile {
    private int id;
//    private MetodoRilevamento metodoDiRilevamento;
    private int luminosita;
    private int volume;
    private boolean bluetooth;
    private boolean wifi;
    private String nome;

    public UserProfile(/*MetodoRilevamento metodoDiRilevamento ,*/String nome, int luminosita, int volume, boolean bluetooth, boolean wifi) {
//        this.metodoDiRilevamento = metodoDiRilevamento;
        this.luminosita = luminosita;
        this.volume = volume;
        this.bluetooth = bluetooth;
        this.wifi = wifi;
        this.nome=nome;
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

    public String getNome() {
        return nome;
    }
}

package com.example.androidadvanced201819.model;

public class Profile {
    private int id;
    private String nome;
    private int option;
    private int brightness;
    private int volume;
    private int bluethoot;
    private int wifi;
    private String application;
    private String application_name;
    private int auto_birghtness;
    private String coordinate;
    private String nfc;

    public Profile() {
        
    }

    public Profile(int id, String nome, int option, int brightness, int volume, int bluethoot, int wifi, String application, String application_name, int auto_birghtness, String coordinate, String nfc) {
        this.id = id;
        this.nome = nome;
        this.option = option;
        this.brightness = brightness;
        this.volume = volume;
        this.bluethoot = bluethoot;
        this.wifi = wifi;
        this.application = application;
        this.application_name = application_name;
        this.auto_birghtness = auto_birghtness;
        this.coordinate = coordinate;
        this.nfc = nfc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getBluethoot() {
        return bluethoot;
    }

    public void setBluethoot(int bluethoot) {
        this.bluethoot = bluethoot;
    }

    public int getWifi() {
        return wifi;
    }

    public void setWifi(int wifi) {
        this.wifi = wifi;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public int getAuto_birghtness() {
        return auto_birghtness;
    }

    public void setAuto_birghtness(int auto_birghtness) {
        this.auto_birghtness = auto_birghtness;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplicationName() {
        return application_name;
    }

    public void setApplicationName(String application_name) {
        this.application_name = application_name;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public String getApplication_name() {
        return application_name;
    }

    public void setApplication_name(String application_name) {
        this.application_name = application_name;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getNfc() {
        return nfc;
    }

    public void setNfc(String nfc) {
        this.nfc = nfc;
    }
}

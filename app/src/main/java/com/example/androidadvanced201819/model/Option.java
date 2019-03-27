package com.example.androidadvanced201819.model;

public enum Option {

    GPS("gps", 1),
    WIFI("wifi", 2),
    NFC("nfc", 3),
    BEACON("beacon", 4);

    private String name;
    private int value;

    private Option(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

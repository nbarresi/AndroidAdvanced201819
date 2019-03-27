package com.example.androidadvanced201819.model;

import android.graphics.Path;

public enum Option {
    DEFAULT(0),
    GPS(1),
    WIFI(2),
    NFC(3),
    BEACON(4);

    private final int option;

    private Option(int i) {
        this.option = i;
    }

    public int getOption() {
        return option;
    }

    public static Option getEnumfromValue(int option) {
        Option optionSelectd = Option.DEFAULT;
        switch (option) {
            case 1:
                optionSelectd = Option.GPS;
                break;
            case 2:
                optionSelectd = Option.WIFI;
                break;
            case 3:
                optionSelectd = Option.NFC;
                break;
            case 4:
                optionSelectd = Option.BEACON;
                break;
        }
        return optionSelectd;
    }
}

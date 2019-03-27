package org.its.db.entities;

import android.provider.BaseColumns;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Profile implements Serializable {

    private long id;

    private String name;

    private String metodoRilevamento;

    private int luminosita;

    private int volume;

    private boolean bluetooth;

    private String app;

    public Profile() {
    }

    public Profile(long id, String name, String metodoRilevamento, int luminosita, int volume, boolean bluetooth, String app) {
        this.id = id;
        this.name = name;
        this.metodoRilevamento = metodoRilevamento;
        this.luminosita = luminosita;
        this.volume = volume;
        this.bluetooth = bluetooth;
        this.app = app;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetodoRilevamento() {
        return metodoRilevamento;
    }

    public void setMetodoRilevamento(String metodoRilevamento) {
        this.metodoRilevamento = metodoRilevamento;
    }

    public int getLuminosita() {
        return luminosita;
    }

    public void setLuminosita(int luminosita) {
        this.luminosita = luminosita;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    /* Inner class that defines the table contents */
    public static class ProfileEntry implements BaseColumns {
        public final static String TABLE_NAME = "profile";

        public final static String _ID = "id";

        public final static String _NAME = "name";

        public final static String _METODO_RILEVAMENTO = "metodo_rilevamento";

        public final static String _LUMINOSITA = "luminosita";

        public final static String _VOLUME = "volume";

        public final static String _BLUETOOTH = "bluetooth";

        public final static String _APP = "app";
    }

}

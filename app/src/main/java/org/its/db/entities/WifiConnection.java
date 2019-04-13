package org.its.db.entities;


import java.io.Serializable;
import java.util.Objects;

public class WifiConnection implements Serializable {

    private int power;
    private String name;
    private String BSSID;

    public WifiConnection() {
    }

    public WifiConnection(int power, String name, String BSSID) {
        this.power = power;
        this.name = name;
        this.BSSID = BSSID;
    }

    public WifiConnection( String name, String BSSID) {
        this.name = name;
        this.BSSID = BSSID;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WifiConnection that = (WifiConnection) o;
        return power == that.power &&
                Objects.equals(name, that.name) &&
                Objects.equals(BSSID, that.BSSID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(power, name, BSSID);
    }
}

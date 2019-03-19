package org.its.db.entities;

import org.its.utilities.ProfileTypeEnum;

import java.util.Objects;

public class Profilo {
    private Integer id;
    private Integer volume;
    private Integer luminosita;
    private boolean autoLuminosita;
    private boolean wifi;
    private boolean bluetooth;
    private ProfileTypeEnum metodo;

    public Profilo() {
    }
    public Profilo(Integer id, Integer volume, Integer luminosita, boolean autoLuminosita, boolean wifi, boolean bluetooth, ProfileTypeEnum metodo) {
        this.id = id;
        this.volume = volume;
        this.luminosita = luminosita;
        this.autoLuminosita = autoLuminosita;
        this.wifi = wifi;
        this.bluetooth = bluetooth;
        this.metodo = metodo;
    }

    public Profilo(Integer volume, Integer luminosita, boolean autoLuminosita, boolean wifi, boolean bluetooth, ProfileTypeEnum metodo) {
        this.volume = volume;
        this.luminosita = luminosita;
        this.autoLuminosita = autoLuminosita;
        this.wifi = wifi;
        this.bluetooth = bluetooth;
        this.metodo = metodo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getLuminosita() {
        return luminosita;
    }

    public void setLuminosita(Integer luminosita) {
        this.luminosita = luminosita;
    }

    public boolean isAutoLuminosita() {
        return autoLuminosita;
    }

    public void setAutoLuminosita(boolean autoLuminosita) {
        this.autoLuminosita = autoLuminosita;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        this.bluetooth = bluetooth;
    }

    public ProfileTypeEnum getMetodo() {
        return metodo;
    }

    public void setMetodo(ProfileTypeEnum metodo) {
        this.metodo = metodo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profilo profilo = (Profilo) o;
        return autoLuminosita == profilo.autoLuminosita &&
                wifi == profilo.wifi &&
                bluetooth == profilo.bluetooth &&
                Objects.equals(id, profilo.id) &&
                Objects.equals(volume, profilo.volume) &&
                Objects.equals(luminosita, profilo.luminosita) &&
                metodo == profilo.metodo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, volume, luminosita, autoLuminosita, wifi, bluetooth, metodo);
    }

    @Override
    public String toString() {
        return "Profilo{" +
                "id=" + id +
                ", volume=" + volume +
                ", luminosita=" + luminosita +
                ", autoLuminosita=" + autoLuminosita +
                ", wifi=" + wifi +
                ", bluetooth=" + bluetooth +
                ", metodo=" + metodo +
                '}';
    }
}

package org.its.db.entities;

import org.its.utilities.ProfileTypeEnum;

import java.io.Serializable;
import java.util.Objects;

public class Profilo implements Serializable {
    private Integer id;
    private String nome;
    private Integer volume;
    private Integer luminosita;
    private boolean autoLuminosita;
    private boolean wifi;
    private boolean bluetooth;
    private ProfileTypeEnum metodo;
    private String rilevazione;

    public Profilo() {
    }

    public Profilo(Integer id, String nome, Integer volume, Integer luminosita, boolean autoLuminosita, boolean wifi, boolean bluetooth, ProfileTypeEnum metodo, String rilevazione) {
        this.id = id;
        this.nome = nome;
        this.volume = volume;
        this.luminosita = luminosita;
        this.autoLuminosita = autoLuminosita;
        this.wifi = wifi;
        this.bluetooth = bluetooth;
        this.metodo = metodo;
        this.rilevazione = rilevazione;
    }

    public Profilo(Integer volume, String nome,Integer luminosita, boolean autoLuminosita, boolean wifi, boolean bluetooth, ProfileTypeEnum metodo, String rilevazione) {
        this.volume = volume;
        this.nome = nome;
        this.luminosita = luminosita;
        this.autoLuminosita = autoLuminosita;
        this.wifi = wifi;
        this.bluetooth = bluetooth;
        this.metodo = metodo;
        this.rilevazione = rilevazione;
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

    public String getRilevazione() {
        return rilevazione;
    }

    public void setRilevazione(String rilevazione) {
        this.rilevazione = rilevazione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profilo)) return false;
        Profilo profilo = (Profilo) o;
        return autoLuminosita == profilo.autoLuminosita &&
                wifi == profilo.wifi &&
                bluetooth == profilo.bluetooth &&
                Objects.equals(id, profilo.id) &&
                Objects.equals(nome, profilo.nome) &&
                Objects.equals(volume, profilo.volume) &&
                Objects.equals(luminosita, profilo.luminosita) &&
                metodo == profilo.metodo &&
                Objects.equals(rilevazione, profilo.rilevazione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, metodo);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

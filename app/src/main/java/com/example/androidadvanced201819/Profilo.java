package com.example.androidadvanced201819;

import java.util.List;

public class Profilo {
    private int id;
    private String name;
    private int latitudine;
    private int longitudine;
    private int raggio;

    public Profilo(int id, String name, int latitudine, int longitudine, int raggio) {
        this.id = id;
        this.name = name;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.raggio = raggio;
    }

    public Profilo() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(int latitudine) {
        this.latitudine = latitudine;
    }

    public int getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(int longitudine) {
        this.longitudine = longitudine;
    }

    public int getRaggio() {
        return raggio;
    }

    public void setRaggio(int raggio) {
        this.raggio = raggio;
    }

    @Override
    public String toString() {
        return " " + name;
    }
}

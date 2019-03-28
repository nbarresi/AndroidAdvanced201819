package org.its.db.entities;



public class Gps {
    private Double latitudine;
    private Double longitudine;
    private Integer raggio;

    public Gps() {
        raggio = 500;
    }

    public Gps(Double latitudine, Double longitudine) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.raggio = 500;
    }

    public Double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(Double latitudine) {
        this.latitudine = latitudine;
    }

    public Double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(Double longitudine) {
        this.longitudine = longitudine;
    }

    public Integer getRaggio() {
        return raggio;
    }

    public void setRaggio(Integer raggio) {
        this.raggio = raggio;
    }
}

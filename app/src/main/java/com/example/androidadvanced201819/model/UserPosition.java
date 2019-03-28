package com.example.androidadvanced201819.model;

public class UserPosition {

    private double Lat;
    private double Long;
    private int rangeRadius;

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }

    public int getRangeRadius() {
        return rangeRadius;
    }

    public void setRangeRadius(int rangeRadius) {
        this.rangeRadius = rangeRadius;
    }
}

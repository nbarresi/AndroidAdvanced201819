package org.its.db.entities;

import android.provider.BaseColumns;

import org.altbeacon.beacon.Beacon;

import java.io.Serializable;

public class BeaconDBO implements Serializable {
    private String beaconName;
    private String beaconAddress;
    private double beaconDistance;

    public BeaconDBO(String beaconName, String beaconAddress, Double beaconDistance) {
        this.beaconName = beaconName;
        this.beaconAddress = beaconAddress;
        this.beaconDistance = beaconDistance;
    }

    public BeaconDBO(){}

    public BeaconDBO(Beacon beacon){
        this.beaconName = beacon.getBluetoothName();
        this.beaconAddress = beacon.getBluetoothAddress();
        this.beaconDistance = beacon.getDistance();
    }


    public String getBeaconName() {
        return beaconName;
    }

    public void setBeaconName(String beaconName) {
        this.beaconName = beaconName;
    }

    public String getBeaconAddress() {
        return beaconAddress;
    }

    public void setBeaconAddress(String beaconAddress) {
        this.beaconAddress = beaconAddress;
    }

    public double getBeaconDistance() {
        return beaconDistance;
    }

    public void setBeaconDistance(double beaconDistance) {
        this.beaconDistance = beaconDistance;
    }

    /* Inner class that defines the table contents */
    public static class BeaconPointEntry implements BaseColumns {
        public final static String TABLE_NAME = "beaconpoints";

        public final static String _BEACONNAME = "beaconName";

        public final static String _BEACONADDRESS = "beaconAddress";

        public final static String _BEACONDISTANCE= "beaconDistance";

    }
}

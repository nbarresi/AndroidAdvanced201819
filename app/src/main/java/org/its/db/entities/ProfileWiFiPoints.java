package org.its.db.entities;

import android.provider.BaseColumns;

import java.util.List;

public class ProfileWiFiPoints {

    private long idProfile;
    private List<WiFiPoint> wiFiPoints;

    public ProfileWiFiPoints(){}

    public ProfileWiFiPoints(Long idProfile, List<WiFiPoint> wiFiPoints){
        this.idProfile = idProfile;
        this.wiFiPoints = wiFiPoints;
    }

    public long getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(long idProfile) {
        this.idProfile = idProfile;
    }

    public List<WiFiPoint> getWiFiPoints() {
        return wiFiPoints;
    }

    public void setWiFiPoints(List<WiFiPoint> wiFiPoints) {
        this.wiFiPoints = wiFiPoints;
    }

    /* Inner class that defines the table contents */
    public static class ProfileWiFiPointsEntry implements BaseColumns {
        public final static String TABLE_NAME = "profilewifipoints";

        public final static String _ID = "id";

        public final static String _BSSID = "bssid";

    }
}

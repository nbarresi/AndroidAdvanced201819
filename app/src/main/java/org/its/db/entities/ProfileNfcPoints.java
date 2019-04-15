package org.its.db.entities;

import android.provider.BaseColumns;

import java.util.List;

public class ProfileNfcPoints {

    private long idProfile;
    private String idNfc;

    public ProfileNfcPoints(){}

    public ProfileNfcPoints(Long idProfile, String idNfc){
        this.idProfile = idProfile;
        this.idNfc = idNfc;
    }

    public long getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(long idProfile) {
        this.idProfile = idProfile;
    }

    public String getIdNfc() {
        return idNfc;
    }

    public void setIdNfc(String idNfc) {
        this.idNfc = idNfc;
    }

    /* Inner class that defines the table contents */
    public static class ProfileNfcPointsEntry implements BaseColumns {
        public final static String TABLE_NAME = "profilenfcpoints";

        public final static String _ID = "id";

        public final static String _NFCID = "idNfc";

    }
}

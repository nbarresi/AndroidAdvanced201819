package org.its.db.entities;

import android.provider.BaseColumns;

import java.io.Serializable;

public class NfcPoint implements Serializable {


    String nfcId;

    public NfcPoint( String nfcId) {
        this.nfcId = nfcId;
    }

    public NfcPoint(){
    }

    public String getNfcId() {
        return nfcId;
    }

    public void setNfcId(String nfcId) {
        this.nfcId = nfcId;
    }


    /* Inner class that defines the table contents */
    public static class NfcPointEntry implements BaseColumns {
        public final static String TABLE_NAME = "nfcpoints";
        public final static String _NFCID =  "nfcId";
    }
}

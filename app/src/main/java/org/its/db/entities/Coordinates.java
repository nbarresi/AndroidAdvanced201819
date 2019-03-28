package org.its.db.entities;

import android.provider.BaseColumns;
import java.io.Serializable;


    public class Coordinates implements Serializable {

        private long id;

        private double latitude;

        private double longitude;

        private long radius;

        private long idProfile;


        public Coordinates() {
        }

        public Coordinates(long id, double latitude, double longitude, long radius, long idProfile) {
            this.id = id;
            this.latitude = latitude;
            this.longitude = longitude;
            this.radius = radius;
            this.idProfile = idProfile;
        }



        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public long getRadius() {
            return radius;
        }

        public void setRadius(long radius) {
            this.radius = radius;
        }

        public long getIdProfile() {
            return idProfile;
        }

        public void setIdProfile(long idProfile) {
            this.idProfile = idProfile;
        }

        /* Inner class that defines the table contents */
        public static class CoordinatesEntry implements BaseColumns {
            public final static String TABLE_NAME = "coordinates";

            public final static String _ID = "id";

            public final static String _LATITUDE = "latitude";

            public final static String _LONGITUDE = "longitude";

            public final static String _RADIUS = "radius";

            public final static String _IDPROFILE = "idProfile";
        }
    }

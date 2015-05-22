package com.mag.boikov.testapp.communications;

public class GpsData {
    public static GpsData EMPTY = new GpsData();

    Double latitude;
    Double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}

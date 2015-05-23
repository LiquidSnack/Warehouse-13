package com.mag.boikov.testapp.statistics;

import android.app.Service;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GpsLocationListener implements LocationListener {
    static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    static final long MIN_TIME_BW_UPDATES = 1000 * 60;

    LocationManager locationManager;
    Location location;
    boolean gpsEnabled;
    boolean networkEnabled;

    static GpsLocationListener gpsLocationListener;

    public static synchronized GpsLocationListener register(Context context) {
        if (gpsLocationListener == null) {
            return registerNew(context);
        }
        return gpsLocationListener;
    }

    static GpsLocationListener registerNew(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);
        GpsLocationListener listener = new GpsLocationListener();
        listener.locationManager = locationManager;
        listener.gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        listener.networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        listener.requestLocationUpdates();
        return listener;
    }

    void requestLocationUpdates() {
        if (networkEnabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        } else if (gpsEnabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public GpsData gpsData() {
        if (location == null) {
            return lastKnown();
        }
        return asGpsData(location);
    }

    GpsData lastKnown() {
        Location location = null;
        if (networkEnabled) {
            location = getLastKnowLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (gpsEnabled) {
            location = getLastKnowLocation(LocationManager.GPS_PROVIDER);
        }
        return asGpsData(location);
    }

    GpsData asGpsData(Location location) {
        if (location == null) {
            return GpsData.EMPTY;
        }
        return nonEmptyGpsDataFrom(location);
    }

    GpsData nonEmptyGpsDataFrom(Location location) {
        GpsData gpsData = new GpsData();
        gpsData.setLatitude(location.getLatitude());
        gpsData.setLongitude(location.getLongitude());
        return gpsData;
    }

    Location getLastKnowLocation(String gpsProvider) {
        return locationManager.getLastKnownLocation(gpsProvider);
    }
}

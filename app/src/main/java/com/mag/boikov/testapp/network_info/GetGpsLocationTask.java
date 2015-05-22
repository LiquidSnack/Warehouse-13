package com.mag.boikov.testapp.network_info;

import android.app.Service;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.mag.boikov.testapp.communications.GpsData;

import java.util.concurrent.ExecutionException;

public class GetGpsLocationTask extends AsyncTask<Void, Void, GpsData> implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60;

    private final Context context;
    LocationManager locationManager;
    Location location;
    boolean gpsEnabled;
    boolean networkEnabled;

    @Override
    protected GpsData doInBackground(Void... params) {
        return gpsData();
    }

    public GetGpsLocationTask(Context context) {
        this.context = context;
        registerGpsListener();
    }

    void registerGpsListener() {
        locationManager = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (networkEnabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        } else if (gpsEnabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        }
    }

    GpsData gpsData() {
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

    Location getLastKnowLocation(String gpsProvider) {
        return locationManager.getLastKnownLocation(gpsProvider);
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

    public GpsData getGpsData() {
        try {
            return get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("TestFragment", e.toString());
            return GpsData.EMPTY;
        }
    }
}

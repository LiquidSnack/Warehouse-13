package com.mag.boikov.testapp.network_info;

/**
 * Created by Boikov on 2015.03.23..
 */
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MyLocationListener implements LocationListener{

    public static double latitude;
    public static double longitude;

    public void onLocationChanged(Location loc)
    {
        latitude=loc.getLatitude();
        longitude=loc.getLongitude();

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



}

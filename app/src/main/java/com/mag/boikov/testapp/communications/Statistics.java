package com.mag.boikov.testapp.communications;

import com.mag.boikov.testapp.network_info.GpsData;
import com.mag.boikov.testapp.network_info.NetworkData;

import java.util.Date;

public class Statistics {
    GsmData gsmData;

    Date testPerformedAt;

    NetworkData networkData;

    GpsData gpsData;

    public GsmData getGsmData() {
        return gsmData;
    }

    public void setGsmData(GsmData gsmData) {
        this.gsmData = gsmData;
    }

    public Date getTestPerformedAt() {
        return testPerformedAt;
    }

    public void setTestPerformedAt(Date testPerformedAt) {
        this.testPerformedAt = testPerformedAt;
    }

    public NetworkData getNetworkData() {
        return networkData;
    }

    public void setNetworkData(NetworkData networkData) {
        this.networkData = networkData;
    }

    public GpsData getGpsData() {
        return gpsData;
    }

    public void setGpsData(GpsData gpsData) {
        this.gpsData = gpsData;
    }
}

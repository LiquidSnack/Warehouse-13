package com.mag.boikov.testapp.communications;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mag.boikov.testapp.network_info.PhoneCellInfo;

import java.util.Date;
import java.util.Map;

public class Statistics {
    GsmData gsmData;

    Date testPerformedAt;

    NetworkData networkData;

    GpsData gpsData;

    @JsonProperty("cellInfo")
    Map<String, PhoneCellInfo> cellInfoByType;

    UserData userData;

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

    public Map<String, PhoneCellInfo> getCellInfoByType() {
        return cellInfoByType;
    }

    public void setCellInfoByType(Map<String, PhoneCellInfo> cellInfoByType) {
        this.cellInfoByType = cellInfoByType;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}

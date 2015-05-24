package com.mag.boikov.testapp.statistics.communication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mag.boikov.testapp.statistics.GpsData;
import com.mag.boikov.testapp.statistics.GsmData;
import com.mag.boikov.testapp.statistics.NetworkStats;
import com.mag.boikov.testapp.statistics.PhoneCellInfo;

import java.util.Date;
import java.util.Map;

class Statistics {
    GsmData gsmData;

    Date testPerformedAt;

    NetworkStats networkStats;

    GpsData gpsData;

    @JsonProperty("cellInfo")
    Map<String, PhoneCellInfo> cellInfoByType;

    UserComments userComments;

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

    public NetworkStats getNetworkStats() {
        return networkStats;
    }

    public void setNetworkStats(NetworkStats networkStats) {
        this.networkStats = networkStats;
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

    public UserComments getUserComments() {
        return userComments;
    }

    public void setUserComments(UserComments userComments) {
        this.userComments = userComments;
    }
}

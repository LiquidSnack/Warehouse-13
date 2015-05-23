package com.mag.boikov.testapp.statistics;

public class NetworkStats {
    public static final NetworkStats EMPTY = new NetworkStats();

    Double uploadSpeed;
    Double downloadSpeed;
    Integer packetLoss;
    Double pingTime;

    public Double getUploadSpeed() {
        return uploadSpeed;
    }

    public void setUploadSpeed(Double uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }

    public Double getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(Double downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public Integer getPacketLoss() {
        return packetLoss;
    }

    public void setPacketLoss(Integer packetLoss) {
        this.packetLoss = packetLoss;
    }

    public Double getPingTime() {
        return pingTime;
    }

    public void setPingTime(Double pingTime) {
        this.pingTime = pingTime;
    }
}

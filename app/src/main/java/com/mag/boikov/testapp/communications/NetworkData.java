package com.mag.boikov.testapp.communications;

public class NetworkData {
    Double uploadSpeed;
    Double downloadSpeed;
    Integer packetLoss;

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
}

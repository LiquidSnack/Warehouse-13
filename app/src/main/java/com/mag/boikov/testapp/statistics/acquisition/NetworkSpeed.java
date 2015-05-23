package com.mag.boikov.testapp.statistics.acquisition;

class NetworkSpeed {
    public static final NetworkSpeed EMPTY = new NetworkSpeed();

    double upload;

    double download;

    static NetworkSpeed withValues(double upload, double download) {
        NetworkSpeed speed = new NetworkSpeed();
        speed.setUpload(upload);
        speed.setDownload(download);
        return speed;
    }

    public double getUpload() {
        return upload;
    }

    public void setUpload(double upload) {
        this.upload = upload;
    }

    public double getDownload() {
        return download;
    }

    public void setDownload(double download) {
        this.download = download;
    }
}

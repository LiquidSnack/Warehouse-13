package com.mag.boikov.testapp.statistics.acquisition;

import android.net.TrafficStats;

class SpeedWatch {
    static class Stats {
        long txBytes;
        long rxBytes;
        long time;
    }

    Stats before;

    static SpeedWatch start() {
        SpeedWatch speedWatch = new SpeedWatch();
        speedWatch.before = stats();
        return speedWatch;
    }

    NetworkSpeed stop() {
        return calculateSpeed();
    }

    private NetworkSpeed calculateSpeed() {
        Stats current = stats();
        double txDiff = current.txBytes - before.txBytes;
        double rxDiff = current.rxBytes - before.rxBytes;
        double timeDifference = current.time - before.time;
        double downloadKbps = 8 * rxDiff / timeDifference;
        double uploadKbps = 8 * txDiff / timeDifference;
        return NetworkSpeed.withValues(uploadKbps, downloadKbps);
    }

    static Stats stats() {
        Stats stats = new Stats();
        stats.rxBytes = TrafficStats.getTotalRxBytes();
        stats.txBytes = TrafficStats.getTotalTxBytes();
        stats.time = System.currentTimeMillis();
        return stats;
    }
}

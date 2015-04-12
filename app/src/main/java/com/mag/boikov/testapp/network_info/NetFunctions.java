package com.mag.boikov.testapp.network_info;

import android.content.Context;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.util.Log;

import com.mag.boikov.testapp.communications.NetworkData;

import java.net.InetAddress;

public class NetFunctions extends AsyncTask<Void, Void, NetworkData> {

    static final String serverUrl = "http://52.11.170.103:4848";

    final Context mContext;

    public NetFunctions(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected NetworkData doInBackground(Void... params) {
        NetworkData networkData = runSpeedTest();
        networkData.setPacketLoss(calculatePacketLoss());
        return networkData;
    }

    Integer calculatePacketLoss() {
        int packetLoss = getPacketLoss();
        return packetLoss;
    }

    public int getPacketLoss() {
        byte[] ip = {52, 11, (byte) 170, 103};
        int timeOut = 3000;
        int packetLoss = 0;
        double timeSum = 0;
        for (int i = 0; i < 5; i++) {
            try {
                long beforeTime = System.currentTimeMillis();
                InetAddress.getByAddress(null, ip)
                           .isReachable(timeOut);
                long afterTime = System.currentTimeMillis();
                long timeDifference = afterTime - beforeTime;
                if (timeDifference > timeOut) {
                    packetLoss += 20;
                }
                timeSum = timeSum + timeDifference;
            } catch (Exception e) {
                Log.e("NetFunctions", e.toString());
            }
        }
        return packetLoss;
    }

    NetworkData runSpeedTest() {
        long BeforeTime = System.currentTimeMillis();   //Замер до сетевой активности
        long TotalRxBeforeTest = TrafficStats.getTotalTxBytes();
        long TotalTxBeforeTest = TrafficStats.getTotalRxBytes();
        getPacketLoss();
        long TotalRxAfterTest = TrafficStats.getTotalTxBytes(); //Замер после
        long TotalTxAfterTest = TrafficStats.getTotalRxBytes();
        long AfterTime = System.currentTimeMillis();

        double timeDifference = AfterTime - BeforeTime;

        double rxDiff = TotalRxAfterTest - TotalRxBeforeTest;
        double txDiff = TotalTxAfterTest - TotalTxBeforeTest;
        if ((rxDiff != 0) && (txDiff != 0)) {
            double rxBPS = (rxDiff / (timeDifference / 1000)); // total rx bytes per second.
            double txBPS = (txDiff / (timeDifference / 1000)); // total tx bytes per second.
            return asNetworkData(rxBPS, txBPS);
        }
        return new NetworkData();
    }

    private NetworkData asNetworkData(double rxBPS, double txBPS) {
        NetworkData networkData = new NetworkData();
        networkData.setUploadSpeed(rxBPS);
        networkData.setDownloadSpeed(txBPS);
        return networkData;
    }
}

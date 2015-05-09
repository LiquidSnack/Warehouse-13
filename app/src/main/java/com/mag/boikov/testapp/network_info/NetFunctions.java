package com.mag.boikov.testapp.network_info;

import android.content.Context;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.util.Log;

import com.mag.boikov.testapp.communications.NetworkData;

import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.util.Arrays;

public class NetFunctions extends AsyncTask<Void, Void, NetworkData> {

    static final String serverUrl = "http://52.11.170.103:4848";

    static int TIME_OUT_MS = 3000;

    final Context mContext;

    RestTemplate template = new RestTemplate();

    public NetFunctions(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected NetworkData doInBackground(Void... params) {
        NetworkData networkData = speedData();
        networkData.setPacketLoss(calculatePacketLoss());
        return networkData;
    }

    NetworkData speedData() {
        try {
            return runSpeedTest();
        } catch (Exception e) {
            Log.e("NetFunctions", e.toString());
            return new NetworkData();
        }
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
        byte[] testData = randomBytes();
        long totalRxBeforeTest = TrafficStats.getTotalRxBytes();
        long totalTxBeforeTest = TrafficStats.getTotalTxBytes();
        long BeforeTime = System.currentTimeMillis();   //Замер до сетевой активности
        sendBytes(testData);
        double timeDifference = System.currentTimeMillis() - BeforeTime;

        double txDiff = TrafficStats.getTotalTxBytes() - totalTxBeforeTest;
        double rxDiff = TrafficStats.getTotalRxBytes() - totalRxBeforeTest;
        if ((rxDiff != 0) && (txDiff != 0)) {
            double downloadKbps = rxDiff / timeDifference; // total rx bytes per second.
            double uploadKbps = txDiff / timeDifference; // total tx bytes per second.
            return asNetworkData(downloadKbps, uploadKbps);
        }
        return new NetworkData();
    }

    byte[] randomBytes() {
        byte[] bytes = new byte[1000];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (Math.random() * 256);
        }
        return bytes;
    }

    void sendBytes(byte[] bytes) {
        byte[] response = template.postForObject(serverUrl + "/echo", bytes, byte[].class);
        if (!Arrays.equals(response, bytes)) {
            throw new RuntimeException("Response didn't match the request");
        }
    }

    NetworkData asNetworkData(double rxBPS, double txBPS) {
        NetworkData networkData = new NetworkData();
        networkData.setUploadSpeed(rxBPS);
        networkData.setDownloadSpeed(txBPS);
        return networkData;
    }
}

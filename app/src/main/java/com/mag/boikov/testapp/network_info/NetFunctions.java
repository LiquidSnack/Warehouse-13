package com.mag.boikov.testapp.network_info;

import android.content.Context;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.util.Log;

import com.googlecode.jpingy.Ping;
import com.googlecode.jpingy.PingArguments;
import com.googlecode.jpingy.PingResult;
import com.mag.boikov.testapp.communications.NetworkData;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class NetFunctions extends AsyncTask<Void, Void, NetworkData> {

    static final String serverUrl = "http://52.11.170.103:4848";

    static int TIME_OUT_MS = 3000;

    final Context mContext;

    RestTemplate template = buildTemplate();

    public NetFunctions(Context mContext) {
        this.mContext = mContext;
    }

    RestTemplate buildTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(TIME_OUT_MS);
        factory.setReadTimeout(TIME_OUT_MS);
        return new RestTemplate(factory);
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
        PingResult pingResult = pingResult();
        double loss = (1 - (double) pingResult.received() / pingResult.transmitted()) * 100;
        return (int) Math.round(loss);
    }

    PingResult pingResult() {
        PingArguments args = new PingArguments.Builder().url("52.11.170.103")
                                                        .count(5)
                                                        .bytes(32)
                                                        .build();
        return Ping.ping(args, Ping.Backend.UNIX);
    }

    NetworkData runSpeedTest() {
        byte[] testData = randomBytes();
        long totalRxBefore = TrafficStats.getTotalRxBytes();
        long totalTxBefore = TrafficStats.getTotalTxBytes();
        long beforeTime = System.currentTimeMillis();
        byte[] response = sendAndReceive(testData);
        double timeDifference = System.currentTimeMillis() - beforeTime;
        double txDiff = TrafficStats.getTotalTxBytes() - totalTxBefore;
        double rxDiff = TrafficStats.getTotalRxBytes() - totalRxBefore;
        checkAreEqual(response, testData);
        if ((rxDiff != 0) && (txDiff != 0)) {
            double downloadKbps = 8 * rxDiff / timeDifference;
            double uploadKbps = 8 * txDiff / timeDifference;
            return asNetworkData(downloadKbps, uploadKbps);
        }
        return new NetworkData();
    }

    void checkAreEqual(byte[] one, byte[] another) {
        if (!Arrays.equals(one, another)) {
            throw new RuntimeException("Response didn't match the request");
        }
    }

    byte[] randomBytes() {
        byte[] bytes = new byte[1024];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (Math.random() * 256);
        }
        return bytes;
    }

    byte[] sendAndReceive(byte[] bytes) {
        return template.postForObject(serverUrl + "/echo", bytes, byte[].class);
    }

    NetworkData asNetworkData(double rxBPS, double txBPS) {
        NetworkData networkData = new NetworkData();
        networkData.setUploadSpeed(rxBPS);
        networkData.setDownloadSpeed(txBPS);
        return networkData;
    }
}

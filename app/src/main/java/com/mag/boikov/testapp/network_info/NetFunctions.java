package com.mag.boikov.testapp.network_info;

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
    static final int TIME_OUT_MS = 3000;
    static final int ONE_BYTE = 1;
    static final int ONE_KB = 1024 * ONE_BYTE;
    static final int ONE_MB = 1024 * ONE_KB;

    final String serverIp;
    final String echoEndpointUrl;
    final RestTemplate rest;

    class Stats {
        long txBytes;
        long rxBytes;
        long time;
    }

    NetFunctions() {
        serverIp = System.getProperty("serverIp");
        echoEndpointUrl = System.getProperty("endpointUrl") + "/echo";
        rest = buildRest();
    }

    RestTemplate buildRest() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(TIME_OUT_MS);
        factory.setReadTimeout(TIME_OUT_MS);
        return new RestTemplate(factory);
    }

    public static NetworkData getNetworkData() {
        try {
            return new NetFunctions().execute()
                                     .get();
        } catch (Exception e) {
            Log.e("NetFunctions", e.toString());
            return NetworkData.EMPTY;
        }
    }

    @Override
    protected NetworkData doInBackground(Void... params) {
        PingResult pingResult = pingResult();
        if (pingResult == null) return NetworkData.EMPTY;
        return networkDataWithPingResult(pingResult);
    }

    NetworkData networkDataWithPingResult(PingResult pingResult) {
        NetworkData networkData = speedData();
        networkData.setPacketLoss(calculatePacketLoss(pingResult));
        networkData.setPingTime((double) pingResult.rtt_avg());
        return networkData;
    }

    NetworkData speedData() {
        try {
            return runSpeedTest();
        } catch (Exception e) {
            Log.e("NetFunctions", e.toString());
            return NetworkData.EMPTY;
        }
    }

    Integer calculatePacketLoss(PingResult pingResult) {
        double loss = (1 - (double) pingResult.received() / pingResult.transmitted()) * 100;
        return (int) Math.round(loss);
    }

    PingResult pingResult() {
        PingArguments args = new PingArguments.Builder().url(serverIp)
                                                        .count(5)
                                                        .bytes(32)
                                                        .build();
        return Ping.ping(args, Ping.Backend.UNIX);
    }

    NetworkData runSpeedTest() {
        byte[] testData = randomBytes();
        Stats before = stats();
        byte[] response = sendAndReceive(testData);
        Stats after = stats();
        checkAreEqual(response, testData);
        return networkDataFromDiff(before, after);
    }

    NetworkData networkDataFromDiff(Stats before, Stats after) {
        double txDiff = after.txBytes - before.txBytes;
        double rxDiff = after.rxBytes - before.rxBytes;
        if (rxDiff == 0 || txDiff == 0) return NetworkData.EMPTY;
        double timeDifference = after.time - before.time;
        double downloadKbps = 8 * rxDiff / timeDifference;
        double uploadKbps = 8 * txDiff / timeDifference;
        return asNetworkData(downloadKbps, uploadKbps);
    }

    Stats stats() {
        Stats stats = new Stats();
        stats.rxBytes = TrafficStats.getTotalRxBytes();
        stats.txBytes = TrafficStats.getTotalTxBytes();
        stats.time = System.currentTimeMillis();
        return stats;
    }

    void checkAreEqual(byte[] one, byte[] another) {
        if (!Arrays.equals(one, another)) {
            throw new RuntimeException("Response didn't match the request");
        }
    }

    byte[] randomBytes() {
        byte[] bytes = new byte[ONE_MB];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (Math.random() * 256);
        }
        return bytes;
    }

    byte[] sendAndReceive(byte[] bytes) {
        return rest.postForObject(echoEndpointUrl, bytes, byte[].class);
    }

    NetworkData asNetworkData(double rxBPS, double txBPS) {
        NetworkData networkData = new NetworkData();
        networkData.setUploadSpeed(rxBPS);
        networkData.setDownloadSpeed(txBPS);
        return networkData;
    }
}

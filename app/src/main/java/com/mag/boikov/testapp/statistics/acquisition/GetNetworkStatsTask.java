package com.mag.boikov.testapp.statistics.acquisition;

import android.os.AsyncTask;
import android.util.Log;

import com.googlecode.jpingy.Ping;
import com.googlecode.jpingy.PingArguments;
import com.googlecode.jpingy.PingResult;
import com.mag.boikov.testapp.statistics.NetworkStats;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

public class GetNetworkStatsTask extends AsyncTask<Void, Void, NetworkStats> {
    static final int TIME_OUT_MS = 5000;
    static final int ONE_BYTE = 1;
    static final int ONE_KB = 1024 * ONE_BYTE;
    static final int TEST_DATA_SIZE = 32 * ONE_KB;
    static final int TIMES = 64;

    final String serverIp;
    final String testDataEndpointUrl;
    final RestTemplate rest;

    public GetNetworkStatsTask() {
        serverIp = System.getProperty("serverIp");
        testDataEndpointUrl = System.getProperty("endpointUrl") + "/test-data";
        rest = buildRest();
    }

    RestTemplate buildRest() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(TIME_OUT_MS);
        factory.setReadTimeout(TIME_OUT_MS);
        return new RestTemplate(factory);
    }

    @Override
    protected NetworkStats doInBackground(Void... params) {
        PingResult pingResult = pingResult();
        if (pingResult == null) return NetworkStats.EMPTY;
        return networkDataWithPingResult(pingResult);
    }

    NetworkStats networkDataWithPingResult(PingResult pingResult) {
        NetworkSpeed networkSpeed = networkSpeed();
        if (networkSpeed == NetworkSpeed.EMPTY) {
            return NetworkStats.EMPTY;
        }
        NetworkStats networkStats = new NetworkStats();
        networkStats.setUploadSpeed(networkSpeed.getUpload());
        networkStats.setDownloadSpeed(networkSpeed.getDownload());
        networkStats.setPacketLoss(calculatePacketLoss(pingResult));
        networkStats.setPingTime((double) pingResult.rtt_avg());
        return networkStats;
    }

    NetworkSpeed networkSpeed() {
        try {
            return runSpeedTestTimes(TIMES);
        } catch (Exception e) {
            Log.e("GetNetworkStatistics", e.toString());
            return NetworkSpeed.EMPTY;
        }
    }

    NetworkSpeed runSpeedTestTimes(int times) {
        double up = 0;
        double down = 0;
        for (int i = 0; i < times; i++) {
            NetworkSpeed networkSpeed = performSpeedTest();
            up += networkSpeed.getUpload();
            down += networkSpeed.getDownload();
        }
        return NetworkSpeed.withValues(up / times, down / times);
    }

    NetworkSpeed performSpeedTest() {
        return NetworkSpeed.withValues(calculateUploadSpeed(), calculateDownloadSpeed());
    }

    double calculateDownloadSpeed() {
        SpeedWatch speedWatch = SpeedWatch.start();
        ResponseEntity<byte[]> response = receive();
        NetworkSpeed speed = speedWatch.stop();
        checkResponse(response);
        return speed.getDownload();
    }

    void checkResponse(ResponseEntity<byte[]> response) {
        HttpStatus statusCode = response.getStatusCode();
        if (statusCode != HttpStatus.OK) {
            throw new IllegalStateException("Receiving test data failed - got response status: " + statusCode);
        }
        int receivedDataSize = response.getBody().length;
        if (receivedDataSize != TEST_DATA_SIZE) {
            throw new IllegalStateException("Receiving test data failed - received data size didn't match");
        }
    }

    ResponseEntity<byte[]> receive() {
        return rest.getForEntity(testDataEndpointUrl + "?bytes={bytes}", byte[].class, TEST_DATA_SIZE);
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

    double calculateUploadSpeed() {
        byte[] testData = TestData.randomBytes(TEST_DATA_SIZE);
        SpeedWatch speedWatch = SpeedWatch.start();
        HttpStatus responseStatus = send(testData);
        NetworkSpeed speed = speedWatch.stop();
        if (responseStatus != HttpStatus.OK) {
            throw new IllegalStateException("Sending test data failed - got response status: " + responseStatus);
        }
        return speed.getUpload();
    }

    HttpStatus send(byte[] bytes) {
        return rest.exchange(testDataEndpointUrl, HttpMethod.POST, new HttpEntity<>(bytes), String.class)
                   .getStatusCode();
    }

    public NetworkStats getNetworkData() {
        try {
            return get(45, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e("GetNetworkStatistics", e.toString());
            return NetworkStats.EMPTY;
        }
    }
}

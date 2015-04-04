package com.mag.boikov.testapp.network_info;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetFunctions extends AsyncTask<Void, Void, NetworkData> {

    static final String serverUrl = "http://52.11.170.103:4848";

    final Context mContext;

    public NetFunctions(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected NetworkData doInBackground(Void... params) {
        NetworkData networkData = runSpeedTest();
        networkData.setPing(calculatePing());
        return networkData;
    }

    Double calculatePing() {
        return null;
    }

    String getAddress() {
        InetAddress addr = null;
        String host = "194.105.56.170";
        String str = "";
        try {
            addr = InetAddress.getByName(serverUrl);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            if (addr.isReachable(5000)) {
                str = (host + " -Respond OK");
            } else {
                str = (host);
            }
        } catch (IOException e) {
            str = (e.toString());

        }
        //Тут желательно получить какие-нибудь данные от сервера
        return str;
    }

    NetworkData runSpeedTest() {
        long BeforeTime = System.currentTimeMillis();   //Замер до сетевой активности
        long TotalRxBeforeTest = TrafficStats.getTotalTxBytes();
        long TotalTxBeforeTest = TrafficStats.getTotalRxBytes();
        getAddress();
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

package com.mag.boikov.testapp.network_info;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.AsyncTask;
import java.util.Map;

import com.mag.boikov.testapp.MainActivity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Boikov on 2015.03.26..
 */
public class NetFunctions extends AsyncTask<Void, Void, String> {

    static final String serverUrl = "http://52.11.170.103:4848";

    final Context mContext;

    public NetFunctions(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isConnected() { //Есть связь или нет
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /*public String ping() {
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
        return str;
    *///}

    long BeforeTime = System.currentTimeMillis();   //Замер до сетевой активности
    long TotalRxBeforeTest = TrafficStats.getTotalTxBytes();
    long TotalTxBeforeTest = TrafficStats.getTotalRxBytes();

    @Override
    protected String doInBackground(Void... params) {
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

    long TotalRxAfterTest = TrafficStats.getTotalTxBytes(); //Замер после
    long TotalTxAfterTest = TrafficStats.getTotalRxBytes();
    long AfterTime = System.currentTimeMillis();

    double TimeDifference = AfterTime - BeforeTime;

    double rxDiff = TotalRxAfterTest - TotalRxBeforeTest;
    double txDiff = TotalTxAfterTest - TotalTxBeforeTest;

    public Map<String,String> netSpeed() {  // Как правильно возвращать 2 величины сразу?
        Map<String,String> speed = netSpeed();
        String downSpeed ="";
        String upSpeed = "";
        if ((rxDiff != 0) && (txDiff != 0)) {
            double rxBPS = (rxDiff / (TimeDifference / 1000)); // total rx bytes per second.
            double txBPS = (txDiff / (TimeDifference / 1000)); // total tx bytes per second.
            downSpeed = String.valueOf(rxBPS) + "bps. Total rx = " + rxDiff;
            upSpeed = String.valueOf(txBPS) + "bps. Total tx = " + txDiff;
        } else {
            downSpeed = "No downloaded bytes.";
            upSpeed = "No uploaded bytes";
        }
        return speed;
    }



}

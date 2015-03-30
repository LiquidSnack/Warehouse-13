package com.mag.boikov.testapp.network_info;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetFunctions {

    final Context mContext;

    public NetFunctions(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isConnected() { //Есть связь или нет
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

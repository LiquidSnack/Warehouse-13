package com.mag.boikov.testapp.network_info;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mag.boikov.testapp.MainActivity;

/**
 * Created by Boikov on 2015.03.26..
 */
public class NetFunctions {

    Context mContext;
    public NetFunctions(Context mContext){
        this.mContext = mContext;
    }

    public boolean isConnected(){ //Есть связь или нет
        ConnectivityManager connMgr = (ConnectivityManager)mContext.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


}

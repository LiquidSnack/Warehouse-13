package com.mag.boikov.testapp.network_info;

import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

public class PhoneInfo {
    TelephonyManager telephonyManager;

    public static PhoneInfo fromContext(Context context) {
        PhoneInfo phoneInfo = new PhoneInfo();
        phoneInfo.telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return phoneInfo;
    }

    public String getOperatorName() {
        return telephonyManager.getNetworkOperatorName();
    }

    public String getPhoneType() {
        switch (telephonyManager.getPhoneType()) {
            case TelephonyManager.PHONE_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.PHONE_TYPE_GSM:
                return "GSM";
            case TelephonyManager.PHONE_TYPE_NONE:
                return "No radio";
            case TelephonyManager.PHONE_TYPE_SIP:
                return "SIP";
            default:
                return "Undetermined";
        }
    }

    public String getNetworkCountry() {
        return telephonyManager.getNetworkCountryIso();
    }

    public String getNetworkOperator() {
        return telephonyManager.getNetworkOperator();
    }

    public String getNetworkType() {
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            default:
                return "Undetermined";
        }
    }

    public String TimeDate() {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString;
    }

    public Map<String, String> getAllCellInfo() {
        List<CellInfo> infos = telephonyManager.getAllCellInfo();
        Map<String, String> cellInfoByCellType = new HashMap<>(infos.size());
        for (CellInfo info : infos) {
            PhoneCellInfo phoneCellInfo = CellInfoParser.parse(info);
            cellInfoByCellType.put(phoneCellInfo.name(), phoneCellInfo.toString());
        }
        return cellInfoByCellType;
    }

    public String ping() {
        InetAddress addr = null;
        String host = "194.105.56.170";
        String str = "";
        try {
            addr = InetAddress.getByName(host);
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

        /*String str = "https://www.ss.lv/";
        try {
            Process process = Runtime.getRuntime().exec(
                    "/system/bin/ping -c 8 " + str);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            int i;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((i = reader.read(buffer)) > 0)
                output.append(buffer, 0, i);
            reader.close();

            // body.append(output.toString()+"\n");
            str = output.toString();
            // Log.d(TAG, str);
        } catch (IOException e) {
            // body.append("Error\n");
            e.printStackTrace();
        }*/
        //return str;
        //}
    }
}

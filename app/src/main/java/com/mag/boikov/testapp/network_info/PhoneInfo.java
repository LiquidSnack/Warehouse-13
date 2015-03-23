package com.mag.boikov.testapp.network_info;

import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

public class PhoneInfo {
    TelephonyManager telephonyManager;

    int date;
    int month;
    int year;
    int hour;
    int minute;
    int second;

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
}

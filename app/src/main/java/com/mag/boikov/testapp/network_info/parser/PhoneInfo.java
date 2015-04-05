package com.mag.boikov.testapp.network_info.parser;

import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;

import com.mag.boikov.testapp.network_info.PhoneCellInfo;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneInfo {
    TelephonyManager telephonyManager;

    Date testPerformedAt;

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

    public Map<String, String> getAllCellInfo() {
        List<CellInfo> infos = telephonyManager.getAllCellInfo();
        if (infos == null) {
            return parseCellLocation();
        }
        return parseCellInfo(infos);
    }

    private Map<String, String> parseCellInfo(List<CellInfo> infos) {
        Map<String, String> cellInfoByCellType = new HashMap<>(infos.size());
        for (CellInfo info : infos) {
            PhoneCellInfo phoneCellInfo = CellInfoParser.parse(info);
            cellInfoByCellType.put(phoneCellInfo.name(), phoneCellInfo.toString());
        }
        return cellInfoByCellType;
    }

    private Map<String, String> parseCellLocation() {
        CellLocation cellLocation = telephonyManager.getCellLocation();
        if (cellLocation == null) {
            return Collections.emptyMap();
        }
        HashMap<String, String> cellLocationByType = new HashMap<>(1);
        PhoneCellInfo phoneCellInfo = CellLocationParser.parse(cellLocation);
        cellLocationByType.put(phoneCellInfo.name(), phoneCellInfo.toString());
        return cellLocationByType;
    }

    public TelephonyManager getTelephonyManager() {
        return telephonyManager;
    }

    public void setTelephonyManager(TelephonyManager telephonyManager) {
        this.telephonyManager = telephonyManager;
    }

    public Date getTestPerformedAt() {
        return testPerformedAt;
    }

    public void setTestPerformedAt(Date testPerformedAt) {
        this.testPerformedAt = testPerformedAt;
    }
}

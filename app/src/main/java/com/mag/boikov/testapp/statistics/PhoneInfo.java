package com.mag.boikov.testapp.statistics;

import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhoneInfo {
    static final int BASE_GSM_DBM = 113;
    static final int BASE_CDMA_DBM = 116;

    TelephonyManager telephonyManager;
    int gsmSignalStrengthAsu;
    int cdmaSignalStrengthAsu;

    class SignalStrengthStateListener extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            if (signalStrength.isGsm()) {
                PhoneInfo.this.gsmSignalStrengthAsu = signalStrength.getGsmSignalStrength();
            } else {
                PhoneInfo.this.cdmaSignalStrengthAsu = signalStrength.getCdmaDbm();
            }
        }
    }

    public static PhoneInfo fromContext(Context context) {
        PhoneInfo phoneInfo = new PhoneInfo();
        phoneInfo.telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        phoneInfo.telephonyManager.listen(phoneInfo.new SignalStrengthStateListener(), PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
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
        int networkType = telephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPAP";
            default:
                Log.e("PhoneInfo", "Unknown network type: " + networkType);
                return "Undetermined";
        }
    }

    public List<PhoneCellInfo> getAllCellInfo() {
        List<CellInfo> infos = telephonyManager.getAllCellInfo();
        if (infos == null) {
            return parseCellLocation();
        }
        return parseCellInfo(infos);
    }

    List<PhoneCellInfo> parseCellInfo(List<CellInfo> infos) {
        List<PhoneCellInfo> phoneCellInfos = new ArrayList<>(infos.size());
        for (CellInfo info : infos) {
            phoneCellInfos.add(CellInfoParser.parse(info));
        }
        return phoneCellInfos;
    }

    List<PhoneCellInfo> parseCellLocation() {
        CellLocation cellLocation = telephonyManager.getCellLocation();
        if (cellLocation == null) {
            return Collections.emptyList();
        }
        PhoneCellInfo phoneCellInfo = CellLocationParser.parse(cellLocation);
        setAsuAndDbm(phoneCellInfo);
        return Collections.singletonList(phoneCellInfo);
    }

    void setAsuAndDbm(PhoneCellInfo phoneCellInfo) {
        if (phoneCellInfo instanceof GsmInfo) {
            setAsuAndDbm((GsmInfo) phoneCellInfo);
        } else {
            setAsuAndDbm((CdmaInfo) phoneCellInfo);
        }
    }

    void setAsuAndDbm(GsmInfo gsmInfo) {
        gsmInfo.setDbm(toGsmDbm(gsmSignalStrengthAsu));
        gsmInfo.setAsuLevel(gsmSignalStrengthAsu);
    }

    void setAsuAndDbm(CdmaInfo cdmaInfo) {
        cdmaInfo.setAsuLevel(cdmaSignalStrengthAsu);
        cdmaInfo.setCdmaDbm(toCdmaDbm(cdmaSignalStrengthAsu));
    }

    int toGsmDbm(int asu) {
        return 2 * asu - BASE_GSM_DBM;
    }

    int toCdmaDbm(int asu) {
        return asu - BASE_CDMA_DBM;
    }
}

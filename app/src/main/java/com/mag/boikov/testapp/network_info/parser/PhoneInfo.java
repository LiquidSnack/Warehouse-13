package com.mag.boikov.testapp.network_info.parser;

import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import com.mag.boikov.testapp.network_info.CdmaInfo;
import com.mag.boikov.testapp.network_info.GsmInfo;
import com.mag.boikov.testapp.network_info.PhoneCellInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PhoneInfo {
    class SignalStrengthStateListener extends PhoneStateListener {
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            if (signalStrength.isGsm()) {
                PhoneInfo.this.gsmSignalStrength = signalStrength.getGsmSignalStrength();
            } else {
                PhoneInfo.this.cdmaSignalStrength = signalStrength.getCdmaDbm();
            }
        }
    }

    TelephonyManager telephonyManager;

    int gsmSignalStrength;

    int cdmaSignalStrength;

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
        final PhoneCellInfo phoneCellInfo = CellLocationParser.parse(cellLocation);
        if (phoneCellInfo instanceof GsmInfo) {
            ((GsmInfo) phoneCellInfo).setDbm(gsmSignalStrength);
        } else {
            ((CdmaInfo) phoneCellInfo).setDbm(cdmaSignalStrength);
        }
        return Arrays.asList(phoneCellInfo);
    }
}

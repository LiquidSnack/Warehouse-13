package com.mag.boikov.testapp.network_info;

import android.telephony.CellIdentityGsm;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;

public class GsmInfo implements PhoneCellInfo {
    int asuLevel;
    int dbm;
    int cid;
    int lac;
    int mcc;
    int mnc;

    public static GsmInfo fromCellInfo(CellInfoGsm cellInfo) {
        CellSignalStrengthGsm signalStrengthGsm = cellInfo.getCellSignalStrength();
        CellIdentityGsm cellIdentityGsm = cellInfo.getCellIdentity();
        GsmInfo gsmInfo = new GsmInfo();
        gsmInfo.asuLevel = signalStrengthGsm.getAsuLevel();
        gsmInfo.dbm = signalStrengthGsm.getDbm();
        gsmInfo.cid = cellIdentityGsm.getCid();
        gsmInfo.lac = cellIdentityGsm.getLac();
        gsmInfo.mcc = cellIdentityGsm.getMcc();
        gsmInfo.mnc = cellIdentityGsm.getMnc();
        return gsmInfo;
    }

    @Override
    public String name() {
        return "GSM";
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GsmInfo{");
        sb.append("asuLevel=")
          .append(asuLevel);
        sb.append(", dbm=")
          .append(dbm);
        sb.append(", cid=")
          .append(cid);
        sb.append(", lac=")
          .append(lac);
        sb.append(", mcc=")
          .append(mcc);
        sb.append(", mnc=")
          .append(mnc);
        sb.append('}');
        return sb.toString();
    }
}

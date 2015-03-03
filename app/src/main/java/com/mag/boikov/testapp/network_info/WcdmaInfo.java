package com.mag.boikov.testapp.network_info;

import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthWcdma;

public class WcdmaInfo implements PhoneCellInfo {
    int asuLevel;
    int dbm;
    int cid;
    int lac;
    int mcc;
    int mnc;
    int psc;

    public static WcdmaInfo fromCellInfo(CellInfoWcdma cellInfo) {
        CellSignalStrengthWcdma signalStrength = cellInfo.getCellSignalStrength();
        CellIdentityWcdma cellIdentityWcdma = cellInfo.getCellIdentity();
        WcdmaInfo wcdmaInfo = new WcdmaInfo();
        wcdmaInfo.asuLevel = signalStrength.getAsuLevel();
        wcdmaInfo.dbm = signalStrength.getDbm();
        wcdmaInfo.cid = cellIdentityWcdma.getCid();
        wcdmaInfo.lac = cellIdentityWcdma.getLac();
        wcdmaInfo.mcc = cellIdentityWcdma.getMcc();
        wcdmaInfo.mnc = cellIdentityWcdma.getMnc();
        wcdmaInfo.psc = cellIdentityWcdma.getPsc();
        return wcdmaInfo;
    }

    @Override
    public String name() {
        return "WCDMA";
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cell Info{");
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
        sb.append(", psc=")
          .append(psc);
        sb.append('}');
        return sb.toString();
    }
}

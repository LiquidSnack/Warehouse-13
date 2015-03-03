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
        return "Network type - WCDMA";
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cell Info{");
        sb.append("asuLevel=")
          .append(asuLevel);
        sb.append("," + '\n' +" dbm=")
          .append(dbm);
        sb.append("," + '\n' +" cid=")
          .append(cid);
        sb.append("," + '\n' +" lac=")
          .append(lac);
        sb.append("," + '\n' +" mcc=")
          .append(mcc);
        sb.append("," + '\n' +" mnc=")
          .append(mnc);
        sb.append("," + '\n' +" psc=")
          .append(psc);
        sb.append('}');
        return sb.toString();
    }
}

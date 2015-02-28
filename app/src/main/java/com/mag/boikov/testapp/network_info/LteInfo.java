package com.mag.boikov.testapp.network_info;

import android.telephony.CellIdentityLte;
import android.telephony.CellInfoLte;
import android.telephony.CellSignalStrengthLte;

public class LteInfo implements PhoneCellInfo {
    int asuLevel;
    int dbm;
    int ci;
    int mcc;
    int mnc;
    int pci;
    int tac;

    public static LteInfo fromCellInfo(CellInfoLte cellInfo) {
        CellSignalStrengthLte signalStrengthLte = cellInfo.getCellSignalStrength();
        CellIdentityLte cellIdentityLte = cellInfo.getCellIdentity();
        LteInfo lteInfo = new LteInfo();
        lteInfo.asuLevel = signalStrengthLte.getAsuLevel();
        lteInfo.dbm = signalStrengthLte.getDbm();
        lteInfo.ci = cellIdentityLte.getCi();
        lteInfo.mcc = cellIdentityLte.getMcc();
        lteInfo.mnc = cellIdentityLte.getMnc();
        lteInfo.pci = cellIdentityLte.getPci();
        lteInfo.tac = cellIdentityLte.getTac();
        return lteInfo;
    }

    @Override
    public String name() {
        return "LTE";
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LteInfo{");
        sb.append("asuLevel=")
          .append(asuLevel);
        sb.append(", dbm=")
          .append(dbm);
        sb.append(", ci=")
          .append(ci);
        sb.append(", mcc=")
          .append(mcc);
        sb.append(", mnc=")
          .append(mnc);
        sb.append(", pci=")
          .append(pci);
        sb.append(", tac=")
          .append(tac);
        sb.append('}');
        return sb.toString();
    }
}

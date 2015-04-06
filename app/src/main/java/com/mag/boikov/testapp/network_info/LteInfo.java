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
    public String getCellType() {
        return "Network type - LTE";
    }

    public int getAsuLevel() {
        return asuLevel;
    }

    public void setAsuLevel(int asuLevel) {
        this.asuLevel = asuLevel;
    }

    public int getDbm() {
        return dbm;
    }

    public void setDbm(int dbm) {
        this.dbm = dbm;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public int getMcc() {
        return mcc;
    }

    public void setMcc(int mcc) {
        this.mcc = mcc;
    }

    public int getMnc() {
        return mnc;
    }

    public void setMnc(int mnc) {
        this.mnc = mnc;
    }

    public int getPci() {
        return pci;
    }

    public void setPci(int pci) {
        this.pci = pci;
    }

    public int getTac() {
        return tac;
    }

    public void setTac(int tac) {
        this.tac = tac;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cell Info{");
        sb.append("asuLevel=")
          .append(asuLevel);
        sb.append("," + '\n' + " dbm=")
          .append(dbm);
        sb.append("," + '\n' + " ci=")
          .append(ci);
        sb.append("," + '\n' + " mcc=")
          .append(mcc);
        sb.append("," + '\n' + " mnc=")
          .append(mnc);
        sb.append("," + '\n' + " pci=")
          .append(pci);
        sb.append("," + '\n' + " tac=")
          .append(tac);
        sb.append('}');
        return sb.toString();
    }
}

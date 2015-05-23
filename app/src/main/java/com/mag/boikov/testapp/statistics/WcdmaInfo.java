package com.mag.boikov.testapp.statistics;

import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthWcdma;

class WcdmaInfo implements PhoneCellInfo {
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
    public String getCellType() {
        return "Network type - WCDMA";
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

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getLac() {
        return lac;
    }

    public void setLac(int lac) {
        this.lac = lac;
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

    public int getPsc() {
        return psc;
    }

    public void setPsc(int psc) {
        this.psc = psc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cell Info{");
        sb.append("asuLevel=")
          .append(asuLevel);
        sb.append("," + '\n' + " dbm=")
          .append(dbm);
        sb.append("," + '\n' + " cid=")
          .append(cid);
        sb.append("," + '\n' + " lac=")
          .append(lac);
        sb.append("," + '\n' + " mcc=")
          .append(mcc);
        sb.append("," + '\n' + " mnc=")
          .append(mnc);
        sb.append("," + '\n' + " psc=")
          .append(psc);
        sb.append('}');
        return sb.toString();
    }
}

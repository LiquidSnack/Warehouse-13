package com.mag.boikov.testapp.statistics;

import android.telephony.CellIdentityGsm;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.gsm.GsmCellLocation;

class GsmInfo implements PhoneCellInfo {
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

    public static PhoneCellInfo fromCellLocation(GsmCellLocation cellLocation) {
        GsmInfo gsmInfo = new GsmInfo();
        gsmInfo.cid = cellLocation.getCid();
        gsmInfo.lac = cellLocation.getLac();
        return gsmInfo;
    }

    @Override
    public String getCellType() {
        return "GSM";
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\n\tasuLevel: ")
          .append(asuLevel);
        sb.append("\n\tdbm: ")
          .append(dbm);
        sb.append("\n\tcid: ")
          .append(cid);
        sb.append("\n\tlac: ")
          .append(lac);
        return sb.toString();
    }
}

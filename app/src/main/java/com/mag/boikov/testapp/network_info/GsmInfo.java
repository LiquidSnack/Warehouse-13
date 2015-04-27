package com.mag.boikov.testapp.network_info;

import android.telephony.CellIdentityGsm;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.gsm.GsmCellLocation;

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
/*
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
*/
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cell Info{");
        sb.append("asuLevel=")
          .append(asuLevel);
        sb.append("," + '\n' + "dbm=")
          .append(dbm);
        sb.append("," + '\n' + " cid=")
          .append(cid);
        sb.append("," + '\n' + " lac=")
          .append(lac);
        //sb.append("," + '\n' + " mcc=")
        //  .append(mcc);
       // sb.append("," + '\n' + " mnc=")
        //  .append(mnc);
        sb.append('}');
        return sb.toString();
    }
}

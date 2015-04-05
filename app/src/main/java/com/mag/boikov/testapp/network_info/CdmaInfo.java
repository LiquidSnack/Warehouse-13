package com.mag.boikov.testapp.network_info;

import android.telephony.CellIdentityCdma;
import android.telephony.CellInfoCdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.cdma.CdmaCellLocation;

public class CdmaInfo implements PhoneCellInfo {
    int networkId;
    int baseId;
    int latitude;
    int longitude;
    int netId;
    int sysId;
    int asuLevel;
    int cdmaDbm;
    int cdmaEcio;
    int cdmaLevel;
    int dbm;
    int evdoDbm;
    int evdoEcio;
    int evdoLevel;
    int evdoSnr;
    int level;

    public static CdmaInfo fromCellInfo(CellInfoCdma cellInfo) {
        CellSignalStrengthCdma signalStrengthCdma = cellInfo.getCellSignalStrength();
        CellIdentityCdma cellIdentityCdma = cellInfo.getCellIdentity();
        CdmaInfo cdmaInfo = new CdmaInfo();
        cdmaInfo.networkId = cellIdentityCdma.getNetworkId();
        cdmaInfo.baseId = cellIdentityCdma.getBasestationId();
        cdmaInfo.latitude = cellIdentityCdma.getLatitude();
        cdmaInfo.longitude = cellIdentityCdma.getLongitude();
        cdmaInfo.netId = cellIdentityCdma.getNetworkId();
        cdmaInfo.sysId = cellIdentityCdma.getSystemId();
        cdmaInfo.asuLevel = signalStrengthCdma.getAsuLevel();
        cdmaInfo.cdmaDbm = signalStrengthCdma.getCdmaDbm();
        cdmaInfo.cdmaEcio = signalStrengthCdma.getCdmaEcio();
        cdmaInfo.cdmaLevel = signalStrengthCdma.getCdmaLevel();
        cdmaInfo.dbm = signalStrengthCdma.getDbm();
        cdmaInfo.evdoDbm = signalStrengthCdma.getEvdoDbm();
        cdmaInfo.evdoEcio = signalStrengthCdma.getEvdoEcio();
        cdmaInfo.evdoLevel = signalStrengthCdma.getEvdoLevel();
        cdmaInfo.evdoSnr = signalStrengthCdma.getEvdoSnr();
        cdmaInfo.level = signalStrengthCdma.getLevel();
        return cdmaInfo;
    }

    public static PhoneCellInfo fromCellLocation(CdmaCellLocation cellLocation) {
        CdmaInfo cdmaInfo = new CdmaInfo();
        cdmaInfo.baseId = cellLocation.getBaseStationId();
        cdmaInfo.latitude = cellLocation.getBaseStationLatitude();
        cdmaInfo.longitude = cellLocation.getBaseStationLongitude();
        cdmaInfo.networkId = cellLocation.getNetworkId();
        cdmaInfo.sysId = cellLocation.getSystemId();
        return cdmaInfo;
    }

    @Override
    public String getCellType() {
        return "Network type - CDMA";
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public int getBaseId() {
        return baseId;
    }

    public void setBaseId(int baseId) {
        this.baseId = baseId;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getNetId() {
        return netId;
    }

    public void setNetId(int netId) {
        this.netId = netId;
    }

    public int getSysId() {
        return sysId;
    }

    public void setSysId(int sysId) {
        this.sysId = sysId;
    }

    public int getAsuLevel() {
        return asuLevel;
    }

    public void setAsuLevel(int asuLevel) {
        this.asuLevel = asuLevel;
    }

    public int getCdmaDbm() {
        return cdmaDbm;
    }

    public void setCdmaDbm(int cdmaDbm) {
        this.cdmaDbm = cdmaDbm;
    }

    public int getCdmaEcio() {
        return cdmaEcio;
    }

    public void setCdmaEcio(int cdmaEcio) {
        this.cdmaEcio = cdmaEcio;
    }

    public int getCdmaLevel() {
        return cdmaLevel;
    }

    public void setCdmaLevel(int cdmaLevel) {
        this.cdmaLevel = cdmaLevel;
    }

    public int getDbm() {
        return dbm;
    }

    public void setDbm(int dbm) {
        this.dbm = dbm;
    }

    public int getEvdoDbm() {
        return evdoDbm;
    }

    public void setEvdoDbm(int evdoDbm) {
        this.evdoDbm = evdoDbm;
    }

    public int getEvdoEcio() {
        return evdoEcio;
    }

    public void setEvdoEcio(int evdoEcio) {
        this.evdoEcio = evdoEcio;
    }

    public int getEvdoLevel() {
        return evdoLevel;
    }

    public void setEvdoLevel(int evdoLevel) {
        this.evdoLevel = evdoLevel;
    }

    public int getEvdoSnr() {
        return evdoSnr;
    }

    public void setEvdoSnr(int evdoSnr) {
        this.evdoSnr = evdoSnr;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CdmaInfo{");
        sb.append("networkId=")
          .append(networkId);
        sb.append(", baseId=")
          .append(baseId);
        sb.append(", latitude=")
          .append(latitude);
        sb.append(", longitude=")
          .append(longitude);
        sb.append(", netId=")
          .append(netId);
        sb.append(", sysId=")
          .append(sysId);
        sb.append(", asuLevel=")
          .append(asuLevel);
        sb.append(", cdmaDbm=")
          .append(cdmaDbm);
        sb.append(", cdmaEcio=")
          .append(cdmaEcio);
        sb.append(", cdmaLevel=")
          .append(cdmaLevel);
        sb.append(", dbm=")
          .append(dbm);
        sb.append(", evdoDbm=")
          .append(evdoDbm);
        sb.append(", evdoEcio=")
          .append(evdoEcio);
        sb.append(", evdoLevel=")
          .append(evdoLevel);
        sb.append(", evdoSnr=")
          .append(evdoSnr);
        sb.append(", level=")
          .append(level);
        sb.append('}');
        return sb.toString();
    }
}

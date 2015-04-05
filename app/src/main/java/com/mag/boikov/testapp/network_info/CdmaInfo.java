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
    public String name() {
        return "Network type - CDMA";
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

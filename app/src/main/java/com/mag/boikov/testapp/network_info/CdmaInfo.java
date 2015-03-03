package com.mag.boikov.testapp.network_info;

import android.telephony.CellIdentityCdma;
import android.telephony.CellInfoCdma;
import android.telephony.CellSignalStrengthCdma;

public class CdmaInfo implements PhoneCellInfo {
    int baseED;
    int latitude;
    int longitude;
    int netId;
    int sysId;

    public static CdmaInfo fromCellInfo(CellInfoCdma cellInfo) {
        CellSignalStrengthCdma signalStrengthCdma = cellInfo.getCellSignalStrength();
        CellIdentityCdma cellIdentityCdma = cellInfo.getCellIdentity();
        CdmaInfo cdmaInfo = new CdmaInfo();
        cdmaInfo.baseED = cellIdentityCdma.getBasestationId();
        cdmaInfo.latitude = cellIdentityCdma.getLatitude();
        cdmaInfo.longitude = cellIdentityCdma.getLongitude();
        cdmaInfo.netId = cellIdentityCdma.getNetworkId();
        cdmaInfo.sysId = cellIdentityCdma.getSystemId();
        return cdmaInfo;
    }

    @Override
    public String name() {
        return "Network type - CDMA";
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cell Info{");
        sb.append("baseED=")
          .append(baseED);
        sb.append("," + '\n' +" latitude=")
          .append(latitude);
        sb.append("," + '\n' +" longitude=")
          .append(longitude);
        sb.append("," + '\n' +" netId=")
          .append(netId);
        sb.append("," + '\n' +" sysId=")
          .append(sysId);
        sb.append('}');
        return sb.toString();
    }
}

package com.mag.boikov.testapp.statistics;

import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;

class CellInfoParser {
    public static PhoneCellInfo parse(CellInfo cellInfo) {
        if (cellInfo instanceof CellInfoCdma) {
            return parseCdmaInfo((CellInfoCdma) cellInfo);
        } else if (cellInfo instanceof CellInfoGsm) {
            return parseGsmInfo((CellInfoGsm) cellInfo);
        } else if (cellInfo instanceof CellInfoLte) {
            return parseLteInfo((CellInfoLte) cellInfo);
        } else if (cellInfo instanceof CellInfoWcdma) {
            return parseWcdmaInfo((CellInfoWcdma) cellInfo);
        }
        throw new RuntimeException("Unknown CellInfo type: " + cellInfo.getClass()
                                                                       .getCanonicalName());
    }

    static WcdmaInfo parseWcdmaInfo(CellInfoWcdma cellInfo) {
        return WcdmaInfo.fromCellInfo(cellInfo);
    }

    static LteInfo parseLteInfo(CellInfoLte cellInfo) {
        return LteInfo.fromCellInfo(cellInfo);
    }

    static GsmInfo parseGsmInfo(CellInfoGsm cellInfo) {
        return GsmInfo.fromCellInfo(cellInfo);
    }

    static CdmaInfo parseCdmaInfo(CellInfoCdma cellInfo) {
        return CdmaInfo.fromCellInfo(cellInfo);
    }
}

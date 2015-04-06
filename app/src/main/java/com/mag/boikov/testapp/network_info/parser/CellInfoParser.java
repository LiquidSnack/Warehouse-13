package com.mag.boikov.testapp.network_info.parser;

import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;

import com.mag.boikov.testapp.network_info.CdmaInfo;
import com.mag.boikov.testapp.network_info.GsmInfo;
import com.mag.boikov.testapp.network_info.LteInfo;
import com.mag.boikov.testapp.network_info.PhoneCellInfo;
import com.mag.boikov.testapp.network_info.WcdmaInfo;

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

package com.mag.boikov.testapp.network_info.parser;


import android.telephony.CellLocation;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

import com.mag.boikov.testapp.network_info.CdmaInfo;
import com.mag.boikov.testapp.network_info.GsmInfo;
import com.mag.boikov.testapp.network_info.PhoneCellInfo;

class CellLocationParser {
    public static PhoneCellInfo parse(CellLocation cellLocation) {
        if (cellLocation instanceof GsmCellLocation) {
            return GsmInfo.fromCellLocation((GsmCellLocation) cellLocation);
        } else if (cellLocation instanceof CdmaCellLocation) {
            return CdmaInfo.fromCellLocation((CdmaCellLocation) cellLocation);
        }
        throw new RuntimeException("Unknown CellLocation type: " + cellLocation.getClass()
                                                                               .getCanonicalName());
    }
}

package com.mag.boikov.testapp.communications;

import java.util.Date;

public class Statistics {
    GsmData gsmData;

    Date testPerformedAt;

    public GsmData getGsmData() {
        return gsmData;
    }

    public void setGsmData(GsmData gsmData) {
        this.gsmData = gsmData;
    }

    public Date getTestPerformedAt() {
        return testPerformedAt;
    }

    public void setTestPerformedAt(Date testPerformedAt) {
        this.testPerformedAt = testPerformedAt;
    }
}

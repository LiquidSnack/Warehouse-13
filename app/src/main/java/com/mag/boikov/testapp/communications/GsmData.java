package com.mag.boikov.testapp.communications;

import java.util.Map;

public class GsmData {
    String operatorName;

    String networkOperator;

    String networkCountry;

    Map<String, String> cellInfo;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getNetworkOperator() {
        return networkOperator;
    }

    public void setNetworkOperator(String networkOperator) {
        this.networkOperator = networkOperator;
    }

    public String getNetworkCountry() {
        return networkCountry;
    }

    public void setNetworkCountry(String networkCountry) {
        this.networkCountry = networkCountry;
    }

    public Map<String, String> getCellInfo() {
        return cellInfo;
    }

    public void setCellInfo(Map<String,String> cellInfo) {
        this.cellInfo = cellInfo;
    }

}

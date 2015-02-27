package com.mag.boikov.testapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import java.io.File;
import android.telephony.CellIdentityGsm;
import android.telephony.CellInfo;
import android.net.*;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TextView outputbox;
    private TelephonyManager telephonyManager = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputbox.setText("Output");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void runTest() {
        try {
            String operatorName = telephonyManager.getNetworkOperatorName();

            int phoneType = telephonyManager.getPhoneType();
            String phoneTypeString;
            switch(phoneType) {
                case TelephonyManager.PHONE_TYPE_CDMA:
                    phoneTypeString = "CDMA";
                    break;
                case TelephonyManager.PHONE_TYPE_GSM:
                    phoneTypeString = "GSM";
                    break;
                case TelephonyManager.PHONE_TYPE_NONE:
                    phoneTypeString = "No radio";
                    break;
                case TelephonyManager.PHONE_TYPE_SIP:
                    phoneTypeString = "SIP";
                    break;}

            String NetCountryIso = telephonyManager.getNetworkCountryIso();

            String NetOper = telephonyManager.getNetworkOperator();

            int netType = telephonyManager.getNetworkType();
            String netTypeString = "Unknown";
            switch(netType) {
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    netTypeString = "UMTS";
                    break;
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    netTypeString = "CDMA";
                    break;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    netTypeString = "LTE";
                    break;
            }
            
            List<CellInfo> cellInfos = telephonyManager.getAllCellInfo();
            for(CellInfo cellInfo : cellInfos) {
                if(cellInfo instanceof CellInfoCdma) {
                    CellInfoCdma cdma = (CellInfoCdma) cellInfo;
                    CellSignalStrengthCdma signalStrengthCdma = cdma.getCellSignalStrength();
                    int asuLevel = signalStrengthCdma.getAsuLevel();
                    int dbm = signalStrengthCdma.getDbm();
                    CellIdentityCdma cellIdentityCdma = cdma.getCellIdentity();
                    int baseED = cellIdentityCdma.getBasestationId();
                    int latitude = cellIdentityCdma.getLatitude();
                    int longitude = cellIdentityCdma.getLongitude();
                    int netID = cellIdentityCdma.getNetworkId();
                    int sysID = cellIdentityCdma.getSystemId();

                } else if(cellInfo instanceof CellInfoGsm) {
                    CellInfoGsm gsm = (CellInfoGsm) cellInfo;
                    CellSignalStrengthGsm signalStrengthGsm = gsm.getCellSignalStrength();
                    int asuLevel = signalStrengthGsm.getAsuLevel();
                    int dbm = signalStrengthGsm.getDbm();
                    CellIdentityGsm cellIdentityGsm = gsm.getCellIdentity();
                    int cid = cellIdentityGsm.getCid();
                    int lac = cellIdentityGsm.getLac();
                    int mcc = cellIdentityGsm.getMcc();
                    int mnc = cellIdentityGsm.getMnc();

                } else if(cellInfo instanceof CellInfoLte) {
                    CellInfoLte lte = (CellInfoLte) cellInfo;
                    CellSignalStrengthLte signalStrengthLte = lte.getCellSignalStrength();
                    int asuLevel = signalStrengthLte.getAsuLevel();
                    int dbm = signalStrengthLte.getDbm();
                    CellIdentityLte cellIdentityLte = lte.getCellIdentity();
                    int ci = cellIdentityLte.getCi();
                    int mcc = cellIdentityLte.getMcc();
                    int mnc = cellIdentityLte.getMnc();
                    int pci = cellIdentityLte.getPci();
                    int tac = cellIdentityLte.getTac();

                } else if(cellInfo instanceof CellInfoWcdma) {
                    CellInfoWcdma wcdma = (CellInfoWcdma) cellInfo;
                    CellSignalStrengthWcdma signalStrengthWcdma = wcdma.getCellSignalStrength();
                    int asuLevel = signalStrengthWcdma.getAsuLevel();
                    int dbm = signalStrengthWcdma.getDbm();
                    CellIdentityWcdma cellIdentityWcdma = wcdma.getCellIdentity();
                    int cid = cellIdentityWcdma.getCid();
                    int lac = cellIdentityWcdma.getLac();
                    int mcc = cellIdentityWcdma.getMcc();
                    int mnc = cellIdentityWcdma.getMnc();
                    int psc = cellIdentityWcdma.getPsc();

                }
            }
            
            outputbox.setText("");
        } catch (Exception e) {}

    }
}

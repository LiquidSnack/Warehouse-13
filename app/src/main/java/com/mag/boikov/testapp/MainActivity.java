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
            CellInfo cellinfo = (CellInfo)TelephonyManager.getAllCellInfo().get();
            outputbox.setText("");
        } catch (Exception e) {}

    }
}

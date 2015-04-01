package com.mag.boikov.testapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;

import com.mag.boikov.testapp.communications.StatisticsSender;
import com.mag.boikov.testapp.network_info.MyLocationListener;
import com.mag.boikov.testapp.network_info.NetFunctions;
import com.mag.boikov.testapp.network_info.PhoneInfo;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import java.util.Map;


public class MainActivity extends ActionBarActivity {

    TextView outputBox;
    PhoneInfo phoneInfo;
    NetFunctions netFunctions;
    MyLocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputBox = (TextView) findViewById(R.id.outputBox);
        outputBox.setMovementMethod(new ScrollingMovementMethod());
        phoneInfo = PhoneInfo.fromContext(getApplicationContext());
        Button startTestButton = (Button) findViewById(R.id.StartTest);
        startTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performNetworkTest();
            }
        });
        Button sendButton = (Button) findViewById(R.id.Send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        NetFunctions netFunctions = new NetFunctions(this);

        AlertDialog alert = new AlertDialog.Builder(this).create(); //предупреждение
        alert.setTitle("Error");
        alert.setMessage("No network connection");
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

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

    void performNetworkTest() {
        outputBox.setText("");
        outputBox.append("Operator: " + phoneInfo.getOperatorName());
        outputBox.append('\n' + "Network Country: " + phoneInfo.getNetworkCountry());
        outputBox.append('\n' + "Network Operator: " + phoneInfo.getNetworkOperator());
        for (Map.Entry<String, String> cellInfo : phoneInfo.getAllCellInfo()
                                                           .entrySet()) {
            outputBox.append('\n' + cellInfo.getKey() + ": " + cellInfo.getValue());
        }
        outputBox.append('\n' + "Datums:" + phoneInfo.TimeDate());
        outputBox.append('\n' + "Ping:" + netFunctions.ping());
        outputBox.append('\n' + "GPS koordinates: Platums =" + locationListener.getLatitude());
        outputBox.append('\n' + "Garums=" + locationListener.getLongitude());
        //Если isConnected в NetFunctions даёт false, вывести предупреждение
    }

    void sendData() {
        try {
            outputBox.setText(new StatisticsSender().execute()
                    .get());
            } catch (Exception e) {
            // log exception
                    }

    }
}

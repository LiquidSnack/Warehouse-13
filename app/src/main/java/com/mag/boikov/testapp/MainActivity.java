package com.mag.boikov.testapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mag.boikov.testapp.communications.GsmData;
import com.mag.boikov.testapp.communications.NetworkData;
import com.mag.boikov.testapp.communications.Statistics;
import com.mag.boikov.testapp.communications.StatisticsSender;
import com.mag.boikov.testapp.network_info.GeoLocationListener;
import com.mag.boikov.testapp.network_info.NetFunctions;
import com.mag.boikov.testapp.network_info.PhoneCellInfo;
import com.mag.boikov.testapp.network_info.parser.PhoneInfo;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends ActionBarActivity {
    TextView outputBox;
    PhoneInfo phoneInfo;
    NetFunctions netFunctions;
    GeoLocationListener locationListener;

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
        sendButton.setEnabled(isNetworkConnectionAvailable());
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        netFunctions = new NetFunctions(this);
        locationListener = new GeoLocationListener(getApplicationContext());
    }

    boolean isNetworkConnectionAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    void performNetworkTest() {
        outputBox.setText("");
        outputBox.append("Phone Type: " + phoneInfo.getPhoneType());
        outputBox.append('\n' + "Operator: " + phoneInfo.getOperatorName());
        outputBox.append('\n' + "Network Country: " + phoneInfo.getNetworkCountry());
        outputBox.append('\n' + "Network Operator Code: " + phoneInfo.getNetworkOperator());
        outputBox.append('\n' + "Network Type: " + phoneInfo.getNetworkType());
        for (PhoneCellInfo cellInfo : phoneInfo.getAllCellInfo()) {
            outputBox.append('\n' + cellInfo.getCellType() + ": " + cellInfo);
        }
        outputBox.append('\n' + String.format("Datums: %Tc", new Date()));
        outputBox.append('\n' + "Location: " + locationListener.getLatitude() + ";" + locationListener.getLongitude());
        appendNetworkStats();
    }

    private void appendNetworkStats() {
        NetworkData networkData = getNetworkData();
        if (networkData == NetworkData.EMPTY) return;
        outputBox.append(String.format("\nDownload Speed: %.3f Kbps", networkData.getDownloadSpeed()));
        outputBox.append(String.format("\nUpload Speed: %.3f Kbps", networkData.getUploadSpeed()));
        outputBox.append(String.format("\nPacket Loss: %d", networkData.getPacketLoss()) + "%");
        outputBox.append(String.format("\nPing Time: %.1f", networkData.getPingTime()) + " ms");
    }

    NetworkData getNetworkData() {
        try {
            return new NetFunctions(getApplicationContext()).execute()
                                                            .get();
        } catch (Exception e) {
            Log.e("Main", e.toString());
            return NetworkData.EMPTY;
        }
    }

    void sendData() {
        Statistics statistics = buildStatistics();
        try {
            HttpStatus httpStatus = new StatisticsSender().execute(statistics)
                                                          .get(3, TimeUnit.SECONDS);
            if (httpStatus != HttpStatus.OK) {
                Log.e("MainActivity", "Got response status " + httpStatus);
            }
        } catch (Exception e) {
            Log.e("MainActivity", e.toString());
        }
    }

    Statistics buildStatistics() {
        Statistics statistics = new Statistics();
        statistics.setGsmData(gsmData());
        statistics.setTestPerformedAt(new Date());
        statistics.setCellInfoByType(cellInfoByType());
        statistics.setNetworkData(getNetworkData());
        statistics.setGpsData(locationListener.getLocation());
        return statistics;
    }

    Map<String, PhoneCellInfo> cellInfoByType() {
        Map<String, PhoneCellInfo> cellInfo = new HashMap<>();
        for (PhoneCellInfo info : phoneInfo.getAllCellInfo()) {
            cellInfo.put(info.getCellType(), info);
        }
        return cellInfo;
    }

    GsmData gsmData() {
        GsmData gsmData = new GsmData();
        gsmData.setPhoneType(phoneInfo.getPhoneType());
        gsmData.setNetworkCountry(phoneInfo.getNetworkCountry());
        gsmData.setNetworkOperator(phoneInfo.getNetworkOperator());
        gsmData.setOperatorName(phoneInfo.getOperatorName());
        gsmData.setNetworkType(phoneInfo.getNetworkType());
        return gsmData;
    }
}

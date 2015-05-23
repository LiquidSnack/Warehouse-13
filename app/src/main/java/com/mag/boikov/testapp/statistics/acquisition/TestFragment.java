package com.mag.boikov.testapp.statistics.acquisition;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mag.boikov.testapp.R;
import com.mag.boikov.testapp.statistics.GpsData;
import com.mag.boikov.testapp.statistics.GpsLocationListener;
import com.mag.boikov.testapp.statistics.NetworkStats;
import com.mag.boikov.testapp.statistics.PhoneCellInfo;
import com.mag.boikov.testapp.statistics.PhoneInfo;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestFragment extends Fragment {
    @InjectView(R.id.startTest)
    Button startTestButton;

    @InjectView(R.id.testOutput)
    TextView outputBox;

    PhoneInfo phoneInfo;
    GpsLocationListener gpsLocationListener;
    GetNetworkStatsTask getNetworkStatsTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment, container, false);
        ButterKnife.inject(this, view);
        setup();
        return view;
    }

    void setup() {
        Context context = context();
        phoneInfo = PhoneInfo.fromContext(context);
        outputBox.setMovementMethod(new ScrollingMovementMethod());
        setupStartTestButton();
        gpsLocationListener = GpsLocationListener.register(context);
    }

    void setupStartTestButton() {
        startTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performNetworkTest();
            }
        });
    }

    Context context() {
        return getActivity().getApplicationContext();
    }

    void performNetworkTest() {
        executeTasks();
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
        appendNetworkStats();
        appendGpsLocation();
    }

    void executeTasks() {
        getNetworkStatsTask = new GetNetworkStatsTask();
        getNetworkStatsTask.execute();
    }

    void appendGpsLocation() {
        GpsData gpsData = gpsLocationListener.gpsData();
        if (gpsData != GpsData.EMPTY) {
            outputBox.append('\n' + "Location: " + gpsData.getLatitude() + ";" + gpsData.getLongitude());
        }
    }

    void appendNetworkStats() {
        NetworkStats networkStats = getNetworkStatsTask.getNetworkData();
        if (networkStats == NetworkStats.EMPTY) return;
        outputBox.append(String.format("\nDownload Speed: %.3f Kbps", networkStats.getDownloadSpeed()));
        outputBox.append(String.format("\nUpload Speed: %.3f Kbps", networkStats.getUploadSpeed()));
        outputBox.append(String.format("\nPacket Loss: %d", networkStats.getPacketLoss()) + "%");
        outputBox.append(String.format("\nPing Time: %.1f", networkStats.getPingTime()) + " ms");
    }
}

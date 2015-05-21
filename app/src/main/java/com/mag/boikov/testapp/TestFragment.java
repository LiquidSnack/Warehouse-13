package com.mag.boikov.testapp;

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

import com.mag.boikov.testapp.communications.GpsData;
import com.mag.boikov.testapp.communications.NetworkData;
import com.mag.boikov.testapp.network_info.GetGpsLocationTask;
import com.mag.boikov.testapp.network_info.GetNetworkStatisticsTask;
import com.mag.boikov.testapp.network_info.PhoneCellInfo;
import com.mag.boikov.testapp.network_info.parser.PhoneInfo;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestFragment extends Fragment {
    @InjectView(R.id.startTest)
    Button startTestButton;

    @InjectView(R.id.testOutput)
    TextView outputBox;

    PhoneInfo phoneInfo;
    GetGpsLocationTask getGpsLocationTask;
    GetNetworkStatisticsTask getNetworkStatisticsTask;

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
        getGpsLocationTask = new GetGpsLocationTask(context);
        getNetworkStatisticsTask = new GetNetworkStatisticsTask();
        outputBox.setMovementMethod(new ScrollingMovementMethod());
        setupStartTestButton();
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
        appendGpsLocation();
        appendNetworkStats();
    }

    void executeTasks() {
        getNetworkStatisticsTask.execute();
        getGpsLocationTask.execute();
    }

    void appendGpsLocation() {
        GpsData gpsData = getGpsLocationTask.getGpsData();
        if (gpsData != GpsData.EMPTY) {
            outputBox.append('\n' + "Location: " + gpsData.getLatitude() + ";" + gpsData.getLongitude());
        }
    }

    void appendNetworkStats() {
        NetworkData networkData = getNetworkStatisticsTask.getNetworkData();
        if (networkData == NetworkData.EMPTY) return;
        outputBox.append(String.format("\nDownload Speed: %.3f Kbps", networkData.getDownloadSpeed()));
        outputBox.append(String.format("\nUpload Speed: %.3f Kbps", networkData.getUploadSpeed()));
        outputBox.append(String.format("\nPacket Loss: %d", networkData.getPacketLoss()) + "%");
        outputBox.append(String.format("\nPing Time: %.1f", networkData.getPingTime()) + " ms");
    }
}

package com.mag.boikov.testapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mag.boikov.testapp.communications.GsmData;
import com.mag.boikov.testapp.communications.Statistics;
import com.mag.boikov.testapp.communications.StatisticsSender;
import com.mag.boikov.testapp.communications.UserData;
import com.mag.boikov.testapp.network_info.GeoLocationListener;
import com.mag.boikov.testapp.network_info.NetFunctions;
import com.mag.boikov.testapp.network_info.PhoneCellInfo;
import com.mag.boikov.testapp.network_info.parser.PhoneInfo;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SendFragment extends Fragment {
    @InjectView(R.id.send)
    Button sendButton;
    EditText phoneBox;
    EditText commentBox;

    PhoneInfo phoneInfo;
    GeoLocationListener locationListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_fragment, container, false);
        ButterKnife.inject(this, view);
        setup();
        String phoneNumber = phoneBox.getText().toString();
        String comment = commentBox.getText().toString();
        return view;
    }

    void setup() {
        sendButton.setEnabled(isNetworkConnectionAvailable());
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
        phoneInfo = PhoneInfo.fromContext(getActivity().getApplicationContext());
        locationListener = new GeoLocationListener(context());
    }

    boolean isNetworkConnectionAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void sendData() {
        Statistics statistics = buildStatistics();
        try {
            HttpStatus httpStatus = new StatisticsSender().execute(statistics)
                                                          .get(3, TimeUnit.SECONDS);
            if (httpStatus != HttpStatus.OK) {
                Log.e("SendFragment", "Got response status " + httpStatus);
            }
        } catch (Exception e) {
            Log.e("SendFragment", e.toString());
        }
    }

    Statistics buildStatistics() {
        Statistics statistics = new Statistics();
        statistics.setGsmData(gsmData());
        statistics.setTestPerformedAt(new Date());
        statistics.setCellInfoByType(cellInfoByType());
        statistics.setNetworkData(NetFunctions.getNetworkData(context()));
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

    UserData userData() {
        UserData userData = new UserData();
        //userData.setPhoneNum();
        //userData.setComment();
        return userData();
    }

    Context context() {
        return getActivity().getApplicationContext();
    }
}

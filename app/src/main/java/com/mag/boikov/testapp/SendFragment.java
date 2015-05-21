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
import android.widget.CheckBox;
import android.widget.EditText;

import com.mag.boikov.testapp.communications.Complaint;
import com.mag.boikov.testapp.communications.GsmData;
import com.mag.boikov.testapp.communications.Statistics;
import com.mag.boikov.testapp.communications.StatisticsSender;
import com.mag.boikov.testapp.communications.UserComments;
import com.mag.boikov.testapp.network_info.GeoLocationListener;
import com.mag.boikov.testapp.network_info.NetFunctions;
import com.mag.boikov.testapp.network_info.PhoneCellInfo;
import com.mag.boikov.testapp.network_info.parser.PhoneInfo;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;

public class SendFragment extends Fragment {
    static final int SEND_STATISTICS_TIMEOUT = 3;

    @InjectView(R.id.send)
    Button sendButton;

    @InjectView(R.id.phoneBox)
    EditText phoneBox;

    @InjectView(R.id.commentBox)
    EditText commentBox;

    @InjectViews({R.id.cantHearOtherParty, R.id.otherPartyCantHearMe, R.id.callGetsInterrupted, R.id.slowInternetConnection, R.id.poorSoundQuality})
    List<CheckBox> complaintCheckBoxes;

    PhoneInfo phoneInfo;
    GeoLocationListener locationListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_fragment, container, false);
        ButterKnife.inject(this, view);
        setup();
        return view;
    }

    void setup() {
        Context context = context();
        phoneInfo = PhoneInfo.fromContext(context);
        locationListener = new GeoLocationListener(context);
        setupSendButton();
    }

    void setupSendButton() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
        sendButton.setEnabled(isNetworkConnectionAvailable());
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
                                                          .get(SEND_STATISTICS_TIMEOUT, TimeUnit.SECONDS);
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
        statistics.setNetworkData(NetFunctions.getNetworkData());
        statistics.setGpsData(locationListener.getLocation());
        statistics.setUserComments(userData());
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

    UserComments userData() {
        UserComments userComments = new UserComments();
        userComments.setContactPhone(phoneBox.getText()
                                             .toString());
        userComments.setComment(commentBox.getText()
                                          .toString());
        userComments.setComplaints(complaints());
        return userComments;
    }

    List<Complaint> complaints() {
        List<Complaint> complaints = new ArrayList<>();
        for (CheckBox complaint : complaintCheckBoxes) {
            if (complaint.isChecked()) {
                complaints.add(Complaint.valueOf(complaint.getId()));
            }
        }
        return complaints;
    }


    Context context() {
        return getActivity().getApplicationContext();
    }
}

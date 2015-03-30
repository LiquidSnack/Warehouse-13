package com.mag.boikov.testapp.communications;

import android.os.AsyncTask;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class StatisticsSender extends AsyncTask<Void, Void, String> {
    static final String serverUrl = "http://52.11.170.103:4848";

    public StatisticsSender() {
    }

    @Override
    protected String doInBackground(Void... params) {
        return sendStatistics();
    }


    String sendStatistics() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                    .add(new StringHttpMessageConverter());
        return restTemplate.getForObject(serverUrl, String.class);
    }
}

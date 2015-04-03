package com.mag.boikov.testapp.communications;

import android.os.AsyncTask;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class StatisticsSender extends AsyncTask<Statistics, Void, HttpStatus> {
    static final String serverUrl = "http://52.11.170.103:4848";

    RestTemplate restTemplate;

    public StatisticsSender() {
        init();
    }

    void init() {
        restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
    }

    @Override
    protected HttpStatus doInBackground(Statistics... params) {
        return sendStatistics(params[0]);
    }

    HttpStatus sendStatistics(Statistics statistics) {
        return restTemplate.postForEntity(serverUrl, statistics, Statistics.class)
                           .getStatusCode();
    }
}
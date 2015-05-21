package com.mag.boikov.testapp.communications;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class StatisticsSender extends AsyncTask<Statistics, Void, HttpStatus> {
    final String endpointUrl;
    RestTemplate restTemplate;

    public StatisticsSender() {
        endpointUrl = System.getProperty("endpointUrl");
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
        try {
            return send(params[0]);
        } catch (Exception e) {
            Log.e("StatisticsSender", e.toString());
            return HttpStatus.I_AM_A_TEAPOT;
        }
    }

    HttpStatus send(Statistics statistics) {
        return restTemplate.postForEntity(endpointUrl, statistics, Void.class)
                           .getStatusCode();
    }
}
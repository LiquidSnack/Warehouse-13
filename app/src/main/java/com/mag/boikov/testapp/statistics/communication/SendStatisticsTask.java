package com.mag.boikov.testapp.statistics.communication;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

class SendStatisticsTask extends AsyncTask<Statistics, Void, HttpStatus> {
    static final int TIMEOUT = 3;

    final String endpointUrl;
    RestTemplate restTemplate;

    public SendStatisticsTask() {
        endpointUrl = System.getProperty("endpointUrl") + "/statistics";
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
        HttpStatus responseStatus = send(params[0]);
        if (responseStatus != HttpStatus.CREATED) {
            Log.e("SendFragment", "Got response status " + responseStatus);
        }
        return responseStatus;
    }

    HttpStatus send(Statistics statistics) {
        return restTemplate.postForEntity(endpointUrl, statistics, Void.class)
                           .getStatusCode();
    }

    HttpStatus getResponseCode() {
        HttpStatus httpStatus = HttpStatus.I_AM_A_TEAPOT;
        try {
            httpStatus = get(TIMEOUT, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e("SendFragment", e.toString());
        }
        return httpStatus;
    }
}
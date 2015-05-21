package com.mag.boikov.testapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PagerActivity extends FragmentActivity {
    @InjectView(R.id.pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemProperties();
        setContentView(R.layout.pager);
        ButterKnife.inject(this);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
    }

    private void setSystemProperties() {
        String serverIp = getString(R.string.serverIp);
        System.setProperty("serverIp", serverIp);
        System.setProperty("endpointUrl", String.format("http://%s:%s", serverIp, getString(R.string.serverPort)));
    }
}

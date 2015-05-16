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
        setContentView(R.layout.pager);
        ButterKnife.inject(this);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
    }
}

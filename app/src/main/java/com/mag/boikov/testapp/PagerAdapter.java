package com.mag.boikov.testapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mag.boikov.testapp.statistics.acquisition.TestFragment;
import com.mag.boikov.testapp.statistics.communication.SendFragment;

import java.util.Arrays;
import java.util.List;

class PagerAdapter extends FragmentPagerAdapter {
    static final int TEST_TAB = 0;
    static final int SEND_TAB = 1;

    List<Fragment> tabs = Arrays.asList(new TestFragment(), new SendFragment());

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return tabs.get(i);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case TEST_TAB:
                return "Run Test";
            case SEND_TAB:
                return "Send Data to Operator";
            default:
                return "";
        }
    }
}

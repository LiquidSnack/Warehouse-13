package com.mag.boikov.testapp.network_info;

import android.os.AsyncTask;
import android.util.Log;

import com.googlecode.jpingy.Ping;
import com.googlecode.jpingy.Ping.Backend;
import com.googlecode.jpingy.PingArguments;
import com.googlecode.jpingy.PingResult;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PingStats extends AsyncTask<Void, Void, PingResult> {
    public static PingResult get(long timeout) {
        try {
            return new PingStats().execute()
                                  .get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
            Log.e("PingStats", e.toString());
            return null;
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected PingResult doInBackground(Void... params) {
        PingArguments args = new PingArguments.Builder().url("52.11.170.103")
                                                        .count(5)
                                                        .bytes(32)
                                                        .build();
        return Ping.ping(args, Backend.UNIX);
    }
}

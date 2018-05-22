package com.lt.vs.come.Sevice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MovieService extends Service {

    public MovieService() {
    }

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

package com.android.smsutil;

import android.app.Application;

import com.carlt.networklibs.NetworkManager;


public class SmsApplication extends Application {
    String TAG = "SmsApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getInstance().init(this);
    }

}

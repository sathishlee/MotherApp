package com.unicef.thaimai.motherapp.application;

import android.app.Application;

import com.unicef.thaimai.motherapp.networkService.NetworkService;

/**
 * Created by sathish on 2/15/2018.
 */

public class RxApplication extends Application {

    private static NetworkService networkService;

    @Override
    public void onCreate() {
        super.onCreate();

        networkService = new NetworkService();

    }

    public static NetworkService getNetworkService() {
        return networkService;
    }
}
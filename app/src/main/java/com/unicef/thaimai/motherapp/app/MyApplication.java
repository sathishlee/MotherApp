package com.unicef.thaimai.motherapp.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.unicef.thaimai.motherapp.broadCastReceivers.ConnectivityReceiver;
import com.unicef.thaimai.motherapp.utility.LocaleHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

//import com.unicef.thaimai.motherapp.bradcastReceiver.ConnectivityReceiver;


/**
 * Created by sathish on 3/25/2018.
 */

public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("Mother.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
        MultiDex.install(this);
    }

}

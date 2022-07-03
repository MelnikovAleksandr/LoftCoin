package com.mas.loftcoin;

import android.app.Application;
import android.os.StrictMode;

import com.google.firebase.messaging.FirebaseMessaging;
import com.mas.loftcoin.util.DebugTree;

import timber.log.Timber;

public class LoftApp extends Application {

    private BaseComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
            Timber.plant(new DebugTree());
        }
        component = DaggerAppComponent.builder()
                .application(this)
                .build();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(instanceIdResult -> {
            Timber.d("fcm: %s", instanceIdResult.getResult());
        });
    }

    public BaseComponent getComponent() {
        return component;
    }

}

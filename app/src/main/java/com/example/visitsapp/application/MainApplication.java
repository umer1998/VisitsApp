package com.example.visitsapp.application;

import android.app.Application;

public class MainApplication extends Application {

    private static MainApplication instance;


    public static MainApplication getAppContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }
}

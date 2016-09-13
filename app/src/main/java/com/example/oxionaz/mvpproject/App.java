package com.example.oxionaz.mvpproject;

import android.app.Application;

import com.example.oxionaz.mvpproject.di.AppComponent;
import com.example.oxionaz.mvpproject.di.DaggerAppComponent;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import dagger.internal.DaggerCollections;

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
        appComponent = buildComponent();
    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .build();
    }
}
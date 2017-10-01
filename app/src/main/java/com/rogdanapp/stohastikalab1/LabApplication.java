package com.rogdanapp.stohastikalab1;

import android.app.Application;

import com.rogdanapp.stohastikalab1.di.Injector;

public class LabApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Injector.initInjector(this);
    }
}

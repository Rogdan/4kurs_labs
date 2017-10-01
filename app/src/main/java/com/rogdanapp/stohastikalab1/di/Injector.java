package com.rogdanapp.stohastikalab1.di;

import android.content.Context;

import com.rogdanapp.stohastikalab1.di.components.ApplicationComponent;
import com.rogdanapp.stohastikalab1.di.components.DaggerApplicationComponent;
import com.rogdanapp.stohastikalab1.di.modules.ApplicationModule;


public class Injector {
    private static ApplicationComponent applicationComponent;

    public static void initInjector(Context context) {
        ApplicationModule applicationModule = new ApplicationModule(context);

        if(applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(applicationModule)
                    .build();
        }
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
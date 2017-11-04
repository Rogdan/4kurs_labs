package com.rogdanapp.stohastikalab1;

import android.app.Application;

import com.rogdanapp.stohastikalab1.di.Injector;
import com.rogdanapp.stohastikalab1.tools.WordsFormatter;

public class LabApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Injector.initInjector(this);

        String[] wordsToIgnore = getResources().getStringArray(R.array.words_to_ignore);
        WordsFormatter.init(wordsToIgnore);
    }
}

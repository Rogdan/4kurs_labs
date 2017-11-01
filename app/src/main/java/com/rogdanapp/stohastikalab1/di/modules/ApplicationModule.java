package com.rogdanapp.stohastikalab1.di.modules;

import android.content.Context;

import com.rogdanapp.stohastikalab1.data.InMemoryStore;
import com.rogdanapp.stohastikalab1.data.pojo.Analyzer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Context mContext;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    public Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    public InMemoryStore provideInMemoryStore(){
        return new InMemoryStore();
    }

    @Provides
    @Singleton
    public Analyzer provideAnalyzer() {
        return new Analyzer(mContext);
    }
}

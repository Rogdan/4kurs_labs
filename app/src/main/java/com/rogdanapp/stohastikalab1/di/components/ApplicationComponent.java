package com.rogdanapp.stohastikalab1.di.components;

import com.rogdanapp.stohastikalab1.di.modules.ApplicationModule;
import com.rogdanapp.stohastikalab1.ui.didenko.AnalyzeActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class})
@Singleton
public interface ApplicationComponent {

    AnalyzeActivity.AnalyzeComponent plus(AnalyzeActivity.AnalyzeModule analyzeModule);
}

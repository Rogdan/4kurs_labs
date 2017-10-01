package com.rogdanapp.stohastikalab1.di.components;

import com.rogdanapp.stohastikalab1.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class})
@Singleton
public interface ApplicationComponent {

}

package com.rogdanapp.stohastikalab1.di.components;

import com.rogdanapp.stohastikalab1.di.modules.ApplicationModule;
import com.rogdanapp.stohastikalab1.ui.experiment.ExperimentActivity;
import com.rogdanapp.stohastikalab1.ui.input.FieldInputActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class})
@Singleton
public interface ApplicationComponent {
    FieldInputActivity.FieldInputComponent plus(FieldInputActivity.FieldInputModule fieldInputModule);

    ExperimentActivity.ExperimentComponent plus(ExperimentActivity.ExperimentModule experimentModule);
}

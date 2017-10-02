package com.rogdanapp.stohastikalab1.ui.experiment;

import com.rogdanapp.stohastikalab1.core.Presenter;
import com.rogdanapp.stohastikalab1.data.InMemoryStore;

import javax.inject.Inject;

public class ExperimentPresenter extends Presenter<ExperimentComponent.IExperimentView> implements ExperimentComponent.IExperimentPresenter{
    private InMemoryStore repository;

    @Inject
    public ExperimentPresenter(InMemoryStore inMemoryStore) {
        this.repository = inMemoryStore;
    }
}

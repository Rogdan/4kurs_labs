package com.rogdanapp.stohastikalab1.ui.didenko;

import com.rogdanapp.stohastikalab1.core.Presenter;
import com.rogdanapp.stohastikalab1.data.InMemoryStore;

import javax.inject.Inject;

public class AnalyzePresenter extends Presenter<AnalyzeContract.IAnalyzeView> implements AnalyzeContract.IAnalyzePresenter {
    private InMemoryStore inMemoryStore;

    @Inject
    public AnalyzePresenter(InMemoryStore inMemoryStore) {
        this.inMemoryStore = inMemoryStore;
    }
}

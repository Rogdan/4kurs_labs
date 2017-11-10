package com.rogdanapp.stohastikalab1.ui.didenko.naive_bayes_lab_2_3.analyze;

import com.rogdanapp.stohastikalab1.core.Presenter;
import com.rogdanapp.stohastikalab1.data.InMemoryStore;
import com.rogdanapp.stohastikalab1.data.pojo.naive_bayes.AnalyzerTask;

import java.io.InputStream;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AnalyzePresenter extends Presenter<AnalyzeContract.IAnalyzeView> implements AnalyzeContract.IAnalyzePresenter {
    private InMemoryStore inMemoryStore;

    @Inject
    public AnalyzePresenter(InMemoryStore inMemoryStore) {
        this.inMemoryStore = inMemoryStore;
    }

    @Override
    public void startAnalyze(InputStream inputStream) {
        view().showProgress();

        Subscription subscription = Observable.fromCallable(() -> {
            AnalyzerTask analyzerTask = new AnalyzerTask();
            return analyzerTask.analyze(inputStream);
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view().isActive()) {
                        view().hideProgress();
                        inMemoryStore.setBaesAnalyzeResult(response);

                        view().onDataAnalyzed(response.getHamResult(), response.getSpamResult());
                    }
                });

        subscriptionsToUnbind.add(subscription);
    }
}
package com.rogdanapp.stohastikalab1.ui.didenko.analyze;

import com.rogdanapp.stohastikalab1.core.Presenter;
import com.rogdanapp.stohastikalab1.data.pojo.AnalyzerTask;

import java.io.InputStream;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AnalyzePresenter extends Presenter<AnalyzeContract.IAnalyzeView> implements AnalyzeContract.IAnalyzePresenter {
    private AnalyzerTask analyzer;

    @Inject
    public AnalyzePresenter() {
        analyzer = new AnalyzerTask();
    }

    @Override
    public void startAnalyze(InputStream inputStream) {
        view().showProgress();

        Subscription subscription = Observable.fromCallable(() -> {
            analyzer.analyze(inputStream);

            return analyzer;
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view().isActive()) {
                        view().hideProgress();

                        view().onDataAnalyzed(response.getAnalyzedHam(), response.getAnalyzedSpam());
                    }
                });

        subscriptionsToUnbind.add(subscription);
    }
}
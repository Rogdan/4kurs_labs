package com.rogdanapp.stohastikalab1.ui.didenko;

import com.rogdanapp.stohastikalab1.core.Presenter;
import com.rogdanapp.stohastikalab1.data.pojo.Analyzer;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AnalyzePresenter extends Presenter<AnalyzeContract.IAnalyzeView> implements AnalyzeContract.IAnalyzePresenter {
    private Analyzer analyzer;

    @Inject
    public AnalyzePresenter(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    @Override
    public void startAnalyze() {
        view().showProgress();

        Subscription subscription = Observable.fromCallable(() -> {
            analyzer.analysisData();

            return analyzer;
        })
                .observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view().isActive()) {
                        view().hideProgress();

                        view().onDataAnalyzed(analyzer.getAnalyzedHam(), analyzer.getAnalyzedSpam());
                    }
                });

        subscriptionsToUnbind.add(subscription);
    }
}
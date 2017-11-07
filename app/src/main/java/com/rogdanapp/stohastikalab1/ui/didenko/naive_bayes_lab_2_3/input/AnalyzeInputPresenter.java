package com.rogdanapp.stohastikalab1.ui.didenko.naive_bayes_lab_2_3.input;

import com.rogdanapp.stohastikalab1.core.Presenter;
import com.rogdanapp.stohastikalab1.data.InMemoryStore;
import com.rogdanapp.stohastikalab1.data.pojo.BaesAnalyzeResult;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AnalyzeInputPresenter extends Presenter<AnalyzeInputContract.IAnalyzeInputView> implements AnalyzeInputContract.IAnalyzeInputPresenter {
    private InMemoryStore inMemoryStore;

    @Inject
    public AnalyzeInputPresenter(InMemoryStore inMemoryStore) {
        this.inMemoryStore = inMemoryStore;
    }

    @Override
    public void startAnalyze(String textToAnalyze) {
        view().showProgress();

        BaesAnalyzeResult analyzeResult = inMemoryStore.getBaesAnalyzeResult();
        final String message = textToAnalyze.trim().toLowerCase();

        Subscription subscription = Observable.fromCallable(() -> analyzeResult.getSpamProbability(message))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(spamProbability -> {
                    if (view().isActive()) {
                        view().hideProgress();

                        double spamProbabilityPercent = spamProbability * 100;
                        double hamProbabilityPercent = 100 - spamProbabilityPercent;

                        view().onDataAnalyzed(spamProbabilityPercent, hamProbabilityPercent);
                    }
                }, throwable -> {
                    if (view().isActive()) {
                        view().hideProgress();
                    }
                });

        subscriptionsToUnbind.add(subscription);
    }
}

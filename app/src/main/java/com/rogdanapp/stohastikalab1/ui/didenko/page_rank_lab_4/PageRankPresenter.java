package com.rogdanapp.stohastikalab1.ui.didenko.page_rank_lab_4;

import com.rogdanapp.stohastikalab1.core.Presenter;
import com.rogdanapp.stohastikalab1.data.pojo.page_rank.PageRankTask;

import java.net.URISyntaxException;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PageRankPresenter extends Presenter<PageRankContract.IPageRankView> implements PageRankContract.IPageRankPresenter {
    @Override
    public void calculatePageRank(String link) {
        try {
            PageRankTask pageRankTask = new PageRankTask(link);

            view().showProgress();

            Subscription subscription = pageRankTask.runCalculation()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (view().isActive()) {
                            view().onPageRankCalculated(response);
                            view().hideProgress();
                        }
                    }, throwable -> {
                        if (view().isActive()) {
                            view().onCalculatingError(throwable.getMessage());
                            view().hideProgress();
                        }
                    });

            subscriptionsToUnbind.add(subscription);

        } catch (URISyntaxException e) {
            view().onCalculatingError("Не верный формат ссылки: " + e.getMessage());
        }
    }
}
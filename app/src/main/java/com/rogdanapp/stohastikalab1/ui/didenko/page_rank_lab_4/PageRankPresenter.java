package com.rogdanapp.stohastikalab1.ui.didenko.page_rank_lab_4;

import com.rogdanapp.stohastikalab1.core.Presenter;

public class PageRankPresenter extends Presenter<PageRankContract.IPageRankView> implements PageRankContract.IPageRankPresenter {
    public PageRankPresenter() {}

    @Override
    public void calculatePageRank(String link) {
        view().onPageRankCalculated(12);
    }
}

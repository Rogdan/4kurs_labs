package com.rogdanapp.stohastikalab1.ui.didenko.page_rank_lab_4;

import com.rogdanapp.stohastikalab1.core.IBaseView;

public interface PageRankContract {
    interface IPageRankView extends IBaseView {
        void onPageRankCalculated(double pageRank);
        void onCalculatingError(String message);
    }

    interface IPageRankPresenter {
        void calculatePageRank(String link);
    }
}

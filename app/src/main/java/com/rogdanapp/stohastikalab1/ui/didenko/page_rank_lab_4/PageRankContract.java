package com.rogdanapp.stohastikalab1.ui.didenko.page_rank_lab_4;

import com.rogdanapp.stohastikalab1.core.IBaseView;
import com.rogdanapp.stohastikalab1.data.pojo.page_rank.Page;

import java.util.ArrayList;

public interface PageRankContract {
    interface IPageRankView extends IBaseView {
        void onPageRankCalculated(ArrayList<Page> pages);
        void onCalculatingError(String message);
    }

    interface IPageRankPresenter {
        void calculatePageRank(String link, int iterationCount);
    }
}

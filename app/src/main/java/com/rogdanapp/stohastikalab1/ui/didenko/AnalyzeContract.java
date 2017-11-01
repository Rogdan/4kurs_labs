package com.rogdanapp.stohastikalab1.ui.didenko;

import com.rogdanapp.stohastikalab1.core.IBaseView;
import com.rogdanapp.stohastikalab1.data.pojo.AnalyzerItem;

import java.util.ArrayList;

public class AnalyzeContract {
    interface IAnalyzeView extends IBaseView {
        void onDataAnalyzed(ArrayList<AnalyzerItem> analyzedHam, ArrayList<AnalyzerItem> analyzedSpam);
    }

    interface IAnalyzePresenter {

        void startAnalyze();
    }
}

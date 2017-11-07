package com.rogdanapp.stohastikalab1.ui.didenko.naive_bayes_lab_2_3.input;

import com.rogdanapp.stohastikalab1.core.IBaseView;

public interface AnalyzeInputContract {
    interface IAnalyzeInputView extends IBaseView {
        void onDataAnalyzed(double spamProbabilityPercent, double hamProbabilityPercent);
    }

    interface IAnalyzeInputPresenter {
        void startAnalyze(String textToAnalyze);
    }
}

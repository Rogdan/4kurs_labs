package com.rogdanapp.stohastikalab1.ui.didenko.analyze.input;

import com.rogdanapp.stohastikalab1.core.IBaseView;

public interface AnalyzeInputContract {
    interface IAnalyzeInputView extends IBaseView {

    }

    interface IAnalyzeInputPresenter {
        void startAnalyze(String textToAnalyze);
    }
}

package com.rogdanapp.stohastikalab1.ui.didenko.naive_bayes_lab_2_3.analyze;

import com.rogdanapp.stohastikalab1.core.IBaseView;
import com.rogdanapp.stohastikalab1.data.pojo.BaesClass;

import java.io.InputStream;

public class AnalyzeContract {
    interface IAnalyzeView extends IBaseView {
        void onDataAnalyzed(BaesClass hamResult, BaesClass spamResult);
    }

    interface IAnalyzePresenter {
        void startAnalyze(InputStream inputStream);
    }
}

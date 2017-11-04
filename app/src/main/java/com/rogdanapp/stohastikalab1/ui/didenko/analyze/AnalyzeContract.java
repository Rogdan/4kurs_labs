package com.rogdanapp.stohastikalab1.ui.didenko.analyze;

import com.rogdanapp.stohastikalab1.core.IBaseView;
import com.rogdanapp.stohastikalab1.data.pojo.AnalyzerItem;
import com.rogdanapp.stohastikalab1.data.pojo.BaesClass;

import java.io.InputStream;
import java.util.ArrayList;

public class AnalyzeContract {
    interface IAnalyzeView extends IBaseView {
        void onDataAnalyzed(BaesClass hamResult, BaesClass spamResult);
    }

    interface IAnalyzePresenter {
        void startAnalyze(InputStream inputStream);
    }
}

package com.rogdanapp.stohastikalab1.di.components;

import com.rogdanapp.stohastikalab1.di.modules.ApplicationModule;
import com.rogdanapp.stohastikalab1.ui.didenko.naive_bayes_lab_2_3.analyze.AnalyzeActivity;
import com.rogdanapp.stohastikalab1.ui.didenko.naive_bayes_lab_2_3.input.AnalyzeInputActivity;
import com.rogdanapp.stohastikalab1.ui.didenko.page_rank_lab_4.PageRankActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class})
@Singleton
public interface ApplicationComponent {

    AnalyzeActivity.AnalyzeComponent plus(AnalyzeActivity.AnalyzeModule analyzeModule);

    AnalyzeInputActivity.AnalyzeInputComponent plus(AnalyzeInputActivity.AnalyzeInputModule analyzeInputModule);

    PageRankActivity.PageRankComponent plus(PageRankActivity.PageRankModule pageRankModule);
}

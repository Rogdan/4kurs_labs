package com.rogdanapp.stohastikalab1.ui.didenko.analyze.input;

import com.rogdanapp.stohastikalab1.core.Presenter;
import com.rogdanapp.stohastikalab1.data.InMemoryStore;
import com.rogdanapp.stohastikalab1.data.pojo.BaesAnalyzeResult;
import com.rogdanapp.stohastikalab1.tools.Informator;

import javax.inject.Inject;

public class AnalyzeInputPresenter extends Presenter<AnalyzeInputContract.IAnalyzeInputView> implements AnalyzeInputContract.IAnalyzeInputPresenter {
    private InMemoryStore inMemoryStore;

    @Inject
    public AnalyzeInputPresenter(InMemoryStore inMemoryStore) {
        this.inMemoryStore = inMemoryStore;
    }

    @Override
    public void startAnalyze(String textToAnalyze) {
        textToAnalyze = textToAnalyze.trim().toLowerCase();
        BaesAnalyzeResult analyzeResult = inMemoryStore.getBaesAnalyzeResult();
        
        double spamProbability = analyzeResult.getSpamProbability(textToAnalyze);
        double spamProbabilityPercent = spamProbability * 100;
        double hamProbabilityPercent = 100 - spamProbability;

        Informator.log("S: " + spamProbabilityPercent +"%, H: " + hamProbabilityPercent);
    }
}

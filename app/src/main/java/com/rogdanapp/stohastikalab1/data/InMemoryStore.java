package com.rogdanapp.stohastikalab1.data;

import com.rogdanapp.stohastikalab1.data.pojo.naive_bayes.BaesAnalyzeResult;

public class InMemoryStore {
    private static BaesAnalyzeResult baesAnalyzeResult;

    public BaesAnalyzeResult getBaesAnalyzeResult() {
        return baesAnalyzeResult;
    }

    public void setBaesAnalyzeResult(BaesAnalyzeResult baesAnalyzeResult) {
        InMemoryStore.baesAnalyzeResult = baesAnalyzeResult;
    }
}

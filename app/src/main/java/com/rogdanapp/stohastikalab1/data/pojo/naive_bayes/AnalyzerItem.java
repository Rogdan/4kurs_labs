package com.rogdanapp.stohastikalab1.data.pojo.naive_bayes;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class AnalyzerItem implements Comparable<AnalyzerItem>, Serializable{
    private float inPercent;
    private int count;
    private String text;

    public float getInPercent() {
        return inPercent;
    }

    public void setInPercent(float inPercent) {
        this.inPercent = inPercent;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int compareTo(@NonNull AnalyzerItem o) {
        return Integer.compare(o.getCount(), count);
    }
}

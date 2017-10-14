package com.rogdanapp.stohastikalab1.ui.didenko;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class AnalyzerItem implements Comparable<AnalyzerItem>, Serializable{
    private float inPercent;
    private int count;
    private String line;

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

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    @Override
    public int compareTo(@NonNull AnalyzerItem o) {
        return Integer.compare(o.getCount(), count);
    }
}

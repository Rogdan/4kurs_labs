package com.rogdanapp.stohastikalab1.data.pojo.page_rank;

public class PageRankResult {
    private int pagesFound;
    private double finalPageRank;

    public int getPagesFound() {
        return pagesFound;
    }

    public void setPagesFound(int pagesFound) {
        this.pagesFound = pagesFound;
    }

    public double getFinalPageRank() {
        return finalPageRank;
    }

    public void setFinalPageRank(double finalPageRank) {
        this.finalPageRank = finalPageRank;
    }
}

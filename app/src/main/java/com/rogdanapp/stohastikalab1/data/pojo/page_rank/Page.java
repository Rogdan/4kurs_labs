package com.rogdanapp.stohastikalab1.data.pojo.page_rank;

import java.util.HashSet;

public class Page {
    private String pageLink;
    private HashSet<Page> innerLinks;

    private int currentIteration;
    private double currentRank, bufferRank;
    private double damping;

    public Page(String pageLink) {
        this.pageLink = pageLink;
        this.damping = 0.85;

        innerLinks = new HashSet<>();
        currentRank = 1;
        currentIteration = 0;
        bufferRank = 0;
    }

    public void addInnerLink(Page link) {
        innerLinks.add(link);
    }

    public void iterate(int iterationNumber) {
        checkIteration(iterationNumber);
        double mass = calculateMass();

        for (Page page : innerLinks) {
            page.addMass(mass, iterationNumber);
        }
    }

    private void checkIteration(int iterationNumber) {
        if (currentIteration != iterationNumber) {
            currentIteration = iterationNumber;
            flush();
        }
    }

    public void flush() {
        currentRank += bufferRank;
        bufferRank = 0;
    }

    private double calculateMass() {
        return damping * (currentRank / innerLinks.size());
    }

    public void addMass(double mass, int iteration) {
        checkIteration(iteration);
        bufferRank += mass;
    }

    public double getRank() {
        return currentRank;
    }

    public int getInnerLinksCount() {
        return innerLinks.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        return pageLink.equals(page.pageLink);
    }

    @Override
    public int hashCode() {
        return pageLink.hashCode();
    }

    @Override
    public String toString() {
        return "Link: " + pageLink + ", PR: " + currentRank;
    }

    public String getPageLink() {
        return pageLink;
    }

    public void setPageLink(String pageLink) {
        this.pageLink = pageLink;
    }
}

package com.rogdanapp.stohastikalab1.data.pojo.page_rank;

import java.util.HashSet;

public class PageNode {
    private String pageLink;
    private HashSet<String> innerLinks;

    public PageNode(String pageLink) {
        this.pageLink = pageLink;
        innerLinks = new HashSet<>();
    }

    public void addInnerLink(String link) {
        innerLinks.add(link);
    }
}

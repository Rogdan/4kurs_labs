package com.rogdanapp.stohastikalab1.data.pojo.page_rank;

import android.support.annotation.Nullable;

import com.rogdanapp.stohastikalab1.tools.Informator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import rx.Observable;

public class PageRankTask {
    private HashMap<String, Page> allPages;
    private String mainLink;
    private String domain;

    public PageRankTask(String mainLink) throws URISyntaxException {
        this.mainLink = mainLink;
        this.allPages = new HashMap<>();

        domain = extractDomain(mainLink);
        Informator.log("PageRankTest created. Link: " + mainLink + ", domain: " + domain);
    }

    private String extractDomain(String link) throws URISyntaxException {
        URI uri = new URI(link);
        String domain = uri.getHost();

        if (domain == null) {
            throw new URISyntaxException("проверьте правильность ссылки.", "Ошибка при попытке проверить домен");
        }
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    //todo сделать многопоточность
    public Observable<ArrayList<Page>> runCalculation(int iterationCount) {
        return Observable.fromCallable(() -> {
            Page rootPage = buildNodeHierarchy(mainLink);

            if (rootPage != null) {
                return iterate(iterationCount);
            } else {
                throw new Exception("Не удалось подключится к странице. Проверьте правильность ссылки.");
            }
        });
    }

    public ArrayList<Page> iterate(int iterationCount) {
        for (int i = 0; i < iterationCount; i++) {
            for (String url : allPages.keySet()) {
                Page page = allPages.get(url);
                page.iterate(i);
            }
        }

        ArrayList<Page> pages = new ArrayList<>();
        for (String url : allPages.keySet()) {
            Page page = allPages.get(url);
            page.flush();
            pages.add(page);
        }

        Collections.sort(pages);
        return pages;
    }

    @Nullable
    private Page buildNodeHierarchy(String url) {
        Page page = null;
        try {
            Document document = Jsoup.connect(url).get();
            Informator.log("Start analyze. Link: " + url);

            page = new Page(url);
            Elements elements = document.select("a");

            if (!allPages.containsKey(url)) {
                allPages.put(url, page);
            }

            for(Element element : elements){
                String innerLink = element.absUrl("href");

                if (isLinkValid(innerLink)) {
                    Page innerPage;

                    if (allPages.containsKey(innerLink)) {
                        innerPage = allPages.get(innerLink);
                    } else {
                        innerPage = buildNodeHierarchy(innerLink);
                    }

                    if (innerPage != null) {
                        page.addInnerLink(innerPage);
                    }
                }
            }
        } catch (Exception e) {
            Informator.error(getClass(), "Ошибка подключения по ссылке: " + url + ". Сообщения ошибки: " + e.getMessage());
        }

        if (page != null) {
            Informator.log("End analyze. Inner links:" + page.getInnerLinksCount() + " Link: " + url);
        }
        return page;
    }

    private boolean isLinkValid(String uri) {
        return !uri.contains("#") && belongDomain(uri);
    }

    private boolean belongDomain(String uri){
        String uriDomain;
        try {
            uriDomain = extractDomain(uri);
        } catch (URISyntaxException e) {
            return false;
        }

        return uriDomain.equals(domain);
    }

    public String getMainLink() {
        return mainLink;
    }
}
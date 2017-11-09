package com.rogdanapp.stohastikalab1.data.pojo.page_rank;

import android.support.annotation.Nullable;

import com.rogdanapp.stohastikalab1.tools.Informator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import rx.Observable;

public class PageRankTask {
    private HashMap<String, Page> allPages;
    private String mainLink;
    private String domain;
    private PageRankResult taskResult;

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

    //todo сделать многозадачность
    public Observable<PageRankResult> runCalculation(int iterationCount) {
        taskResult = new PageRankResult();

        return Observable.fromCallable(() -> {
            Page rootPage = buildNodeHierarchy(mainLink);

            for (int i = 0; i < iterationCount; i++) {
                for (String url : allPages.keySet()) {
                    Page page = allPages.get(url);
                    page.iterate(i);
                }
            }

            taskResult.setPagesFound(allPages.keySet().size());
            if (rootPage == null) {
                taskResult.setFinalPageRank(UNDEFINED_DOUBLE);
            } else {
                rootPage.flush();
                taskResult.setFinalPageRank(rootPage.getRank());
            }

            return taskResult;
        });
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
        String uriDomain = null;
        try {
            uriDomain = extractDomain(uri);
        } catch (URISyntaxException e) {
            return false;
        }

        return uriDomain.equals(domain);
    }

    private static final double UNDEFINED_DOUBLE = -1d;
}
package com.rogdanapp.stohastikalab1.data.pojo.page_rank;

import android.webkit.URLUtil;

import com.rogdanapp.stohastikalab1.tools.Informator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;

import rx.Observable;
import rx.Subscription;

public class PageRankTask {
    private HashSet<String> allLinks;
    private String mainLink;
    private String domain;

    public PageRankTask(String mainLink) throws URISyntaxException {
        this.mainLink = mainLink;
        this.allLinks = new HashSet<>();

        extractDomainName();
        Informator.log("PageRankTest created. Link: " + mainLink + ", domain: " + domain);
    }

    private void extractDomainName() throws URISyntaxException {
        URI uri = new URI(mainLink);
        String domain = uri.getHost();
        this.domain = domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public Observable<Integer> runCalculation() {
        return Observable.fromCallable(() -> {
            pageRankRecursion(mainLink);
            return 10;
        });
    }

    private void pageRankRecursion(String url) {
        if (!allLinks.contains(url)) {
            allLinks.add(url);
        }

        Informator.log("Run recursion. Link: " + url);

        try {
            Document document = Jsoup.connect(url).get();
            PageNode pageNode = new PageNode(url);
            Elements elements = document.select("a");

            for(Element element : elements){
                String innerLink = element.absUrl("href");
                if (belongDomain(innerLink)) {
                    pageNode.addInnerLink(innerLink);

                    if (!allLinks.contains(innerLink)) {
                        allLinks.add(innerLink);
                        pageRankRecursion(innerLink);
                    }
                }
            }
        } catch (Exception e) {
            Informator.error(getClass(), "Ошибка подключения по ссылке: " + url + ". Сообщения ошибки: " + e.getMessage());
        }

        Informator.log("===== End recursion. Link: " + url + " =====");
    }

    private boolean belongDomain(String uri) {
        return uri.contains(domain);
    }
}
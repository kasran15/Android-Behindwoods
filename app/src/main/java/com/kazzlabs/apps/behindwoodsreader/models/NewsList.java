package com.kazzlabs.apps.behindwoodsreader.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kasturip on 9/20/15.
 */
public class NewsList {

    private static NewsList instance = new NewsList();

    private List<NewsItem> newsList;


    private NewsList() {
        newsList = new ArrayList<NewsItem>();
    }

    public static NewsList getInstance() {
        return instance;
    }

    public List<NewsItem> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsItem> newsList) {
        this.newsList = newsList;
    }
}

package com.kazzlabs.apps.behindwoodsreader.models;

/**
 * Model used in the home page.
 *
 * Created by kasturip on 8/9/14.
 */
public class NewsItem {

    private String mLink;

    private String mTitle;

    public NewsItem(String link) {
        mLink = link;
        mTitle = createTitle(link);
    }

    private String createTitle(String link) {
        StringBuffer title = new StringBuffer(link.substring(link.lastIndexOf('/') + 1, link.indexOf('.')).replaceAll("-", " "));
        title.setCharAt(0, Character.toUpperCase(title.charAt(0)));
        return title.toString();
    }

    public String getTitle() {
        return mTitle;
    }

    public String getLink() {
        return mLink;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}

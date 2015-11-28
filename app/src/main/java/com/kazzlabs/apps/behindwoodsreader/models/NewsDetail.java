package com.kazzlabs.apps.behindwoodsreader.models;

import android.graphics.Bitmap;

/**
 * Model used in individual news page.
 *
 * Created by kasturip on 8/9/14.
 */
public class NewsDetail {

    private Bitmap image;

    private String article;

    private String title;

    public NewsDetail(String title, Bitmap image, String article) {
        this.image = image;
        this.article = article;
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getArticle() {
        return article;
    }
}

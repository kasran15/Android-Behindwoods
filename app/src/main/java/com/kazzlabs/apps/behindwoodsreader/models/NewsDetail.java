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

    public NewsDetail(Bitmap image, String article) {
        this.image = image;
        this.article = article;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getArticle() {
        return article;
    }
}

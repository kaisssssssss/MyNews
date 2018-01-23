package com.kaichen.mynews;

import android.graphics.Bitmap;

/**
 * Created by Kai on 1/21/2018.
 */

public class NewsItem {
    private String title, section, source, url, date, body;
    Bitmap image;

    public NewsItem(String title, String section, String source, String url, String date, Bitmap image) {
        this.title = title;
        this.section = section;
        this.source = source;
        this.url = url;
        this.date = date;
        this.image = image;
    }

    public NewsItem(NewsItem that) {
        this.title = that.getTitle();
        this.date = that.getDate();
        this.section = that.getSection();
        this.source = that.getSource();
        this.url = that.getUrl();
        this.image = that.getImage();
        this.body = that.getBody();
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public Bitmap getImage() {
        return image;
    }
}

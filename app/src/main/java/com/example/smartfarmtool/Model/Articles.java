package com.example.smartfarmtool.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Articles {
    @SerializedName("Source")
    @Expose
    private Source source;

    @SerializedName("author")
    @Expose
    private String  author;

    @SerializedName("title")
    @Expose
    private String  title;

    @SerializedName("description")
    @Expose
    private String  desciption;

    @SerializedName("url")
    @Expose
    private String  url;

    @SerializedName("urlToImage")
    @Expose
    private String urlToImage;

    @SerializedName("publishedAt")
    @Expose
    private String  publishedAt;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}

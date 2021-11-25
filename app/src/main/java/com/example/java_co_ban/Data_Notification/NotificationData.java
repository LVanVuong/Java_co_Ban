package com.example.java_co_ban.Data_Notification;

public class NotificationData {
    String title;
    String content;
    String titleFull;
    String contentFull;
    String urlFilm;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentFull(String contentFull) {
        this.contentFull = contentFull;
    }

    public void setTitleFull(String titleFull) {
        this.titleFull = titleFull;
    }

    public void setUrlFilm(String urlFilm) {
        this.urlFilm = urlFilm;
    }

    public String getContent() {
        return content;
    }

    public String getContentFull() {
        return contentFull;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleFull() {
        return titleFull;
    }

    public String getUrlFilm() {
        return urlFilm;
    }
}


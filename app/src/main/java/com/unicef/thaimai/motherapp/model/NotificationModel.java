package com.unicef.thaimai.motherapp.model;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class NotificationModel {
    private String title, genre, year;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public NotificationModel(String title, String genre, String year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }
}

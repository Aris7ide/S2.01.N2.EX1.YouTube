package com.models;

import java.time.LocalDateTime;

public class Comment {

    private int id;
    private String text;
    private LocalDateTime date;
    private User user;
    private Video video;

    public Comment(String text, LocalDateTime date, User user, Video video) {
        this.text = text;
        this.date = date;
        this.user = user;
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}

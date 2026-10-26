package com.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Video {

    private int id;
    private String title;
    private String description;
    private double size;
    private String fileName;
    private String thumbnailUrl;
    private String state;
    private User user;
    private Channel channel;
    private LocalDate date;

    private List<LikeVideo> likesList;

    private int likes;

    public Video(String title, String description, double size, String fileName, String thumbnailUrl,
                 String state, User user,
                 Channel channel, LocalDate date) {

        this.title = title;
        this.description = description;
        this.size = size;
        this.fileName = fileName;
        this.thumbnailUrl = thumbnailUrl;
        this.state = state;
        this.user = user;
        this.channel = channel;
        this.date = date;

        this.likesList = new ArrayList<>();
        this.likes = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<LikeVideo> getLikesList() {
        return likesList;
    }

    public void setLikesList(List<LikeVideo> likesList) {
        this.likesList = likesList;
    }
}

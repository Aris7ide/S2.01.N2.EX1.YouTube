package com.models;

public class LikeVideo {

    private User user;
    private Video video;
    private String likeDislike;

    public LikeVideo(User user, Video video, String likeDislike) {
        this.user = user;
        this.video = video;
        this.likeDislike = likeDislike;
    }

    public String getLikeDislike() {
        return likeDislike;
    }

    public void setLikeDislike(String likeDislike) {
        this.likeDislike = likeDislike;
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

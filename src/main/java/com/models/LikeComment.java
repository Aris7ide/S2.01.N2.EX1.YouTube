package com.models;

import java.time.LocalDateTime;

public class LikeComment {

    private User user;
    private Comment comment;
    private LocalDateTime likeDate;
    private String type;

    public LikeComment(User user, Comment comment, LocalDateTime likeDate, String type) {
        this.user = user;
        this.comment = comment;
        this.likeDate = likeDate;
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public LocalDateTime getLikeDate() {
        return likeDate;
    }

    public void setLikeDate(LocalDateTime likeDate) {
        this.likeDate = likeDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

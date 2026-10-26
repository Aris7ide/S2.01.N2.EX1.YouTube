package com.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Channel {

    private int id;
    private String title;
    private String description;
    private LocalDate timeCreated;
    private User user;

    public Channel(String title, String description,
                   LocalDate timeCreated, User user) {
        this.title = title;
        this.description = description;
        this.timeCreated = timeCreated;
        this.user = user;
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

    public LocalDate getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDate timeCreated) {
        this.timeCreated = timeCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package com.models;

import java.time.LocalDate;

public class Playlist {

    private int id;
    private String name;
    private LocalDate creationDate;
    private User user;
    private String state;

    public Playlist(String name, LocalDate creationDate, User user, String state) {
        this.name = name;
        this.creationDate = creationDate;
        this.user = user;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

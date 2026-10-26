package com.models;

public class Subscription {

    private Channel channel;
    private User user;

    public Subscription(Channel channel, User user) {
        this.channel = channel;
        this.user = user;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

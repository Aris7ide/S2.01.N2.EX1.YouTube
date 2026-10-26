package com.models;

public class PlaylistVideo {

    private Playlist playlist;
    private Video video;

    public PlaylistVideo(Playlist playlist, Video video) {
        this.video = video;
        this.playlist = playlist;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}

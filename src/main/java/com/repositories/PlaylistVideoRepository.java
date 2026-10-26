package com.repositories;

import com.models.Playlist;
import com.models.PlaylistVideo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistVideoRepository {
    public PlaylistVideo save(Connection connection, PlaylistVideo playlistVideo) {
        String sql = "INSERT INTO playlist_video (playlist_id, video_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, playlistVideo.getPlaylist().getId());
            ps.setInt(2, playlistVideo.getVideo().getId());
            ps.executeUpdate();
            System.out.println(playlistVideo.getVideo().getTitle() + " has been saved into "
                    + playlistVideo.getPlaylist().getName() + " by user "
                    + playlistVideo.getPlaylist().getUser().getUsername());
            return findById(connection, playlistVideo.getPlaylist().getId(), playlistVideo.getVideo().getId());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public PlaylistVideo findById(Connection connection, int playlist_id, int video_id) {
        String sql = "SELECT * FROM playlist_video WHERE playlist_id = ? AND video_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, playlist_id);
            ps.setInt(2, video_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PlaylistRepository playlistRepository = new PlaylistRepository();
                    VideoRepository videoRepository = new VideoRepository();
                    return new PlaylistVideo(
                            playlistRepository.findById(connection, rs.getInt("playlist_id")),
                            videoRepository.findById(connection, rs.getInt("video_id")));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<PlaylistVideo> findByPlaylist(Connection connection, int playlist_id) {
        List<PlaylistVideo> playlistVideos = new ArrayList<>();
        String sql = "SELECT * FROM playlist_video WHERE playlist_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, playlist_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PlaylistRepository playlistRepository = new PlaylistRepository();
                    VideoRepository videoRepository = new VideoRepository();
                    PlaylistVideo playlistVideo = new PlaylistVideo(
                            playlistRepository.findById(connection, rs.getInt("playlist_id")),
                            videoRepository.findById(connection, rs.getInt("video_id"))
                    );
                    playlistVideos.add(playlistVideo);
                }
                return playlistVideos;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

package com.repositories;

import com.models.LikeVideo;
import com.models.User;
import com.models.Video;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikeVideoRepository {
    public LikeVideo like(Connection connection, LikeVideo likeVideo, String type) {
        String sql = "INSERT INTO likes_video (user_id, video_id, type) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE type = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, likeVideo.getUser().getId());
            ps.setInt(2, likeVideo.getVideo().getId());
            ps.setString(3,type);
            ps.setString(4,type);

            ps.executeUpdate();

            if (likeVideo.getLikeDislike().equals("l")) {
                System.out.println(likeVideo.getVideo().getTitle() + " has been liked successfully by " + likeVideo.getUser().getUsername());
            } else {
                System.out.println(likeVideo.getVideo().getTitle() + " has been disliked successfully by " + likeVideo.getUser().getUsername());
            }

            return  findByUserAndVideo(connection,likeVideo.getUser().getId(),likeVideo.getVideo().getId());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public LikeVideo findByUserAndVideo(Connection connection, int user_id, int video_id) {
        String sql = "SELECT * FROM likes_video WHERE user_id = ? AND video_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user_id);
            ps.setInt(2, video_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserRepository userRepository = new UserRepository();
                    VideoRepository videoRepository = new VideoRepository();
                    return new LikeVideo(
                            userRepository.findById(connection, user_id),
                            videoRepository.findById(connection,video_id),
                            rs.getString("type")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<LikeVideo> findByUser(Connection connection, User user, String type) {
        List<LikeVideo> likeVideoList = new ArrayList<>();
        String sql = "SELECT * FROM likes_video WHERE user_id = ? AND type = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user.getId());
            ps.setString(2, type);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VideoRepository videoRepository = new VideoRepository();
                    LikeVideo likeVideo = new LikeVideo(
                            user,
                            videoRepository.findById(connection,rs.getInt("video_id")),
                            rs.getString("type")
                    );
                    likeVideoList.add(likeVideo);
                }
            }
            return  likeVideoList;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<LikeVideo> findByVideo(Connection connection, Video video, String type) {
        List<LikeVideo> likeVideoList = new ArrayList<>();
        String sql = "SELECT * FROM likes_video WHERE video_id = ? AND type = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, video.getId());
            ps.setString(2, type);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserRepository userRepository = new UserRepository();
                    LikeVideo likeVideo = new LikeVideo(
                            userRepository.findById(connection,rs.getInt("Video_id")),
                            video,
                            rs.getString("type")
                    );
                    likeVideoList.add(likeVideo);
                }
            }
            return  likeVideoList;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

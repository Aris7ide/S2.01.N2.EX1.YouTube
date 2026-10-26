package com.repositories;

import com.models.Comment;

import java.sql.*;

public class CommentRepository {
    public Comment save(Connection connection, Comment comment) {
        String sql = "INSERT INTO comments (text, date, user_id, video_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, comment.getText());
            ps.setObject(2, comment.getDate());
            ps.setInt(3, comment.getUser().getId());
            ps.setInt(4, comment.getVideo().getId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    comment.setId(rs.getInt(1));
                }
            }

            System.out.println("The comment has been saved successfully with id " +  comment.getId());
            return findById(connection, comment.getId());
        } catch (SQLException e) {
            System.err.println("Error saving comment");
        }
        return null;
    }

    public Comment findById(Connection connection, int id) {
        String sql = "SELECT * FROM comments WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserRepository userRepository = new UserRepository();
                    VideoRepository videoRepository = new VideoRepository();
                    return new Comment(
                            rs.getString("text"),
                            rs.getTimestamp("date").toLocalDateTime(),
                            userRepository.findById(connection, rs.getInt("user_id")),
                            videoRepository.findById(connection, rs.getInt("video_id"))
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding comment " + e.getMessage());
        }
        return null;
    }
}

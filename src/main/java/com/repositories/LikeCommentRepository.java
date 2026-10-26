package com.repositories;

import com.models.LikeComment;

import java.sql.*;

public class LikeCommentRepository {
    public LikeComment save(Connection connection, LikeComment likeComment) {
        String sql = "INSERT INTO likes_comment (user_id,comment_id,like_date,type) " +
                "VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, likeComment.getUser().getId());
            ps.setInt(2, likeComment.getComment().getId());
            ps.setObject(3, likeComment.getLikeDate());
            ps.setString(4, likeComment.getType());

            ps.executeUpdate();

            if (likeComment.getType().equals("l")) {
                System.out.println("Comment has been liked by " + likeComment.getComment().getUser().getUsername());
            } else {
                System.out.println("Comment has been disliked by " + likeComment.getComment().getUser().getUsername());
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    UserRepository userRepository = new UserRepository();
                    CommentRepository commentRepository = new CommentRepository();
                    return new LikeComment(
                            userRepository.findById(connection, rs.getInt("user_id")),
                            commentRepository.findById(connection, rs.getInt("comment_id")),
                            rs.getTimestamp("like_date").toLocalDateTime(),
                            rs.getString("type")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

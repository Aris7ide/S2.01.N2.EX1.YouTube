package com.repositories;

import com.models.Video;

import java.sql.*;

public class VideoRepository {
    public Video save(Connection connection, Video video) {
        String sql = "INSERT INTO video (title,description,size,name_file,thumbnail," +
                "state,user_id,channel_id,time_publication) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, video.getTitle());
            ps.setString(2, video.getDescription());
            ps.setDouble(3, video.getSize());
            ps.setString(4, video.getFileName());
            if (!video.getThumbnailUrl().isEmpty()) {
                ps.setString(5, video.getThumbnailUrl());
            } else {
                ps.setNull(5, java.sql.Types.NULL);
            }
            ps.setString(6, video.getState());
            ps.setInt(7, video.getUser().getId());
            if (video.getChannel() != null) {
                ps.setInt(8, video.getChannel().getId());
            } else {
                ps.setNull(8, java.sql.Types.NULL);
            }
            ps.setObject(9, video.getDate());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    video.setId(rs.getInt(1));
                }
            }

            System.out.println(video.getTitle() + " saved with id " + video.getId());
            return findById(connection, video.getId());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Video findById(Connection connection, int id) {
        String sql = "SELECT * FROM video WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs =  ps.executeQuery()) {
                if (rs.next()) {
                    UserRepository userRepository = new UserRepository();
                    ChannelRepository channelRepository = new ChannelRepository();

                    return new Video(
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDouble("size"),
                            rs.getString("name_file"),
                            rs.getString("thumbnail"),
                            rs.getString("state"),
                            userRepository.findById(connection,rs.getInt("user_id")),
                            channelRepository.findById(connection,rs.getInt("channel_id")),
                            rs.getDate("time_publication").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

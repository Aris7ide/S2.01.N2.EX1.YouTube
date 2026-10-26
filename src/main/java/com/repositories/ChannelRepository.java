package com.repositories;

import com.models.Channel;
import com.models.User;

import java.sql.*;

public class ChannelRepository {
    public Channel save(Connection connection, Channel channel) {
        String sql = "INSERT INTO channel(title,description,time_created,user_id) " +
                "VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, channel.getTitle());
            ps.setString(2, channel.getDescription());
            ps.setObject(3, channel.getTimeCreated());
            ps.setInt(4, channel.getUser().getId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    channel.setId(rs.getInt(1));
                }
            }
            System.out.println(channel.getTitle() + " saved with id " + channel.getId());
            return findById(connection, channel.getId());
        } catch (SQLException e) {
            System.err.println("Error while trying to save the channel " + e.getMessage());
        }
        return null;
    }

    public Channel findById(Connection connection, int id) {
        String sql = "SELECT * FROM channel WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserRepository userRepository = new UserRepository();
                    Channel channel = new Channel(
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDate("time_created").toLocalDate(),
                            userRepository.findById(connection, rs.getInt("user_id"))
                    );
                    return channel;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while trying to fetch the channel " + e.getMessage());
        }
        return null;
    }
}

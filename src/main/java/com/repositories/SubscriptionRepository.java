package com.repositories;

import com.models.Channel;
import com.models.Subscription;
import com.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionRepository {

    public Subscription save(Connection connection, Subscription subscription) {
        String sql = "INSERT INTO user_subscription (channel_id, user_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, subscription.getChannel().getId());
            ps.setInt(2, subscription.getUser().getId());

            ps.executeUpdate();

            return findById(connection,subscription.getChannel().getId(),subscription.getUser().getId());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Subscription findById(Connection connection, int channelId, int userId) {
        String sql = "SELECT * FROM user_subscription WHERE channel_id = ? AND user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, channelId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ChannelRepository channelRepository = new ChannelRepository();
                    UserRepository userRepository = new UserRepository();
                    return new Subscription(
                            channelRepository.findById(connection,channelId),
                            userRepository.findById(connection,userId)
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<Subscription> findByUser(Connection connection, User user) {
        List<Subscription> subscriptionList = new ArrayList<>();
        String sql = "SELECT * FROM user_subscription WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChannelRepository channelRepository = new ChannelRepository();
                    UserRepository userRepository = new UserRepository();
                    Subscription subscription = new Subscription(
                            channelRepository.findById(connection, rs.getInt("channel_id")),
                            userRepository.findById(connection, rs.getInt("user_id"))
                    );
                    subscriptionList.add(subscription);
                    return subscriptionList;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<Subscription> findByChannel(Connection connection, Channel channel) {
        List<Subscription> subscriptionList = new ArrayList<>();
        String sql = "SELECT * FROM user_subscription WHERE channel_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, channel.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChannelRepository channelRepository = new ChannelRepository();
                    UserRepository userRepository = new UserRepository();
                    Subscription subscription = new Subscription(
                            channelRepository.findById(connection, rs.getInt("channel_id")),
                            userRepository.findById(connection, rs.getInt("user_id"))
                    );
                    subscriptionList.add(subscription);
                    return subscriptionList;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

package com.repositories;

import com.models.Playlist;

import java.sql.*;

public class PlaylistRepository {
    public Playlist save(Connection connection, Playlist playlist) {
        String sql = "INSERT INTO playlist (name,creation_date,user_id,state) VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, playlist.getName());
            ps.setObject(2, playlist.getCreationDate());
            ps.setInt(3, playlist.getUser().getId());
            ps.setString(4, playlist.getState());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    playlist.setId(rs.getInt(1));
                }
            }
            System.out.println(playlist.getName() + " has been saved with id " + playlist.getId());
            return findById(connection, playlist.getId());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Playlist findById(Connection connection, int id) {
        String sql = "SELECT * FROM playlist WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserRepository userRepository = new UserRepository();
                    Playlist playlist = new Playlist(
                            rs.getString("name"),
                            rs.getDate("creation_date").toLocalDate(),
                            userRepository.findById(connection, rs.getInt("user_id")),
                            rs.getString("state")
                    );
                    playlist.setId(rs.getInt("id"));
                    return playlist;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

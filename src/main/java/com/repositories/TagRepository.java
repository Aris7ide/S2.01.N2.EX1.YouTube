package com.repositories;

import com.models.Tag;

import java.sql.*;

public class TagRepository {
    public Tag save(Connection connection, Tag tag) {
        String sql = "INSERT INTO tags (name) VALUES (?)";
        try  (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, tag.getTag());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    tag.setId(rs.getInt(1));
                }
            }

            System.out.println("tag " + tag.getTag() + " saved successfully");
            return findById(connection, tag.getId());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Tag findById(Connection connection, int id) {
        String sql = "SELECT * FROM tags WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tag tag = new Tag(rs.getString("name"));
                    tag.setId(rs.getInt("id"));
                    return tag;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

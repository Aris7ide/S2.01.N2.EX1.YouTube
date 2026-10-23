package com.repositories;

import com.models.Tag;
import com.models.User;

import java.sql.*;

public class UserRepository {

    public User save(Connection connection, User user) {
        String sql = "INSERT INTO user (email,password,username,birthdate,gender,country,postal_code)" +
                "VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getUsername());
            ps.setObject(4, user.getBirthday());

            if (user.getGender().isEmpty()) {
                ps.setNull(5, Types.VARCHAR);
            } else {
                ps.setString(5, user.getGender());
            }

            ps.setString(6, user.getCountry());
            ps.setObject(7, user.getPostalCode());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }

            System.out.println("Saved user with id: " + user.getId());
            return findById(connection, user.getId());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public User findById(Connection connection, int id) {
        String sql = "SELECT * FROM user WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("username"),
                            rs.getDate("birthdate").toLocalDate(),
                            rs.getString("gender"),
                            rs.getString("country"),
                            rs.getString("postal_code")
                    );
                    user.setId(id);
                    System.out.println("Found " + user.getUsername() + " with id n. " + user.getId());
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

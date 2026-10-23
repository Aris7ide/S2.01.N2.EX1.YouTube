package com.repositories;

import com.connection.DatabaseConnection;
import com.models.Tag;
import com.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TagRepositoryTest {

    private Connection connection;

    private TagRepository tagRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DatabaseConnection.getConnection();
        connection.setAutoCommit(false);

        tagRepository = new TagRepository();
        userRepository = new UserRepository();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    void shouldSaveTag(){
        Tag tag = new Tag(
                "news"
        );
        tagRepository.save(connection, tag);

        assertThat(tagRepository.findById(connection, tag.getId())).isNotNull();
        assertThat(tagRepository.findById(connection, tag.getId()).getTag()).isEqualTo("news");
    }

    @Test
    void shouldSaveUser(){
        User user = new User(
                "aris7ide@gmail.com",
                "password",
                "aris",
                LocalDate.of(1992,8,28),
                "m",
                "Italy",
                "80134"
        );
        userRepository.save(connection, user);

        assertThat(userRepository.findById(connection, user.getId())).isNotNull();
        assertThat(userRepository.findById(connection, user.getId()).getUsername()).isEqualTo("aris");
    }

}
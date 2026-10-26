package com.repositories;

import com.connection.DatabaseConnection;
import com.models.*;
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
    private ChannelRepository channelRepository;
    private VideoRepository videoRepository;
    private LikeVideoRepository likeVideoRepository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DatabaseConnection.getConnection();
        connection.setAutoCommit(false);

        tagRepository = new TagRepository();
        userRepository = new UserRepository();
        channelRepository = new ChannelRepository();
        videoRepository = new VideoRepository();
        likeVideoRepository = new LikeVideoRepository();
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

    @Test
    void shouldSaveChannel() {
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

        Channel channel = new Channel(
                "Casa Drassanes",
                "Your techno music",
                LocalDate.of(2025,8,12),
                user
        );

        channelRepository.save(connection, channel);

        assertThat(channelRepository.findById(connection, channel.getId())).isNotNull();
    }

    @Test
    void shouldSaveVideo() {
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

        Channel channel = new Channel(
                "Casa Drassanes",
                "Your techno music",
                LocalDate.of(2025,8,12),
                user
        );
        channelRepository.save(connection, channel);

        Video video = new Video(
                "Dj Set",
                "A new dj set",
                234.56,
                "this/is/the/path",
                "This/is/the/image",
                "public",
                user,
                channel,
                LocalDate.now()
        );
        videoRepository.save(connection, video);

        assertThat(videoRepository.findById(connection, video.getId())).isNotNull();
        assertThat(videoRepository.findById(connection, video.getId()).getSize()).isEqualTo(234.56);
    }

    @Test
    void shouldSaveLike()  {
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

        Channel channel = new Channel(
                "Casa Drassanes",
                "Your techno music",
                LocalDate.of(2025,8,12),
                user
        );
        channelRepository.save(connection, channel);

        Video video = new Video(
                "Dj Set",
                "A new dj set",
                234.56,
                "this/is/the/path",
                "This/is/the/image",
                "public",
                user,
                channel,
                LocalDate.now()
        );
        videoRepository.save(connection, video);

        LikeVideo likeVideo = new LikeVideo(user,video,"l");
        likeVideoRepository.like(connection, likeVideo,likeVideo.getLikeDislike());

        assertThat(likeVideoRepository.findByUserAndVideo(connection,user.getId(),video.getId())).isNotNull();
        assertThat(likeVideoRepository.findByUserAndVideo(connection,user.getId(),video.getId()).getLikeDislike()).isEqualTo("l");

        assertThat(likeVideoRepository.findByVideo(connection,video,"d").size()).isEqualTo(0);
        assertThat(likeVideoRepository.findByVideo(connection,video,"l").size()).isEqualTo(1);

        LikeVideo likeVideo2 = new LikeVideo(user,video,"d");
        likeVideoRepository.like(connection, likeVideo2,likeVideo2.getLikeDislike());

        assertThat(likeVideoRepository.findByUserAndVideo(connection,user.getId(),video.getId()).getLikeDislike()).isEqualTo("d");

        //Test findByVideo() and findByUser() to list likes per user and per video
        assertThat(likeVideoRepository.findByVideo(connection,video,"d").size()).isEqualTo(1);
        assertThat(likeVideoRepository.findByVideo(connection,video,"l").size()).isEqualTo(0);

        assertThat(likeVideoRepository.findByUser(connection,user,"d")).size().isEqualTo(1);

        assertThat(likeVideoRepository.findByUserAndVideo(connection,user.getId(),video.getId())).isNotNull();
    }



}
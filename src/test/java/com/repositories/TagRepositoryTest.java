package com.repositories;

import com.connection.DatabaseConnection;
import com.models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TagRepositoryTest {

    private Connection connection;

    private TagRepository tagRepository;
    private UserRepository userRepository;
    private ChannelRepository channelRepository;
    private VideoRepository videoRepository;
    private LikeVideoRepository likeVideoRepository;
    private SubscriptionRepository subscriptionRepository;
    private PlaylistRepository playlistRepository;
    private PlaylistVideoRepository playlistVideoRepository;
    private CommentRepository commentRepository;
    private LikeCommentRepository likeCommentRepository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DatabaseConnection.getConnection();
        connection.setAutoCommit(false);

        tagRepository = new TagRepository();
        userRepository = new UserRepository();
        channelRepository = new ChannelRepository();
        videoRepository = new VideoRepository();
        likeVideoRepository = new LikeVideoRepository();
        subscriptionRepository = new SubscriptionRepository();
        playlistRepository = new PlaylistRepository();
        playlistVideoRepository = new PlaylistVideoRepository();
        commentRepository = new CommentRepository();
        likeCommentRepository = new LikeCommentRepository();
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

    @Test
    void shouldSaveSubscription() {
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

        Subscription subscription = new Subscription(channel,user);
        subscriptionRepository.save(connection, subscription);

        assertThat(subscriptionRepository.findById(connection, channel.getId(),user.getId())).isNotNull();

        //test findByUser() and findByChannel
        assertThat(subscriptionRepository.findByChannel(connection,channel).size()).isEqualTo(1);
        assertThat(subscriptionRepository.findByUser(connection, user).size()).isEqualTo(1);
    }

    @Test
    void shouldSavePlaylist() {
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

        Playlist playlist = new Playlist(
                "My videos",
                LocalDate.now(),
                user,
                "public"
        );
        playlistRepository.save(connection, playlist);

        assertThat(playlistRepository.findById(connection, playlist.getId())).isNotNull();
    }

    @Test
    void shouldSaveVideoInPlaylist() {
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

        Playlist playlist = new Playlist(
                "My videos",
                LocalDate.now(),
                user,
                "public"
        );
        playlistRepository.save(connection, playlist);

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

        Video video2 = new Video(
                "Cooking tutorial",
                "Learn how to cook",
                2345.76,
                "this/is/the/name",
                "",
                "public",
                user,
                null,
                LocalDate.now()
        );
        videoRepository.save(connection, video2);

        PlaylistVideo playlistVideo = new PlaylistVideo(playlist,video);
        playlistVideoRepository.save(connection, playlistVideo);
        PlaylistVideo playlistVideo2 = new PlaylistVideo(playlist,video2);
        playlistVideoRepository.save(connection, playlistVideo2);

        assertThat(playlistVideoRepository.findById(connection, playlistVideo.getPlaylist().getId(), playlistVideo.getVideo().getId())).isNotNull();

        assertThat(playlistVideoRepository.findByPlaylist(connection, playlist.getId())).size().isEqualTo(2);
    }

    @Test
    void shouldSaveComment() {
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

        Comment comment = new Comment(
                "Video is nice",
                LocalDateTime.now(),
                user,
                video
        );
        commentRepository.save(connection, comment);

        assertThat(commentRepository.findById(connection, comment.getId())).isNotNull();
    }

    @Test
    void shouldLikeComment() {
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

        Comment comment = new Comment(
                "Video is nice",
                LocalDateTime.now(),
                user,
                video
        );
        commentRepository.save(connection, comment);

        LikeComment likeComment = new LikeComment(user, comment, LocalDateTime.now(), "l");
        likeCommentRepository.save(connection, likeComment);
    }
}
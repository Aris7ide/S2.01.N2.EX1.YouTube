DROP DATABASE IF EXISTS youtube;
CREATE DATABASE youtube CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE youtube;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    username VARCHAR(30) NOT NULL,
    birthdate DATE NOT NULL,
    gender ENUM('m','f','nb'),
    country VARCHAR(30) NOT NULL,
    postal_code VARCHAR(30) NOT NULL
);

CREATE TABLE channel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(60) NOT NULL,
    description VARCHAR(200) NOT NULL,
    time_created DATE NOT NULL,
    user_id INT NOT NULL,

                     CONSTRAINT fk_channel_user
                     FOREIGN KEY (user_id)
                     REFERENCES user(id)
);

CREATE TABLE video (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(60) NOT NULL,
                       description VARCHAR(200) NOT NULL,
                       size DECIMAL (10,4) NOT NULL,
                       name_file VARCHAR(120) NOT NULL,
                       thumbnail VARCHAR(120),
                       state ENUM('public', 'hidden', 'private') NOT NULL,
                       user_id INT NOT NULL,
                       channel_id INT,
                       time_publication DATE NOT NULL,

                        CONSTRAINT fk_video_user
                        FOREIGN KEY (user_id)
                        REFERENCES user(id),

                        CONSTRAINT fk_video_channel
                        FOREIGN KEY (channel_id)
                        REFERENCES channel(id)
);

CREATE TABLE tags (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(15) NOT NULL
);

CREATE TABLE video_tags (
                            tag_id INT NOT NULL,
                            video_id INT NOT NULL,
                            PRIMARY KEY (tag_id, video_id),

                            CONSTRAINT fk_v_tag
                                FOREIGN KEY (tag_id)
                                    REFERENCES tags(id),

                            CONSTRAINT tk_v_video
                                FOREIGN KEY (video_id)
                                    REFERENCES video(id)
);

CREATE TABLE likes_video (
    user_id INT NOT NULL,
    video_id INT NOT NULL,
    type ENUM('l','d') NOT NULL,
    PRIMARY KEY (user_id,video_id),

    CONSTRAINT fk_likes_user_video
                         FOREIGN KEY (user_id)
                         REFERENCES user(id),

    CONSTRAINT fk_likes_video_video
                         FOREIGN KEY (video_id)
                         REFERENCES video(id)
);

CREATE TABLE user_subscription (
    channel_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (channel_id, user_id),

                                CONSTRAINT fk_sub_channel
                                FOREIGN KEY (channel_id)
                                REFERENCES channel(id),

                                CONSTRAINT fk_sub_user
                                FOREIGN KEY (user_id)
                                REFERENCES user(id)
);

CREATE TABLE playlist (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    creation_date DATE NOT NULL,
    user_id INT NOT NULL,
    state ENUM('public','private') NOT NULL,

    CONSTRAINT fk_playlist_user
                      FOREIGN KEY (user_id)
                      REFERENCES user(id)
);

CREATE TABLE playlist_video (
    playlist_id INT NOT NULL,
    video_id INT NOT NULL,
    PRIMARY KEY (playlist_id,video_id),

    CONSTRAINT fk_user_playlist
                           FOREIGN KEY (playlist_id)
                           REFERENCES playlist(id),
    CONSTRAINT fk_video_playlist
                           FOREIGN KEY (video_id)
                           REFERENCES video(id)
);

CREATE TABLE comments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    text VARCHAR(200) NOT NULL,
    date DATE NOT NULL,
    user_id INT NOT NULL,
    video_id INT NOT NULL,

    CONSTRAINT fk_comment_user
                      FOREIGN KEY (user_id)
                      REFERENCES user(id),
    CONSTRAINT fk_comment_video
                      FOREIGN KEY (video_id)
                      REFERENCES video(id)
);

CREATE TABLE likes_comment (
    user_id INT NOT NULL,
    comment_id INT NOT NULL,
    like_date DATE NOT NULL,
    type ENUM('l','d') NOT NULL,
    PRIMARY KEY (user_id,comment_id),

    CONSTRAINT fk_like_comment_user
                           FOREIGN KEY (user_id)
                           REFERENCES user(id),
    CONSTRAINT fk_like_comment_video
                           FOREIGN KEY (comment_id)
                           REFERENCES comments(id)
);

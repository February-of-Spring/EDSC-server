CREATE TABLE category
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    description VARCHAR(255),
    name        VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE comment
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    content     VARCHAR(255),
    is_public   BIT,
    like_count  INTEGER,
    created_at  TIMESTAMP,
    modified_at TIMESTAMP,
    parent_id   BIGINT,
    post_id     BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE file
(
    id      BIGINT NOT NULL AUTO_INCREMENT,
    path    VARCHAR(255),
    post_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE liked
(
    id      BIGINT NOT NULL AUTO_INCREMENT,
    post_id BIGINT,
    user_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE post
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    content     VARCHAR(255),
    created_at  TIMESTAMP,
    like_count  INTEGER,
    modified_at TIMESTAMP,
    title       VARCHAR(255),
    view_count  INTEGER,
    category_id BIGINT,
    user_id     BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE user
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    email         VARCHAR(255),
    name          VARCHAR(255),
    nickname      VARCHAR(255),
    password      VARCHAR(255),
    phone         VARCHAR(255),
    profile_image VARCHAR(255),
    PRIMARY KEY (id)
);
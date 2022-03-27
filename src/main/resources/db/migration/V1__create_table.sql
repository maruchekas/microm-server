CREATE TABLE messages
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    created_time datetime              NULL,
    text         VARCHAR(255)          NULL,
    CONSTRAINT pk_messages PRIMARY KEY (id)
);



CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    email      VARCHAR(255)          NULL,
    password   VARCHAR(255)          NULL,
    user_type  VARCHAR(255)          NULL,
    is_blocked INT                   NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);
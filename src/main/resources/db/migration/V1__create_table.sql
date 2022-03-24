CREATE TABLE messages
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    created_time datetime              NULL,
    text         VARCHAR(255)          NULL,
    CONSTRAINT pk_messages PRIMARY KEY (id)
);


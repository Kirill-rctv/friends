CREATE TABLE users (
    id                  BIGSERIAL       PRIMARY KEY,
    name                VARCHAR(255)    NOT NULL
);

CREATE TABLE friendships (
    user_id             BIGINT          NOT NULL,
    friend_id           BIGINT          NOT NULL,
    PRIMARY KEY (user_id, friend_id)
);
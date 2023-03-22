CREATE TABLE users (
    id                  BIGSERIAL       PRIMARY KEY,
    name                VARCHAR(255)    NOT NULL
);
INSERT INTO users VALUES (DEFAULT, 'first') ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (DEFAULT, 'second') ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (DEFAULT, 'third') ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (DEFAULT, 'fourth') ON CONFLICT DO NOTHING;

CREATE TABLE friendships (
    user_id             BIGINT          NOT NULL,
    friend_id           BIGINT          NOT NULL,
    PRIMARY KEY (user_id, friend_id)
);
INSERT INTO friendships VALUES ('1', '2') ON CONFLICT DO NOTHING;
INSERT INTO friendships VALUES ('1', '3') ON CONFLICT DO NOTHING;
INSERT INTO friendships VALUES ('1', '4') ON CONFLICT DO NOTHING;
CREATE SEQUENCE seq_users INCREMENT 1 START 1;

CREATE TABLE "user"
(
    id         BIGINT                      NOT NULL DEFAULT nextval('seq_users'::regclass),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    name       VARCHAR(120)                NOT NULL,
    email      VARCHAR(120)                 NOT NULL,
    username   VARCHAR(50)                 NOT NULL,
    password   VARCHAR(120)                 NOT NULL,
    role       VARCHAR(50)                 NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT uk_users_username UNIQUE (username)
);


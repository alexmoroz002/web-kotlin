create table if not exists users (
    spotify_id varchar(50) primary key not null,
    user_name varchar(50),
    country char(2),
    is_explicit_filtered boolean,
    avatar_url varchar(100),
    product varchar(20)
);

create table if not exists tracks(
    id varchar(50) primary key not null
);

create table if not exists playlists(
    id varchar(50) primary key not null,
    name varchar(50) not null,
    description varchar(100),
    date date not null,
    user_id varchar(50) not null
);

create table if not exists playlists_tracks
(
    playlist_id varchar(50)  not null
        constraint playlists_tracks_playlists_id_fk
            references playlists
            on update cascade on delete cascade,
    track_id    varchar(100) not null
        constraint playlists_tracks_tracks_id_fk
            references tracks
            on update cascade on delete cascade,
    constraint playlists_tracks_pk
        primary key (playlist_id, track_id)
);

CREATE TABLE if not exists oauth2_authorized_client (
    client_registration_id varchar(100) NOT NULL,
    principal_name varchar(200) NOT NULL,
    access_token_type varchar(100) NOT NULL,
    access_token_value bytea NOT NULL,
    access_token_issued_at timestamp NOT NULL,
    access_token_expires_at timestamp NOT NULL,
    access_token_scopes varchar(1000) DEFAULT NULL,
    refresh_token_value bytea DEFAULT NULL,
    refresh_token_issued_at timestamp DEFAULT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (client_registration_id, principal_name)
);

CREATE TABLE if not exists SPRING_SESSION (
    PRIMARY_ID CHAR(36) NOT NULL,
    SESSION_ID CHAR(36) NOT NULL,
    CREATION_TIME BIGINT NOT NULL,
    LAST_ACCESS_TIME BIGINT NOT NULL,
    MAX_INACTIVE_INTERVAL INT NOT NULL,
    EXPIRY_TIME BIGINT NOT NULL,
    PRINCIPAL_NAME VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX if not exists SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX if not exists SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX if not exists SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE if not exists SPRING_SESSION_ATTRIBUTES (
    SESSION_PRIMARY_ID CHAR(36) NOT NULL,
    ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES BYTEA NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID)
        REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);

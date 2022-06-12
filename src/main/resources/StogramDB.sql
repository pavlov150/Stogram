drop table if exists likes;
drop table if exists post_tag;
drop table if exists tag;
drop table if exists comment;
drop table if exists post;
drop table if exists user_role;
drop table if exists role;
drop table if exists users;
drop table if exists subscriptions;
drop table if exists bugs;

CREATE TABLE users (
    user_id bigserial PRIMARY KEY,
    username varchar(150) not null UNIQUE,
    password varchar(150) not null,
    surname varchar(150),
    firstname varchar(150),
    photo varchar(50),
    info varchar(255),
    www varchar(150),
    email varchar(50),
    phone varchar(20),
    sex varchar(7),
    created_at timestamp not null,
    is_active boolean);

CREATE TABLE role (
    role_id int PRIMARY KEY,
    name varchar(50) not null);

CREATE TABLE user_role (
    user_id bigint REFERENCES users (user_id),
    role_id int REFERENCES role(role_id),
    PRIMARY KEY (user_id, role_id));

CREATE TABLE subscriptions (
    sub_id bigserial PRIMARY KEY,
    user_id bigint REFERENCES users(user_id),
    user_sub_id bigint REFERENCES users(user_id));

CREATE TABLE likes (
    like_id bigserial PRIMARY KEY,
    user_id bigint REFERENCES users(user_id),
    post_id bigint REFERENCES post(post_id));

CREATE TABLE post (
    post_id bigserial PRIMARY KEY,
    photo varchar(15),
    extention varchar(5),
    content text NOT NULL,
    user_id bigint REFERENCES users(user_id),
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone);

CREATE TABLE comment (
    comment_id bigserial PRIMARY KEY,
    post_id bigint REFERENCES post(post_id) ON DELETE CASCADE,
    user_id bigint REFERENCES users(user_id),
    comment_text text,
    created_at timestamp without time zone,
    updated_at timestamp without time zone);

CREATE TABLE bugs (
    bug_id bigserial PRIMARY KEY,
    user_id bigint REFERENCES users(user_id),
    username varchar(150),
    bug_text text,
    created_at timestamp without time zone);

insert into role values (1, 'ADMIN');
insert into role values (2, 'USER');

insert into users (username, password, created_at, is_active) values ('admin', '$2a$10$nzYRhy8lWbVTxvr7xnZFqu8BLBP0pNQaTU1hslTl0xoR6yA2CgsbC', now()::timestamp, true);
insert into users (username, password, created_at, is_active) values ('pavlov89312', '$2a$10$nzYRhy8lWbVTxvr7xnZFqu8BLBP0pNQaTU1hslTl0xoR6yA2CgsbC', now()::timestamp, true);
insert into users (username, password, created_at, is_active) values ('user1', '$2a$10$nzYRhy8lWbVTxvr7xnZFqu8BLBP0pNQaTU1hslTl0xoR6yA2CgsbC', now()::timestamp, true);

insert into user_role values (1, 1);
insert into user_role values (2, 2);

insert into subscriptions (user_id, user_sub_id) values (4, 11);
insert into subscriptions (user_id, user_sub_id) values (4, 2);

insert into post (photo, content, user_id, created_at, updated_at) values ('test.jpg', 'It''s all good!', 2, '2020-12-12 16:10:23'::timestamp, null);
insert into post (photo, content, user_id, created_at, updated_at) values ('test.jpg', 'It''s all ok!', 3, '2022-12-12 16:10:23'::timestamp, null);
insert into post (photo, content, user_id, created_at, updated_at) values ('test2.jpg', 'It''s all bad!', 2, '2020-12-12 16:10:23'::timestamp, null);

insert into comment (post_id, comment_text, created_at) values (2, 'Nice!', current_timestamp);
insert into comment (post_id, comment_text, created_at) values (1, 'Awesome!', current_timestamp);
insert into comment (post_id, comment_text, created_at) values (1, 'Excellent!', current_timestamp);
insert into comment (post_id, comment_text, created_at) values (1, 'Wonderful!', current_timestamp);
insert into comment (post_id, comment_text, created_at) values (3, 'Disgusting!', current_timestamp);
insert into comment (post_id, comment_text, created_at) values (3, 'Atrocious!', current_timestamp);
drop table if exists post_tag;
drop table if exists tag;
drop table if exists comment;
drop table if exists post;
drop table if exists user_role;
drop table if exists role;
drop table if exists users;

CREATE TABLE users (
  user_id bigserial PRIMARY KEY,
  username varchar(50) not null,
  password varchar(50) not null,
  created_at timestamp not null,
  is_active boolean
);

CREATE TABLE role (
  role_id int PRIMARY KEY,
  name varchar(50) not null
);

CREATE TABLE user_role (
  user_id bigint REFERENCES users (user_id),
  role_id int REFERENCES role(role_id),
  PRIMARY KEY (user_id, role_id)
);


CREATE TABLE post (
  post_id bigserial PRIMARY KEY,
  title varchar(100) NOT NULL,
  content text NOT NULL,
  user_id bigint REFERENCES users(user_id),
  created_at timestamp without time zone NOT NULL,
  updated_at timestamp without time zone
);

CREATE TABLE comment (
  comment_id bigserial PRIMARY KEY,
  post_id bigint REFERENCES post(post_id) ON DELETE CASCADE,
  content text,
  created_at timestamp without time zone,
  updated_at timestamp without time zone
);

CREATE TABLE tag (
  tag_id bigserial PRIMARY KEY,
  name varchar(50) not null
);


CREATE TABLE post_tag (
  post_id bigint REFERENCES post(post_id) ON DELETE CASCADE,
  tag_id bigint REFERENCES tag(tag_id),
  PRIMARY KEY (post_id, tag_id)
);

insert into role values (1, 'ADMIN');
insert into role values (2, 'USER');

insert into users (username, password, created_at, is_active)
    values ('admin', 'admin', now()::timestamp, true);

insert into users (username, password, created_at, is_active)
    values ('user1', 'user1', now()::timestamp, true);

insert into user_role values (1, 1);
insert into user_role values (2, 2);


insert into post (title, content, user_id, created_at, updated_at)
	values ('Day 1', 'It''s all good!', 2, current_timestamp - interval '2 days', null);
insert into post (title, content, user_id, created_at, updated_at)
	values ('Day 2', 'It''s all ok!', 2, current_timestamp - interval '1 days', null);
insert into post (title, content, user_id, created_at, updated_at)
	values ('Day 3', 'It''s all bad!', 2, current_timestamp, null);

insert into comment (post_id, content, created_at)
    values (2, 'Nice!', current_timestamp);
insert into comment (post_id, content, created_at)
    values (1, 'Awesome!', current_timestamp);
insert into comment (post_id, content, created_at)
    values (1, 'Excellent!', current_timestamp);
insert into comment (post_id, content, created_at)
    values (1, 'Wonderful!', current_timestamp);
insert into comment (post_id, content, created_at)
    values (3, 'Disgusting!', current_timestamp);
insert into comment (post_id, content, created_at)
    values (3, 'Atrocious!', current_timestamp);

insert into tag (name) values ('news');
insert into tag (name) values ('life');
insert into tag (name) values ('day');
insert into tag (name) values ('mood');
insert into tag (name) values ('ideas');
insert into tag (name) values ('thoughts');

insert into post_tag(post_id, tag_id) values (1, 1);
insert into post_tag(post_id, tag_id) values (1, 2);
insert into post_tag(post_id, tag_id) values (2, 3);
insert into post_tag(post_id, tag_id) values (2, 2);
insert into post_tag(post_id, tag_id) values (2, 1);
insert into post_tag(post_id, tag_id) values (2,5);
insert into post_tag(post_id, tag_id) values (3, 3);
insert into post_tag(post_id, tag_id) values (3, 2);
insert into post_tag(post_id, tag_id) values (3, 6);

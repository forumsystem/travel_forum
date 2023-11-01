create or replace table users
(
    user_id    int auto_increment
        primary key,
    first_name varchar(32) not null,
    last_name  varchar(32) not null,
    email      varchar(64) not null,
    username   varchar(32) not null,
    password   varchar(32) not null,
    is_blocked tinyint(1)  not null,
    is_admin   tinyint(1)  not null,
    constraint users_pk2
        unique (email)
);

create or replace table phone_numbers
(
    phone_number_id int auto_increment
        primary key,
    phone_number    varchar(32) not null,
    user_id         int         not null,
    constraint phone_numbers_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create or replace table posts
(
    post_id    int auto_increment
        primary key,
    title      varchar(64)   not null,
    content    varchar(8192) not null,
    user_id    int           null,
    time_stamp timestamp     not null,
    constraint posts_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create or replace table comments
(
    comment_id int auto_increment
        primary key,
    comment    varchar(4000) not null,
    post_id    int           null,
    user_id    int           null,
    constraint comments_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint comments_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create or replace table likes
(
    like_id int auto_increment
        primary key,
    post_id int not null,
    user_id int not null,
    constraint likes_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint likes_users_user_id_fk
        foreign key (user_id) references users (user_id)
);


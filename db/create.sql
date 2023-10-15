create table users
(
    user_id    int auto_increment
        primary key,
    first_name varchar(32) not null,
    last_name  varchar(32) not null,
    email      varchar(64) not null,
    username   varchar(32) not null,
    password   varchar(32) not null,
    constraint users_pk2
        unique (email)
);

create table admins
(
    admin_id int auto_increment
        primary key,
    user_id  int null,
    constraint admins_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table blocked_users
(
    blocked_id int auto_increment
        primary key,
    user_id    int null,
    admin_id   int null,
    constraint blocked_users_admins_admin_id_fk
        foreign key (admin_id) references admins (admin_id),
    constraint blocked_users_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table phone_numbers
(
    phone_number_id int auto_increment
        primary key,
    phone_number    varchar(32) not null,
    admin_id        int         null,
    constraint phone_numbers_admins_admin_id_fk
        foreign key (admin_id) references admins (admin_id)
);

create table posts
(
    post_id int auto_increment
        primary key,
    title   varchar(64)   not null,
    content varchar(8192) not null,
    user_id int           null,
    constraint posts_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table comments
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

create table profile_photos
(
    photo_id   int auto_increment
        primary key,
    photo_name varchar(32) not null,
    photo_data blob        not null,
    user_id    int         null,
    constraint profile_photos_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table votes
(
    vote_id   int auto_increment
        primary key,
    vote_type tinyint(1) not null,
    post_id   int        null,
    user_id   int        null,
    constraint votes_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint votes_users_user_id_fk
        foreign key (user_id) references users (user_id)
);



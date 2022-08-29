create table if not exists blocked_jwt_token
(
    id    int auto_increment
        primary key,
    token varchar(512) not null,
    constraint blocked_jwt_token_token_uindex
        unique (token)
);

create table if not exists user
(
    id         int auto_increment
        primary key,
    email      varchar(50)                          not null,
    username   varchar(30)                          not null,
    password   varchar(256)                         not null,
    active     tinyint(1) default 0                 not null,
    created_at timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint user_email_uindex
        unique (email),
    constraint user_username_uindex
        unique (username)
);

create table if not exists verification_token
(
    id      int auto_increment
        primary key,
    user_id int                                 not null,
    token   varchar(256)                        not null,
    expiry  timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint email_verification_token_token_uindex
        unique (token),
    constraint email_verification_token_user_id_fk
        foreign key (user_id) references user (id)
);

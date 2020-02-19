create schema base_users collate utf8mb4_unicode_ci;
use base_users;

create table user
(
    id int auto_increment primary key,
    cdt datetime null,
    date_of_birth date null,
    email varchar(255) null,
    mdt datetime null,
    name varchar(255) null,
    password varchar(255) null,
    constraint unique_user_email unique (email),
    constraint unique_user_name unique (name)
)
engine=InnoDB;
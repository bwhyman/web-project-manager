create database if not exists `web-project`;

create table if not exists `web-project`.user
(
    id            int auto_increment primary key,
    name          varchar(45)   not null,
    number        varchar(45)   not null,
    password      varchar(45)   not null,
    clazz         varchar(45)   null,
    role          int           not null,
    photo         mediumtext    null,
    repositoryurl varchar(255)  null,
    index (number)
);

create table if not exists `web-project`.project
(
    id          int auto_increment primary key,
    `index`     varchar(45)                         null,
    selfaddress varchar(255)                        null,
    updatetime  timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    user_id     int unsigned                        null,
    index (user_id)
);





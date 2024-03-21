create schema if not exists simplechatdbschema;
create table if not exists simplechatdbschema.users
(
    id       bigserial primary key,
    username     varchar(100) not null,
    fullName    varchar(255) not null,
    password varchar(255) not null
);

create table if not exists simplechatdbschema.chats
(
    id             bigserial       primary key,
    sender_user    varchar(100)    not null,
    content        varchar(500)    null,
    timestamp      timestamp       not null
);

create table if not exists simplechatdbschema.users_chats
(
    user_id bigint not null,
    chat_id bigint not null,
    primary key (user_id, chat_id),
    constraint fk_users_events_users foreign key (user_id) references users (id) on delete cascade on update no action,
    constraint fk_users_events_events foreign key (chat_id) references chats (id) on delete cascade on update no action
);


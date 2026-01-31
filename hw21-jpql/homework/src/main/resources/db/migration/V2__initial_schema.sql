create sequence address_SEQ start with 1 increment by 1;
create table address
(
    id          bigint not null primary key,
    client_id   bigint,
    street      varchar(50)
);

create sequence phone_SEQ start with 1 increment by 1;
create table phone
(
    id          bigint not null primary key,
    client_id   bigint,
    number      varchar(50)
);
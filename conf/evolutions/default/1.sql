# user schema

# --- !Ups

create table user
(
  id      int(10)      not null auto_increment,
  name    varchar(255) not null,
  age     int(3)       not null,
  deleted datetime default null,
  primary key (id)
);


create table userTest
(
  id         bigint not null auto_increment primary key,
  first_name text not null,
  last_name  text not null,
  mobile     bigint not null,
  email      text not null
);

# --- !Downs

drop table user;
drop table userTest;
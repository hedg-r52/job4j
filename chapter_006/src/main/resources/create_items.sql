create table items
(
  id serial primary key,
  name varchar(100) not null,
  "desc" varchar(300) not null,
  created bigint
)
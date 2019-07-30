create table countries(
                          id serial primary key,
                          title varchar(50)
);
create table cities(
                       id serial primary key,
                       title varchar(50),
                       countries_id integer references countries(id)
);
insert into countries (title) values
('Russia'),
('Germany'),
('United Kingdom'),
('Italy');
insert into cities(title, countries_id) values
('Moscow', 1),
('Saint-Petersburg', 1),
('Nižnij Novgorod', 1),
('Kazan', 1),
('Berlin', 2),
('Hamburg', 2),
('London', 3),
('Liverpool', 3),
('Rome', 4),
('Milan', 4);
create table users (
                       id serial primary key,
                       name varchar(50),
                       login varchar(50) unique not null,
                       email varchar(50),
                       password varchar(50),
                       role varchar(20),
                       created timestamp,
                       country integer references countries(id),
                       city integer references cities(id)
);
insert into users (name, login, email, password, role, created, country, city) values ('root', 'root', 'root@nomail.ru', 'root', 'administrator', now(), 1, 1);
insert into users (name, login, email, password, role, created, country, city) values ('bob', 'bob', 'bob@nomail.ru', 'bob', 'user', now(), 1, 1);
insert into users (name, login, email, password, role, created, country, city) values ('silvia', 'silvia', 'silvia@nomail.ru', 'silvia', 'user', now(), 1, 1);
insert into users (name, login, email, password, role, created, country, city) values ('covax', 'covax', 'covax@nomail.ru', 'covax', 'user', now(), 1, 1);

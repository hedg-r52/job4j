create table halls (
    id serial primary key,
    row int not null,
    place int not null,
    sold boolean default false,
    price int not null
);
create table accounts (
    id serial primary key,
    username varchar(100) not null,
    phone varchar(20) not null,
    halls_id integer references halls(id)
);
insert into halls(row, place, price) values
(1, 1, 500), (1, 2, 500), (1, 3, 500), (1, 4, 500), (1, 5, 500),
(2, 1, 500), (2, 2, 500), (2, 3, 500), (2, 4, 500), (2, 5, 500),
(3, 1, 500), (3, 2, 500), (3, 3, 500), (3, 4, 500), (3, 5, 500),
(4, 1, 450), (4, 2, 450), (4, 3, 450), (4, 4, 450), (4, 5, 450),
(5, 1, 450), (5, 2, 450), (5, 3, 450), (5, 4, 450), (5, 5, 450);

-- Кузов(body)
create table body
(
	id serial primary key,
	name varchar(100)
);

-- Двигатель(engine)
create table engine
(
	id serial primary key,
	name varchar(100)
);

-- Коробка передач(transmission)
create table transmission
(
	id serial primary key,
	name varchar(100)
);

-- Машины(Automobiles)
create table auto
(
	id serial primary key,
	name varchar(100),
	body_id int references body(id),
	engine_id int references engine(id),
	transmission_id int references transmission(id)
);

insert into body values (1, 'sedan');
insert into body values (2, 'hatchback');
insert into body values (3, 'SUV');

insert into engine values (1, 'petrol engine');
insert into engine values (2, 'diesel engine');
insert into engine values (3, 'electro engine');

insert into transmission values (1, 'AT');
insert into transmission values (2, 'MT');
insert into transmission values (3, 'CVT');
insert into transmission values (4, 'none');

insert into auto values (1, 'Ford Focus', 1, 1, 1);
insert into auto values (1, 'Kia Rio New', 2, 1, 2);
insert into auto values (1, 'Toyota Land Cruiser Prado', 3, 2, 3);
insert into auto values (1, 'Tesla Model S', 1, 3, 4);

-- Все автомобили с "комплектующими"
SELECT 
	a.name AS Autos, 
	b.name AS Body, 
	e.name AS Engine, 
	t.name AS Transmission 
FROM auto AS a 
	LEFT JOIN body AS b ON a.body_id = b.id
	LEFT JOIN engine AS e ON a.engine_id = e.id
	LEFT JOIN transmission AS t ON a.transmission_id = t.id
	
-- Комплектующие не используемые в автомобиле
SELECT b.name, 'body' as "Type"
	FROM body as b
	LEFT JOIN auto AS a ON b.id = a.body_id
	WHERE a.name IS NOT NULL
UNION
SELECT e.name, 'engine'
	FROM engine as e
	LEFT JOIN auto AS a ON e.id = a.engine_id
	WHERE a.name IS NOT NULL
UNION
SELECT t.name, 'transmission'
	FROM transmission as t
	LEFT JOIN auto AS a ON t.id = a.transmission_id
	WHERE a.name IS NOT NULL
ORDER BY "Type"; 

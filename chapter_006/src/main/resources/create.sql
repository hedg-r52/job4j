create database tracker;

-- Create tables

create table role
(
	id serial primary key,
	name varchar(100)
);

create table users
(
	id serial primary key,
	name varchar(100),
	role_id integer references role(id)
);

create table rules
(
	id serial primary key,
	name varchar(100)
);

create table role_rules
(
	role_id integer references role(id),
	rule_id integer references rules(id),
	primary key (role_id, rule_id)
);

create table state
(
	id serial primary key,
	name varchar(100)
);

create table category
(
	id serial primary key,
	name varchar(100)
);

create table item
(
	id integer primary key references users(id),
	name varchar(100),
	state_id integer references state(id),
	category_id integer references category(id)
);

create table attaches
(
	id serial primary key,
	name varchar(100),
	item_id integer references item(id)
);

create table comments
(
	id serial primary key,
	name varchar(200),
	item_id integer references item(id)
);

-- Insert data

-- state
INSERT INTO state VALUES ( 1, 'state1' );
INSERT INTO state VALUES ( 2, 'state2' );
INSERT INTO state VALUES ( 3, 'state3' );

-- category
INSERT INTO category VALUES ( 1, 'category1' );
INSERT INTO category VALUES ( 2, 'category2' );
INSERT INTO category VALUES ( 3, 'category3' );

-- rules
INSERT INTO rules VALUES ( 1, 'rule1' );
INSERT INTO rules VALUES ( 2, 'rule2' );
INSERT INTO rules VALUES ( 3, 'rule3' );

-- role
INSERT INTO role VALUES ( 1, 'roe1' );
INSERT INTO role VALUES ( 2, 'role2' );
INSERT INTO role VALUES ( 3, 'role3' );

-- role_rules
INSERT INTO role_rules VALUES ( 1, 1 );
INSERT INTO role_rules VALUES ( 2, 2 );
INSERT INTO role_rules VALUES ( 3, 3 );

-- users
INSERT INTO users VALUES (1, 'Иванов', 1);
INSERT INTO users VALUES (2, 'Петров', 2);
INSERT INTO users VALUES (3, 'Сидоров', 3);

-- item
INSERT INTO item VALUES ( 1, 'Item1', 1, 2 );
INSERT INTO item VALUES ( 2, 'Item2', 2, 3 );
INSERT INTO item VALUES ( 3, 'Item3', 3, 1 );

-- attaches
INSERT INTO attaches VALUES ( 1, 'Attach1_1', 1 );
INSERT INTO attaches VALUES ( 2, 'Attach1_2', 1 );
INSERT INTO attaches VALUES ( 3, 'Attach2_1', 2 );
INSERT INTO attaches VALUES ( 4, 'Attach2_2', 2 );
INSERT INTO attaches VALUES ( 5, 'Attach3_1', 3 );
INSERT INTO attaches VALUES ( 6, 'Attach3_2', 3 );

-- comments
INSERT INTO comments VALUES ( 1, 'Comment1_1', 1 );
INSERT INTO comments VALUES ( 2, 'Comment1_2', 1 );
INSERT INTO comments VALUES ( 3, 'Comment2_1', 2 );
INSERT INTO comments VALUES ( 4, 'Comment2_2', 2 );
INSERT INTO comments VALUES ( 5, 'Comment3_1', 3 );
INSERT INTO comments VALUES ( 6, 'Comment3_2', 3 );


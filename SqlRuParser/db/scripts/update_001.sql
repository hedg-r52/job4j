CREATE TABLE if NOT EXISTS vacancies (
    href char(300) PRIMARY KEY NOT NULL,
    datetime TIMESTAMP,
    author varchar(100),
    title varchar(300),
    msg text
);
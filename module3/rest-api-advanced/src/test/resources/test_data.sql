DROP TABLE IF EXISTS Gift_certificate_Tag;
DROP TABLE IF EXISTS orders_gift_certificate;
DROP TABLE IF EXISTS Tag;
DROP TABLE IF EXISTS Gift_certificate;
DROP TABLE IF EXISTS Users_orders;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Users;

CREATE TABLE Gift_certificate
(
    id               int generated by default as identity primary key,
    name             varchar(100) not null,
    description      varchar,
    price            DECIMAL(8, 2) CHECK ( price > 10 ),
    duration         int CHECK ( duration > 0 ),
    create_date      timestamp,
    last_update_date timestamp
);

CREATE TABLE Tag
(
    id   int generated by default as identity primary key,
    name varchar(100) not null unique
);

CREATE TABLE Gift_certificate_Tag
(
    gift_certificate_id int references Gift_certificate (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    tag_id              int references Tag (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    primary key (gift_certificate_id, tag_id)
);

CREATE TABLE Users
(
    id        int generated by default as identity primary key,
    name      varchar(100) not null,
    last_name varchar(100) not null,
    email     varchar(320) not null unique
);

CREATE TABLE Orders
(
    id          int generated by default as identity primary key,
    cost        DECIMAL(10, 2) CHECK ( cost > 0 ),
    create_date timestamp,
    user_id     int references Users (id)
);

CREATE TABLE orders_gift_certificate
(
    orders_id           int references Orders (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    gift_certificate_id int references Gift_certificate (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    primary key (orders_id, gift_certificate_id),
    count int
);

INSERT INTO Gift_certificate
values (DEFAULT, '100$', 'anything gift card', 131.5, 8, current_timestamp, current_timestamp),
       (DEFAULT, '200$', 'anything gift card', 255.3, 12, current_timestamp, current_timestamp),
       (DEFAULT, '200$', 'only for toys shops card', 255.3, 11, current_timestamp, current_timestamp),
       (DEFAULT, '50$', 'only for toys shops card', 70.37, 14, current_timestamp, current_timestamp),
       (DEFAULT, '200$', 'happy birthday', 230.75, 30, current_timestamp, current_timestamp);


INSERT INTO Tag
values (DEFAULT, 'wedding'),
       (DEFAULT, 'birthday'),
       (DEFAULT, 'health'),
       (DEFAULT, 'auto-moto');

INSERT INTO gift_certificate_tag
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 4),
       (5, 4);

INSERT INTO Users
values (DEFAULT, 'Tom', 'Riddle', 'tom.ri@one.com'),
       (DEFAULT, 'Bob', 'Marley', 'bob.marley@two.com'),
       (DEFAULT, 'Quentin ', 'Tarantino', 'quentin@three.com'),
       (DEFAULT, 'Walt ', 'Disney', 'disney@disney.com');

INSERT INTO Orders
values (DEFAULT, 263, current_timestamp, 1),
       (DEFAULT, 255.3, current_timestamp, 2);

INSERT INTO orders_gift_certificate
values (1, 1, 2),
       (1, 4, 1),
       (2, 2, 1);
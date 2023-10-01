CREATE TABLE Users
(
    id        int generated by default as identity primary key,
    name      varchar(100) not null,
    last_name varchar(100) not null,
    email     varchar(320) not null unique,
    password  varchar      not null,
    role      varchar      not null
);

-- CREATE TABLE role
-- (
--     id   int generated by default as identity primary key,
--     name varchar not null unique
-- );
--
-- CREATE TABLE users_role
-- (
--     users_id int references Users (id)
--         ON UPDATE CASCADE
--         ON DELETE CASCADE,
--     role_id  int references role (id)
--         ON UPDATE CASCADE
--         ON DELETE CASCADE,
--     primary key (users_id, role_id)
-- );
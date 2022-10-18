/*
    Table: books
    Columns:
    - Id
    - Title
*/

create table if not exists books
(
    id serial primary key,
    title varchar(100)
);

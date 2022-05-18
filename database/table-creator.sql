create schema foundations_project;

set search_path to foundations_project;

create table app_users (
    id              int generated always as identity,
    first_name      varchar not null,
    last_name       varchar not null,
    username        varchar unique not null,
    password        varchar not null check (length(password) >= 6)
);


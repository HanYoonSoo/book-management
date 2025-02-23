create table if not exists author(
    id bigint not null auto_increment primary key,
    name varchar(100) not null,
    email varchar(200) not null unique,
    deleted_at timestamp null,
    created_at timestamp not null,
    updated_at timestamp not null
)

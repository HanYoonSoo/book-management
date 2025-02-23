create table if not exists book(
    id bigint not null auto_increment primary key,
    title varchar(100) not null,
    description varchar(1000),
    isbn varchar(10) not null unique,
    publication_date date,
    author_id bigint not null,
    deleted_at timestamp null,
    created_at timestamp not null,
    updated_at timestamp not null,
    foreign key (author_id) references author(id)
)

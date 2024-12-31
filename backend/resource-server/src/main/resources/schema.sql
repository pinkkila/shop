drop table if exists product;

create table if not exists product
(
    id           bigserial primary key,
    product_name text not null
);



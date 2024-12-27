drop table if exists product;

create table if not exists product
(
    id           bigserial primary key,
    product_name text not null
);

insert into product (id, product_name)
values (1, 'tuote1'),
       (2, 'tuote2');

CREATE TABLE purchases (
    id Long not null,
    datePurchase timestamp not null,
    store varchar(255) not null,
    product varchar(255) not null,
    cost double not null,
    creationDate timestamp,
    modificationDate timestamp,
    primary key (id)
);

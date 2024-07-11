use test;
create table customer (
  customer_id int not null,
  customer_nm varchar(45) not null,
  primary key (customer_id)
);
create table customer_order (
  order_id int not null,
  customer_id int default null,
  product_id int default null,
  product_id int default null,
  order_price int default null,
  primary key (order_id)
);
create table product (
  product_id int not null,
  product_nm varchar(45) not null,
  product_price int default null,
  primary key (product_id)
);
insert into customer values ('1', '홍길동');
insert into customer values ('2', '이길동');
insert into product values ('111', 'tv', '1000');
insert into product values ('222', '냉장고', '2000');
insert into customer_order values ('11', '1', '111', '1000');

create table test.blacklist (
  blacklist_id int not null,
  customer_id int null,
  customer_nm varchar(45) null,
  PRIMARY KEY (blacklist_id)
);
  
insert into blacklist values (1, 2, '이길동');
insert into blacklist values (2, null, '박길동');
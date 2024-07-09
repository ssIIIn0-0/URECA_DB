
use madangdb;

SELECT * FROM customer
	where name = '김연아'; -- 어떤 조건

select name, address	-- 구분한 컬럼명
from customer			-- 어떤 테이블
where name = '김연아';	-- 어떤 조건


-- 3-1
SELECT bookname, price
FROM book;

-- 3-2
SELECT * FROM book;

-- 3-3
SELECT distinct publisher from book;	-- distinct : 중복제거

-- 3-4
SELECT * FROM book
where price < 20000;

-- 3-5
select * from book
where price >= 10000 and price <= 20000;

select * from book
 where price between 10000 and 20000;  -- 10000, 20000 포함
 
-- 3-6
select *
  from book
where publisher = '굿스포츠' or publisher = '대한미디어';

select *
  from book
where publisher in ('굿스포츠', '대한미디어');	-- subquery

-- 부정 (굿스포츠도 아니고, 대한미디어도 아닌)
select *
  from book
where publisher != '굿스포츠' and publisher != '대한미디어';	-- or X

select *
  from book
where publisher not in ('굿스포츠', '대한미디어');	-- subquery

-- 3.7 3.8
 select *
   from book
  where bookname like '축구%';
 select *
   from book
  where bookname like '%이야기';  
 
 select *
   from book
  where bookname like '%의%'; 
select *
  from customer
 where name like '%연%';
 
 -- 3.9
 select *
   from book
  where bookname like '_구%';  
  
-- 3.10
select *
  from book
 where bookname like '%축구%' and price >= 20000;
 
-- 3-11
select * from book
where publisher = '굿스포츠' or '대한미디어';

 select *
   from book
  where publisher in ('굿스포츠', '대한미디어');  
 
-- order by <= select 가 확정된 후 처리

-- 3.12
select *
  from book
 order by bookname desc; -- defalut : asc
 
select *
  from book
 order by price desc; -- defalut : asc 
 
-- 3.13, 3-14
select *
  from book
 order by price desc, bookname desc;
 
select *
  from book
  where price >= 10000  
 order by price desc, bookname desc;

-- 5가지 기본 함수 sum(), avg(), count(), max(), min()
-- 조건의 전체 또는 group by로 묶어서 처리
-- 3.15
select sum(saleprice) as 총매출 from orders;

-- alias, as
select orderid as orderId, custid 'cust id', bookid bookId, saleprice as salePrice, orderdate orderDate
  from orders;  
  
-- 3.16
select sum(saleprice) as 총매출
  from orders
 where custid = 2;

-- 3.17
select sum(saleprice) as total,
	   avg(saleprice) as average,
       min(saleprice) as min,
       max(saleprice) as max
  from orders;
  
-- 3.18
select count(*) totalCnt -- 속성 명을 넣어줌
  from orders;

select count(*) as yunaCnt
  from orders
 where custid = 2;  

-- 3.19
-- group by : 전체 row 를 쪼갠다. 어떻게? group by 뒤에 오는 조건
select *
  from orders; 

select count(*) from orders;	-- 10 건

select count(*) from orders		-- 10건을 custid로 쪼개서 각각 count(*)
group by custid;

select custid, count(*) from orders		-- group by 절에 사용한 컬럼을 select사용
group by custid;

select custid, bookid, count(*)		-- group by 절에 사용하지 않은 컬럼은 select 사용X (bookid)
from orders
group by custid;
-- 	77 from orders group by custid LIMIT 0, 1000 Error Code: 1055. Expression #2 of SELECT list is not in GROUP BY clause and contains nonaggregated column 'madangdb.orders.bookid' which is not functionally dependent on columns in GROUP BY clause; this is incompatible with sql_mode=only_full_group_by


-- 3.20
select custid, count(*)  -- group by 절의 select 항목을 추가로 조건 처리
  from orders
 group by custid
 having custid in (1, 2);
 -- 이 쿼리는 잘못됨. 마지막 having절에서 custid를 제한할거면, where에서 진행해도 됐었음
 -- 따라서 위 쿼리는 다음과 같이 수정하는게 좋음
 /* 
select custid, count(*)  -- group by 절의 select 항목을 추가로 조건 처리
  from orders
where custid in (1, 2)
group by custid;
 */
select custid, count(*)  -- group by 절의 select 항목을 추가로 조건 처리
  from orders
 group by custid
 having count(*) > 2; 
 
 
-- Join vs SubQuery
-- 어느 방법이 더 빠른가

-- 각각의 테이블에 데이터 1개만 남기고 모두 삭제
-- Join
select * from customer;	-- 1건

select * from customer, orders;	-- 1건 : customer 1건 x orders 1건

select * from customer, orders, book;	-- 1건 : customer 1건 x orders 1건 x book 1건

INSERT INTO Customer VALUES (2, '김연아', '대한민국 서울', '000-6000-0001');

select * from customer, orders, book;	-- 2건 : customer 2건 x orders 1건 x book 1건

INSERT INTO book VALUES (2, '축구인', '좋은 책', '9000');

select * from customer, orders, book;	-- 4건 : customer 2건 x orders 1건 x book 2건

select * from customer, orders, book
where orders.custid = customer.custid;	-- 2건 : customer 1건 x orders 1건 x book 2건

select * from customer, orders, book
where orders.custid = customer.custid
and orders.bookid = book.bookid;	-- 2건 : customer 1건 x orders 1건 x book 1건

select customer.custid, customer.name, orders.saleprice, book.bookname
  from customer, orders, book
where orders.custid = customer.custid
and orders.bookid = book.bookid;	-- 1건

select customer.custid, customer.name, orders.saleprice, book.bookname
  from customer, orders, book 
 where customer.custid > 1
   and orders.custid = customer.custid	-- 여기때문에 0건이 출력
   and orders.bookid = book.bookid;
-- 0 건 : customer 1 건 X orders 1 건 처리 중 orders 에는 custid 가 1 만 존재 

-- 데이터 원복
-- 3-21
select *
  from customer, orders;   -- 5 x 10 : 50 건수
  
select *
  from customer, orders
 where customer.custid = orders.custid; -- 1 x 10


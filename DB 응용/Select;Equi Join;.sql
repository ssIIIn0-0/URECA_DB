
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

-- 3-22, 3-23
-- 이름으로 정렬
select *
  from customer, orders
 where customer.custid = orders.custid
 order by customer.name desc;
 
-- 이름으로 asc 정렬, 동일한 경우 saleprice 로 desc
select *
  from customer, orders
 where customer.custid = orders.custid
 order by customer.name asc, orders.saleprice desc;
 
-- table alias
select *
  from customer c, orders o
 where c.custid = o.custid
 order by c.name asc, o.saleprice desc;
 
-- 원하는 컬럼만
select c.name, o.saleprice
  from customer c, orders o
 where c.custid = o.custid
 order by c.name asc, o.saleprice desc; 
 
-- table alias 생략한 컬럼에 대해 유추
select c.name, saleprice
  from customer c, orders o
 where c.custid = o.custid
 order by c.name asc, o.saleprice desc;  
-- ambiguous 
select c.name, custid -- 오류 발생 
  from customer c, orders o
 where c.custid = o.custid
 order by c.name asc, o.saleprice desc; 
 
-- 3-24
-- 동명이인 이 있을 경우 고객이 중복 처리
select c.name, sum(o.saleprice)
  from customer c, orders o
 where c.custid = o.custid
 group by c.name
 order by c.name;
 
select c.name, sum(o.saleprice) -- key 컬럼에 대한 group by 절은 가장 세분화된 group by 가 되므로 select 에 다른 컬럼이 와도 문제 X
  from customer c, orders o
 where c.custid = o.custid
 group by c.custid -- key
 order by c.name;
 
select c.name, sum(o.saleprice) -- 오류 발생 : group by 절의 phone 과 name 이 맞지 않는다. phone 와야 한다.
  from customer c, orders o
 where c.custid = o.custid
 group by c.phone -- non key
 order by c.phone; 
 
-- 3-25
-- 아래 두 쿼리는 결과가 같고 수행계획도 같다. 단, mysql 기준
-- dbms 가 달라지면 다른 결과를 낳을 수 있다.
select c.name, b.bookname
  from customer c, book b, orders o
 where c.custid = o.custid
   and b.bookid = o.bookid;
select c.name, b.bookname
  from orders o, customer c, book b
 where o.custid = c.custid
   and o.bookid = b.bookid;
   
-- ANSI SQL JOIN
select c.name, o.saleprice
  from customer c, orders o
 where c.custid = o.custid;
select c.name, o.saleprice
  from customer c inner join orders o
 where c.custid = o.custid;
 
select c.name, o.saleprice
  from customer c inner join orders o on c.custid = o.custid;
  
select c.name, b.bookname
  from orders o inner join customer c on o.custid = c.custid
                join book b on o.bookid = b.bookid;

-- self join
-- hr database / employees table 기준
-- first_name 이 'Alexander', last_name 이 'Hunold' 인 사람이 관리하는 사람들 목록
use hr;
select staff.*
from employees staff, employees manager
where manager.first_name = 'Alexander' and manager.last_name = 'Hunlod'
and staff.manager_id = manager.employee_id;

-- 3-26
use madangdb;
select c.name, b.bookname
  from customer c, book b, orders o
 where c.custid = o.custid
   and b.bookid = o.bookid
   and b.price = 20000;
   
-- outer join
-- inner join 관계가 없는 데이터도 나오도록 쿼리 수행
-- 주문하지 않은 박세리도 나온다.
select * 
  from customer c, orders o;
-- 아래의 쿼리는 주문한 사람만 나온다. (박세리는 안나온다.)  
select * 
  from customer c, orders o
 where c.custid = o.custid;
 
-- left outer join ( 왼쪽 테이블 기준으로 관계가 없어도 출력 )
select c.name, o.saleprice 
  from customer c left outer join orders o on c.custid = o.custid; 
   
-- 휴가 안간 사원 목록
-- 휴가 테이블에 어떤 사원이 언제 신청했고, 얼마 기간의 휴가를 사용, 사용중...
-- 휴가를 가지 않은 사원 포함 전체 목록을 조회하려면 사원 테이블 left outer join 휴가 테이블 ...
   
-- 즐겨찾기 장소 관계에서 사용자가 즐겨찾기 등록한 장소를 그렇지 않은 장소와 함게 조회해서 별도로 표시할 경우
-- 장소 기준 left outer join 즐겨찾기 형태로 처리해야 원래 보여주려던 장소 목록을 출력할 수 있다.
-- 가령 장소가 100만개, 즐겨찾기 1000개
/*
select ...
  from 장소 left outer join 즐겨찾기
 where.....
 
select ... (화면에 보여줄 10개의 row 만 먼저 추출)
  from 장소
 where ....
  
select ....(장소)
  from 즐겨찾기
 where 고객 = 로그인한 고객
 
각각 별도로 추출한 후 left outer join 
*/
   
-- -------------------------------   
-- subquery : query 의 일부가 별도의 완성적인 query
select max(price) -- 가장 비싼 도서의 가격 (35000)
  from book;
  
select bookname
  from book
 where price = 35000;
 
select bookname
  from book
 where (bookid, bookname) in (select bookid, bookname from book); 
  
-- subquery 부분만 고려
select max(price) from book;   -- 단일행 단일열
select bookid, bookname from book; -- 다중행 다중열
select bookid from book; -- 다중행 단일열
select bookid, bookname from book where bookid = 3; -- 단일행 다중열
-- Subquery returns more than 1 row
-- 해결 : subquery 가 복수개의 row return 할 경우, 대응되는 왼쪽의 컬럼에 in 사용, 항상 단 1개의 row 만 return 될 경우에만 = 사용
   
-- Operand should contain ~~~~  
-- 해결 : subquery 의 return column 과 대응되는 왼쪽의 컬러의 수가 맞지 않는 경우이므로, 갯수를 동일하게 맞춰준다.
   
-- 3-29
-- 도서를 주문한 고객의 이름
select customer.name
  from customer
 where custid in (select custid from orders);
-- 위 subquery 를 join 형태로 변경
select customer.name -- 10명의 중복 포함 고객의 이름
  from customer, orders
 where customer.custid = orders.custid;  
 
select distinct customer.name -- distinct 로 중복 제거
  from customer, orders
 where customer.custid = orders.custid;   
select customer.name
  from customer
 where custid in (select distinct custid from orders);  -- 만약 subquery 가 500 고객이 20000 번 주문 <= subquery 자체에서 distinct 를 사용하는 게 낫다.
 
-- subquery 는 메모리에 적재한 후 처리( io disk swaping ) 
-- subquery 는 메모리에 적재되어서 이후 처리가 되므로 매우 빠르게 처리된다. 동시에 메모리 자원을 낭비하게 된다.
-- 3-30
-- 3 개의 테이블을 모두 사용
-- subquery
select name from customer where custid in (
    select custid from orders where bookid in (
       select bookid from book where publisher = '대한미디어'
    )
);
   
-- join 으로 1 : orders, book join   
select name from customer where custid in (
    select o.custid
      from orders o, book b
     where o.bookid = b.bookid
       and b.publisher = '대한미디어'
);
   
-- join 으로 2 : customer, orders, book 3 테이블 모두 join   
select c.name
  from customer c, orders o, book b
 where c.custid = o.custid
   and o.bookid = b.bookid
   and b.publisher = '대한미디어';
   

-- 3-31
-- 출판사별 평균 가격 테이블이 있으면 그걸 사용, 만약 없다.
select b1.*
  from book b1
 where b1.price > ( select avg(b2.price ) from book b2 where b2.publisher = b1.publisher );
-- 아래 join 에 subquery 가 사용( 
select b1.*
  from book b1, (
                select publisher, avg(price) avg_price
                  from book
                 group by publisher  
                ) avg_book      -- 인라인 뷰
 where b1.publisher = avg_book.publisher
   and b1.price > avg_book.avg_price;
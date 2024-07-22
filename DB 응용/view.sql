-- 필요에 의해 select 문을 작성
select o.orderid, o.custid, o.bookid, b.bookname, o.saleprice, o.orderdate
	from customer c, orders o, book b
    where c.custid = o.custid
    and b.bookid = o.bookid;
    
-- 위 쿼리를 자주 반복적으로 사용할 경우 -> 해당 쿼리를 하나의 오브젝트View 로 만들어서 사용
create view Vorder
as
select o.orderid, o.custid, o.bookid, b.bookname, o.saleprice, o.orderdate
	from customer c, orders o, book b
    where c.custid = o.custid
    and b.bookid = o.bookid;
    
-- view 사용
select * from vorder;

-- view 사용 + 조건 ==> 가상의 테이블로 사용
select * from vorder
where saleprice > 10000;

-- view 수정, 삭제 ==> DB마다 다름. 일반적으로는 지원X(read Only)

-- view 의 내부 쿼리 수행
select * from (
-- view 생성 select
select o.orderid, o.custid, o.bookid, b.bookname, o.saleprice, o.orderdate
	from customer c, orders o, book b
    where c.custid = o.custid
    and b.bookid = o.bookid
) sub
where sub.saleprice > 10000;


select * from jdbc_big where col_1 = 1234560; -- execution plan : PK

select count(*) from jdbc_big where col_2 = '강주상'; -- execution plan : full table scan -> index 이용

-- 데이터 건수가 충분히 더 큰 테이블을 만들자
create table jdbc_big_2 as select * from jdbc_big;
insert into jdbc_big (col_2, col_3, col_4) select col_2, col_3, col_4 from jdbc_big_2;

select count(*) from jdbc_big;

select count(*) from jdbc_big where col_2 like '%주상'; -- index 안탄다. (% 가 앞에 있음)
select count(*) from jdbc_big where col_2 like '강주%';

select count(*) from jdbc_big where upper(col_2) = '강주상';  -- index 안탄다. (인덱스 부분에 function을 사용해서)
select count(*) from jdbc_big where col_2 = upper('강주상'); -- 비교하는 쪽에 function을 사용해야함



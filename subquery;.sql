-- sum
select sum(salary)
  from employees;
  
-- 부서별 (department_id) sum(salary)
select department_id, sum(salary)
  from employees
 group by department_id;
 
 -- 특정 부서(들)의 부서별 sum(salary)
 -- 2개 부서의 sum 만 필요한데 전체 부서의 sum 필요
select department_id, sub_salary
  from (
        select department_id, sum(salary) sub_salary
          from employees
         group by department_id
       ) sub
 where sub.department_id in (60, 100);
 
 -- case when then else end 를 이용해서 보다 효율적인 코드를 작성
 select sum( case when department_id = 60 then salary else 0 end ) sum_60,
        sum( case when department_id = 100 then salary else 0 end ) sum_100
   from employees
  where department_id in (60, 100);
 
-- 아래 코드를 개선
select
(select sum(salary) from employees where department_id = 50) sum50,
(select avg(salary) from employees where department_id = 60) avg60,
(select max(salary) from employees where department_id = 90) max90,
(select min(salary) from employees where department_id = 90) min90;
select sum( case when department_id = 50 then salary else null end ) sum50,
       avg( case when department_id = 60 then salary else null end ) avg60,
       max( case when department_id = 90 then salary else null end ) max90,
       min( case when department_id = 90 then salary else null end ) min90 -- else 0 이면 min 이 0 null은 계산 X
  from employees
 where department_id in (50, 60, 90);
 
-- exist
-- in vs exist, not in not exist 비교
-- 1건
select customer_nm
  from customer
 where customer_id in (select customer_id from customer_order);
 
-- 2건
-- customer 건수만큼 exists 쪽 서브 쿼리를 수행하고 결과 row 가 존재하면 선택
select customer_nm
  from customer
 where exists (select customer_id from customer_order); 
 
-- 수정 1건
select c.customer_nm
  from customer c
 where exists (select co.customer_id from customer_order co where c.customer_id = co.customer_id); 

-- not in 문제 X
select customer_nm
  from customer
 where customer_id not in (select customer_id from customer_order);

-- not exist 문제 X 
select c.customer_nm
  from customer c
 where not exists (select co.customer_id from customer_order co where c.customer_id = co.customer_id);  

-- not in 문제 발생 0 건
-- not in () 은 집합 연산을 수행하는 데 null 비교에서 최종적으로 false 가 된다.
select customer_nm
  from customer
 where customer_id not in (select customer_id from blacklist); -- (2, null)
 
 -- 홍길동의 경우 select 1 != 2 && 1 != null 부분이 true 가 되어야 하는데
 select 1 != null; -- null --> 전체가 false 이므로 선택 X
 
 -- not in 수행시 subquery 에 is not null 추가
select customer_nm
  from customer
 where customer_id not in (select customer_id from blacklist where customer_id is not null); -- (2, null)  

-- not exists 는 row 끼리 join 으로 처리하므로 문제 X 
select c.customer_nm
  from customer c
 where not exists (select b.customer_id from blacklist b where c.customer_id = b.customer_id); 

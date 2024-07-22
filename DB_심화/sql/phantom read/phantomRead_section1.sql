-- section 1 phantom read, 유령데이터 읽기
SELECT * FROM madangdb.users;

SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;

START TRANSACTION;
USE madangdb;
SELECT	*
FROM	Users;	-- 1 row

-- tx2 insert & commit

SELECT	*
FROM	Users; -- 2 row

commit;

-- mysql에서는 유령데이터 읽기가 발생하지 않음
-- REPEATABLE READ; 모드가 있음
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;	-- 반복적으로 동일한 데이터 읽기
-- 내 트랜잭션이 시작하고 끝나기 전까지는 다른 트랜잭션에 의해 row 에 변화 (commit 포함)가 생기더라도
-- 동일한  row를 조회한다.
-- REPEATTABLE READ 가 default

START TRANSACTION;

SELECT	*
FROM	Users;	-- 1 row

-- tx2 insert & commit

SELECT	*
FROM	Users; -- 2 row

commit;
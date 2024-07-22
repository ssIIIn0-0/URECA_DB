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

-- READ COMMITED vs REPEATABLE COMMITED
-- 트랜잭션 격리 수준에는 4가지가 있다.
-- level 0. READ UNCOMMITTED
-- level 1. READ COMMITTED
-- level 2. REPEATABLE READ
-- level 3. SERIALIIZABLE
-- 각각의 레벨에 따라 dirtyread, phantomread등을 허용, 금지하는 수준이 달라진다.

-- 그중에서 READ COMMITED, REPEATABLE COMMITED 은 각각
-- READ COMMITTED : Dirty Read 방지, Non-repeatable Read 허용
-- REPEATABLE READ: Dirty Read 방지, Non-repeatable Read 방지, Phantom Read 방지 (MySQL InnoDB에서만)
-- 의 고립 수준을 결정한다.

-- 추가 : DBMS마다 이러한 고립 수준을 결정하는 명령어가 다를 수 있고, 해당 격리 수준은 MySQL에서 해당한다.
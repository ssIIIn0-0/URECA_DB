-- section 1 non-reapeatable read, 반복적으로 동일한 데이터 읽기 불가능
SELECT * FROM madangdb.users;

SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
START TRANSACTION;
USE madangdb;
SELECT	*
FROM	Users
WHERE	id=1;	-- age:30

-- tx2 update & commit

SELECT	*
FROM	Users
WHERE	id=1;	-- age:21

-- tx2 rollback
SELECT	*
FROM	Users
WHERE	id=1;	-- age:21

commit;
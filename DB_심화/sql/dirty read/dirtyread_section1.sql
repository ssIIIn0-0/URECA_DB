-- section 1 dirty read
SELECT * FROM madangdb.users;

SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
START TRANSACTION;
USE madangdb;
SELECT	*
FROM	Users
WHERE	id=1;	-- age:30

-- tx2 update

SELECT	*
FROM	Users
WHERE	id=1;	-- age:21 <- uncommited (dirty read) // 오손 읽기

-- tx2 rollback
SELECT	*
FROM	Users
WHERE	id=1;	-- age:30

commit;
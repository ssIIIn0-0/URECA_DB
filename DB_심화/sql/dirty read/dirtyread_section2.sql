-- section 2 dirty read
SELECT * FROM madangdb.users;

set sql_safe_updates = 0;
START TRANSACTION;
UPDATE	Users
SET		age=21
WHERE	id=1;

rollback;
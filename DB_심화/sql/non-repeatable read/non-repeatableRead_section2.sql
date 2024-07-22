-- section 2 non-reapeatable read, 반복 불가능 읽기
SELECT * FROM madangdb.users;

set sql_safe_updates = 0;
START TRANSACTION;
UPDATE	Users
SET		age=21
WHERE	id=1;

commit;

rollback;
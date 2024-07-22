-- section 2  phantom read, 유령데이터 읽기
SELECT * FROM madangdb.users;

set sql_safe_updates = 0;
START TRANSACTION;

insert into Users values(3, 'Bob', 27);

delete from users where id = 3;

commit;
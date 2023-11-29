INSERT INTO sys_user (`uid`, `username`, `password`, `realname`, `createtime`, `state`, `mobile`, `amount`)
VALUES (?, ?, ?, ?, NOW(), 1, ?, ?);

select * from sys_user where state = '1';
INSERT INTO sys_user (`uid`, `username`, `password`, `realname`, `createtime`, `state`, `mobile`, `amount`)
VALUES ('1001', 'johndoe', 'mypassword', 'John Doe', '2023-05-13 20:07:15', 1, '1234567890', 5000.00);

select * from sys_user where uid = 22132048 and state = 1

select * from books_info where state = '2';

select * from rent_info where uid = 123;
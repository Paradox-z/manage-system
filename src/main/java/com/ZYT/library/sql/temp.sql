INSERT INTO sys_user (`uid`, `username`, `password`, `realname`, `createtime`, `state`, `mobile`, `amount`)
VALUES (?, ?, ?, ?, NOW(), 1, ?, ?);

select * from sys_user where state = '1';
INSERT INTO sys_user (`uid`, `username`, `password`, `realname`, `createtime`, `state`, `mobile`, `amount`)
VALUES ('1001', 'jensen', 'password', 'Jensen', '2023-12-10 12:45:15', 1, '12345678901', 5000.00);

select * from sys_user where uid = 1001 and state = 1

select * from books_info where state = '2';

select * from rent_info where uid = 123;

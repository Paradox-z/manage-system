/*account data, md5 privacy common usage, state fetch from function now()*/
insert into sys_admin(aid, username, password, realname, createtime, state, mobile)
VALUES (uuid(), 'admin', md5('123'), 'zhangsan', now(), 1, '18676663333');
delete
from sys_admin
where username = 'zhangsan';
INSERT INTO sys_user (uid, username, password, realname, createtime, state, mobile, amount)
VALUES (uuid(), 'zhangsan', md5('123'), 'ZYT', NOW(), 1, '18255551777', 999999999.0);
# book information list created
INSERT INTO books_info(bid, bookname, price, description, author, createtime, state)
VALUES ('001', 'Java编程思想', 78.00, '介绍Java编程的经典著作', 'Bruce Eckel', '2021-01-05 09:00:00', 1),
       ('002', '计算机网络', 48.90, '介绍计算机网络的基本概念和原理', '谢希仁', '2021-02-02 10:00:00', 1),
       ('003', '算法导论', 65.00, '经典的算法教材', 'Thomas H. Cormen', '2021-03-03 11:00:00', 1),
       ('004', '深入浅出MySQL', 42.00, 'MySQL数据库使用入门级教材', '宋陆跃', '2021-04-04 12:00:00', 1),
       ('005', 'Python基础教程', 26.80, 'Python编程语言入门级教材', 'Magnus Lie Hetland', '2021-05-05 13:00:00', 1),
       ('006', 'Java并发编程实战', 88.80, '介绍Java并发编程的实战技巧', 'Brian Goetz', '2021-06-06 14:00:00', 1),
       ('007', '数据结构与算法分析', 62.30, '数据结构和算法的经典教材', 'Mark Allen Weiss', '2021-07-07 15:00:00', 1),
       ('008', 'TCP/IP详解', 78.00, '深入介绍TCP/IP协议，经典著作', 'W.Richard Stevens', '2021-08-08 16:00:00', 1),
       ('009', '计算机组成原理', 52.90, '介绍计算机组成原理的基本概念和技术', '唐朔飞', '2021-09-09 17:00:00', 1),
       ('010', '计算机操作系统', 68.80, '介绍操作系统的基本概念和技术', 'Abraham Silberschatz', '2021-10-10 18:00:00',
        1),
       ('011', 'Python机器学习实践', 95.00, 'Python机器学习实践的经典教材', 'Sebastian Raschka', '2021-11-11 19:00:00',
        1),
       ('012', 'Java测试驱动开发', 45.60, 'TDD在Java开发中的应用', 'Viktor Farcic', '2021-12-12 20:00:00', 1),
       ('013', '计算机图形学基础', 76.30, '计算机图形学的入门级教材', 'Edward Angel', '2022-01-01 12:00:00', 1),
       ('014', 'Linux命令行与Shell脚本编程大全', 59.80, '介绍Linux命令行和Shell脚本编程的经典教材', 'Richard Blum',
        '2022-02-02 13:00:00', 1),
       ('015', 'Web应用开发实战：完整电商项目', 128.00, 'Web电商项目实战指南', '何平', '2022-03-03 14:00:00', 1),
       ('016', '深度学习', 68.80, '深入介绍深度学习的技术和原理', 'Ian Goodfellow', '2022-04-04 15:00:00', 1),
       ('017', 'Java虚拟机规范', 68.50, '介绍Java虚拟机规范的经典教材', 'Tim Lindholm', '2022-05-05 16:00:00', 1),
       ('018', '计算机程序设计艺术', 89.00, '计算机程序设计方面的文化沉淀，经典著作', 'Donald E. Knuth',
        '2022-06-06 17:00:00', 1),
       ('019', '现代操作系统', 72.30, '介绍现代操作系统的基本概念和技术', 'Andrew S. Tanenbaum', '2022-07-07 18:00:00',
        1),
       ('020', '代码大全', 58.00, '软件项目代码质量提高方面的经典教材', 'Steve McConnell', '2022-08-08 19:00:00', 1);
INSERT INTO books_info(bid, bookname, price, description, author, createtime, state)
VALUES ('021', 'Git权威指南', 49.00, '介绍Git版本控制工具的使用和原理', 'Scott Chacon', '2022-09-09 20:00:00', 1),
       ('022', '数据挖掘:概念与技术', 78.00, '数据挖掘的入门级教材', 'Jiawei Han', '2022-10-10 19:00:00', 1),
       ('023', 'Java为你所用', 35.00, 'Java基础和高级特性的实战教程', 'Swing Wong', '2022-11-11 18:00:00', 1),
       ('024', 'Effective Java', 49.80, 'Java编程实践的经典教材', 'Joshua Bloch', '2022-12-12 17:00:00', 1),
       ('025', '编译原理', 72.30, '介绍编译原理的基本原理和技术', 'Alfred V. Aho', '2023-01-01 16:00:00', 1),
       ('026', 'MySQL技术内幕', 98.00, '介绍MySQL数据库的内部原理和技术', '姜承尧', '2023-02-02 15:00:00', 1),
       ('027', 'C++ Primer', 83.50, '介绍C++编程的经典教材', 'Stanley B. Lippman', '2023-03-03 14:00:00', 1),
       ('028', 'Java核心技术', 69.90, '介绍Java核心技术和应用的经典教材', 'Cay S. Horstmann', '2023-04-04 13:00:00', 1),
       ('029', '软件工程:现代方法', 75.00, '软件工程的经典教材', 'Ian Sommerville', '2023-05-05 12:00:00', 1),
       ('030', 'C语言程序设计', 32.20, '介绍C语言编程的入门教材', 'Brian W. Kernighan', '2023-06-06 11:00:00', 1),
       ('031', 'Vue.js实战', 49.00, 'Vue.js的实战教程', '梁灏', '2023-07-07 10:00:00', 1),
       ('032', '软件调试/软件测试艺术', 58.00, '软件调试与测试方面的经典教材', 'Gerald M. Weinberg',
        '2023-08-08 09:00:00', 1),
       ('033', 'Python机器学习基础教程', 42.80, 'Python机器学习入门级教程', 'Andreas Muller', '2023-09-09 08:00:00', 1),
       ('034', '算法与数据结构', 65.50, '算法与数据结构入门级教材', 'Robert Lafore', '2023-10-10 07:00:00', 1),
       ('035', 'Linux/UNIX系统编程手册', 68.00, '介绍Linux/UNIX系统编程的经典教材', 'Michael Kerrisk',
        '2023-11-11 06:00:00', 1),
       ('036', 'Web前端开发', 39.90, '介绍Web前端开发的基本原理和技术', '张旭', '2023-12-12 05:00:00', 1),
       ('037', 'JavaScript高级程序设计', 46.80, 'JavaScript编程的高级特性教材', 'Nicholas C. Zakas',
        '2024-01-01 04:00:00', 1),
       ('038', 'TCP/IP协议族', 78.50, '介绍TCP/IP协议的技术和实战应用', 'Douglas E. Comer', '2024-02-02 03:00:00', 1),
       ('039', '面向对象分析与设计', 56.00, '对象分析和设计入门级教程', 'Grady Booch', '2024-03-03 02:00:00', 1),
       ('040', 'Java EE 7实战', 89.00, 'Java企业级应用实战教程', 'Antonio Goncalves', '2024-04-04 01:00:00', 1);

select * from sys_admin;

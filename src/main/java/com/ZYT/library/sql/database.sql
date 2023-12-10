
use hubue;

drop table if exists sys_user;

drop table if exists sys_admin;
create table sys_admin (
       aid varchar(50) primary key comment 'admin number',
       username varchar(50) comment 'account',
       password varchar(255) comment 'password',
       realname varchar(50) comment 'name',
       createtime datetime comment 'create time',
       state int comment 'State 0:unsubscribe 1:normal',
       mobile varchar(50) comment 'mobile phone'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='system admin sheet';

create table sys_user (
          uid varchar(50) primary key comment 'user number',
          username varchar(50) comment 'acount',
          password varchar(255) comment 'password',
          realname varchar(50) comment 'name',
          createtime datetime comment 'create time',
          state int comment 'State 0:unsubscribe 1:normal',
          mobile varchar(50) comment 'mobile phone',
          amount decimal(18,2) comment 'balance'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='system admin sheet';
#alter table sys_user add mobile varchar(50) comment 'Phone';
#alter table sys_user add amount decimal(18,2) comment 'Balance';
ALTER TABLE recharge_record
    ADD CONSTRAINT fk_recharge_record_sys_user_uid
        FOREIGN KEY (uid) REFERENCES sys_user(uid);
drop table if exists recharge_record;
create table recharge_record(
        rid varchar(50) primary key,
        uid varchar(50),
        amount decimal(18,2) comment 'amounts',
        createtime datetime comment 'create time',
        state int comment 'State -1:chargeback 0:unsubscribe 1:recharge 2:deposit 3:rental 4:refund deposit',
        aid varchar(50) comment 'recharger',
        remark varchar(255) comment 'comment'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='consumption record sheet';

drop table if exists books_info;
create table books_info(
                           bid varchar(50) primary key,
                           bookname varchar(50) comment 'book name',
                           price decimal(18,2) comment 'price',
                           description varchar(255) comment 'presentation',
                           author varchar(50) comment 'author',
                           createtime datetime comment 'create time',
                           state int comment 'State 0:unsubscribe 1:formal'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='books information sheet';

drop table if exists rent_info;
create table rent_info(
       rid varchar(50) primary key,
       uid varchar(50),
       bid varchar(50),
       aid varchar(50),
       createtime datetime comment 'rental time',
       price decimal(18,2) comment 'daily rental',
       deposit decimal(18,2) comment 'down payment',
       endtime datetime comment 'return time',
       amount decimal(18,2) comment 'total rental',
       remark varchar(255) comment 'comment',
       state int comment 'State 0:unsubscribe 1:rent books application 2:under lease 3:returned'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='rental information sheet';



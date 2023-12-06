
use hubue;

drop table if exists sys_user;

drop table if exists sys_admin;
create table sys_admin (
       aid varchar(50) primary key comment 'admin number',
       username varchar(50) comment 'account',
       password varchar(255) comment 'password',
       realname varchar(50) comment 'name',
       createtime datetime comment 'create time',
       state int comment 'status 0-unsubscribe 1-normal',
       mobile varchar(50) comment 'mobile phone'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='system admin list';

create table sys_user (
          uid varchar(50) primary key comment 'user number',
          username varchar(50) comment 'acount',
          password varchar(255) comment 'password',
          realname varchar(50) comment 'name',
          createtime datetime comment 'create time',
          state int comment 'status 0-unsubscribe 1-normal',
          mobile varchar(50) comment 'mobile phone',
          amount decimal(18,2) comment 'balance'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='system admin list';
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
        state int comment 'status -1:chargeback 0:unsubscribe 1:recharge 2:deposit 3:rental 4:refund deposit',
        aid varchar(50) comment 'recharger',
        remark varchar(255) comment 'comment'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='consumption record sheet';

drop table if exists books_info;
create table books_info(
                           bid varchar(50) primary key,
                           bookname varchar(50) comment '书名',
                           price decimal(18,2) comment '售价',
                           description varchar(255) comment '简介',
                           author varchar(50) comment '作者',
                           createtime datetime comment '创建时间',
                           state int comment '状态 0 作废 1 正常'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图书信息表';

drop table if exists rent_info;
create table rent_info(
       rid varchar(50) primary key,
       uid varchar(50),
       bid varchar(50),
       aid varchar(50),
       createtime datetime comment '租赁时间',
       price decimal(18,2) comment '日租金',
       deposit decimal(18,2) comment '押金',
       endtime datetime comment '还书时间',
       amount decimal(18,2) comment '总租金',
       remark varchar(255) comment '备注',
       state int comment '0 作废 1 申请租书 2 租赁中 3 已还'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租赁信息表';



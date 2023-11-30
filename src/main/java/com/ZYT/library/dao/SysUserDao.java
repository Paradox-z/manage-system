package com.ZYT.library.dao;

import cn.hutool.db.Db;
import com.ZYT.library.common.DataSource;
import com.ZYT.library.entity.RechargeRecord;
import com.ZYT.library.entity.SysAdmin;
import com.ZYT.library.entity.SysUser;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SysUserDao extends DataSource {


    // user login
    public SysUser login(String name, String pwd) {
        SysUser u = null;
        String sql = "select * from sys_user where state=1 and username=? and password=?";
        try {
            rs = query(sql, name, pwd);
            while (rs.next()) {
                u = new SysUser();
                u.setAmount(rs.getBigDecimal("Amount"));
                u.setMobile(rs.getString("Mobile"));
                u.setCreatetime(rs.getTimestamp("Createtime"));
                u.setPassword(rs.getString("Password"));
                u.setRealname(rs.getString("Realname"));
                u.setUsername(rs.getString("Username"));
                u.setUid(rs.getString("Uid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    // user list
    public List<SysUser> list() {
        List<SysUser> users = new ArrayList<>();
        String sql = "select * from sys_user where state=1 order by createtime desc";
        try {
            rs = query(sql, null);
            while (rs.next()) {
                SysUser u = new SysUser();
                u.setAmount(rs.getBigDecimal("Amount"));
                u.setMobile(rs.getString("Mobile"));
                u.setCreatetime(rs.getTimestamp("Createtime"));
                u.setPassword(rs.getString("Password"));
                u.setRealname(rs.getString("Realname"));
                u.setUsername(rs.getString("Username"));
                u.setUid(rs.getString("Uid"));
                users.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return users;
    }

    // user recharge
    public void updateUserAmount(BigDecimal amount, String uid) {
        String sql = "update sys_user set amount=ifnull(amount,0)+? where uid=?";
        try {
            execute(sql, amount, uid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    // user deduction amount
    public void reduceUserAmount(BigDecimal amount, String uid) {
        String sql = "update sys_user set amount=ifnull(amount,0)-? where uid=?";
        try {
            execute(sql, amount, uid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    // search by id
    public SysUser findById(String id) {
        String sql = "select * from sys_user where uid=?";
        List<SysUser> users = new ArrayList<>();
        try {
            users = Db.use().query(sql, SysUser.class, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users.get(0);
    }

    /**
     * find all users
     *
     * @return
     */
    public List<SysUser> findAllUser() {
        List<SysUser> sysUserList = new ArrayList<>();
        SysUser sysUser;
        String sql = "select * from sys_user where state = '1';";
        try {
            rs = query(sql);
            while (rs.next()) {
                sysUser = new SysUser();
                sysUser.setCreatetime(rs.getTimestamp("Createtime"));
                sysUser.setState(rs.getInt("State"));
                sysUser.setRealname(rs.getString("Realname"));
                sysUser.setMobile(rs.getString("Mobile"));
                sysUser.setUid(rs.getString("Uid"));
                sysUser.setPassword(rs.getString("Password"));
                sysUser.setUsername(rs.getString("Username"));
                sysUser.setAmount(rs.getBigDecimal("Amount"));
                sysUserList.add(sysUser);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysUserList;
    }

    /**
     * search by id
     *
     * @param Uid
     * @return
     */
    public SysUser getUserById(String Uid) {
        SysUser sysUser = null;
        String sql = "select * from sys_user where uid = ? and state = 1;";
        try {
            rs = query(sql, Uid);
            while (rs.next()) {
                sysUser = new SysUser();
                sysUser.setCreatetime(rs.getTimestamp("Createtime"));
                sysUser.setState(rs.getInt("State"));
                sysUser.setRealname(rs.getString("Realname"));
                sysUser.setMobile(rs.getString("Mobile"));
                sysUser.setUid(rs.getString("Uid"));
                sysUser.setPassword(rs.getString("Password"));
                sysUser.setUsername(rs.getString("Username"));
                sysUser.setAmount(rs.getBigDecimal("Amount"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysUser;
    }


    public boolean deleteById(String id) {
        String sql = "UPDATE sys_user SET state = 0 WHERE uid = ?";
        try {
            execute(sql, id);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * subscribe an account
     *
     * @param sysUser
     * @return
     */
    public boolean addSysUser(SysUser sysUser) {

        String sql = "INSERT INTO sys_user (`uid`, `username`, `password`, `realname`, `createtime`, `state`, `mobile`, `amount`)\n" +
                "VALUES (?, ?, ?, ?, NOW(), 1, ?, ?);";
        try {
            execute(sql, sysUser.getUid(), sysUser.getUsername(), sysUser.getPassword(), sysUser.getRealname(), sysUser.getMobile(), sysUser.getAmount());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

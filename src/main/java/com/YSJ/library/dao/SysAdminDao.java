package com.ZYT.library.dao;

import com.ZYT.library.common.DataSource;
import com.ZYT.library.entity.SysAdmin;

import java.util.ArrayList;
import java.util.List;

public class SysAdminDao extends DataSource {

    /**
     * 登录
     *
     * @param name
     * @param pwd
     * @return
     */
    public SysAdmin adminLogin(String name, String pwd) {
        SysAdmin admin = null;
        String sql = "select * from sys_admin where username=? and password=? and state=1";

        try {
            rs = query(sql, name, pwd);
            while (rs.next()) {
                admin = new SysAdmin();
                admin.setCreatetime(rs.getTimestamp("Createtime"));
                admin.setState(rs.getInt("State"));
                admin.setRealname(rs.getString("Realname"));
                admin.setMobile(rs.getString("Mobile"));
                admin.setAid(rs.getString("Aid"));
                admin.setPassword(rs.getString("Password"));
                admin.setUsername(rs.getString("Username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return admin;
    }

    /**
     * 查找所有管理员
     *
     * @return
     */
    public List<SysAdmin> findAllAdmin() {
        List<SysAdmin> adminList = new ArrayList<>();
        SysAdmin admin;
        String sql = "select * from sys_admin where state = '1';";
        try {
            rs = query(sql);
            while (rs.next()) {
                admin = new SysAdmin();
                admin.setCreatetime(rs.getTimestamp("Createtime"));
                admin.setState(rs.getInt("State"));
                admin.setRealname(rs.getString("Realname"));
                admin.setMobile(rs.getString("Mobile"));
                admin.setAid(rs.getString("Aid"));
                admin.setPassword(rs.getString("Password"));
                admin.setUsername(rs.getString("Username"));
                adminList.add(admin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminList;
    }

    /**
     * 通过姓名查找管理员
     *
     * @param username
     * @return
     */
    public SysAdmin getAdminByName(String username) {
        SysAdmin admin = null;
        String sql = "select * from sys_admin where username = ? and state = 1";
        try {
            rs = query(sql, username);
            while (rs.next()) {
                admin = new SysAdmin();
                admin.setCreatetime(rs.getTimestamp("Createtime"));
                admin.setState(rs.getInt("State"));
                admin.setRealname(rs.getString("Realname"));
                admin.setMobile(rs.getString("Mobile"));
                admin.setAid(rs.getString("Aid"));
                admin.setPassword(rs.getString("Password"));
                admin.setUsername(rs.getString("Username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return admin;
    }

    /**
     * 通过用户名删除
     *
     * @param adminName
     * @return
     */
    public boolean deleteByName(String adminName) {
        String sql = "UPDATE sys_admin SET state = 0 WHERE username = ?";
        try {
            execute(sql, adminName);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    public boolean addAdmin(SysAdmin admin) {
        String sql = "INSERT INTO sys_admin (aid, username, password, realname, createtime, state, mobile) " +
                "VALUES (?, ?, ?, ?, NOW(), 1, ?);";
        try {
            execute(sql, admin.getAid(), admin.getUsername(), admin.getPassword(), admin.getRealname(), admin.getMobile());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

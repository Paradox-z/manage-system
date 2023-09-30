package com.ZYT.library.dao;

import cn.hutool.db.Db;
import com.ZYT.library.entity.RentInfo;
import com.ZYT.library.common.DataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentInfoDao extends DataSource {

    // 添加租赁信息
    public void addRent(RentInfo rent) {
        String sql = "insert into rent_info(rid,uid,bid,createtime,endtime,state) values (uuid(),?,?,now(),DATE_ADD(NOW(), INTERVAL 1 MONTH),1)";
        try {
            Db.use().execute(sql, rent.getUid(), rent.getBid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 列出所有租赁信息
    public List<RentInfo> list() {
        String sql = "select r.*,su.username,bi.bookname,bi.price prices from rent_info r inner join sys_user su on r.uid = su.uid inner join books_info bi on r.bid = bi.bid where r.state=1 order by r.createtime desc";
        List<RentInfo> rents = new ArrayList<>();
        try {
            rents = Db.use().query(sql, RentInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rents;
    }


    // 设为租赁中
    public void rentBook(RentInfo info) {
        String sql = "update rent_info set aid=?,price=?,deposit=?,state=2 where rid=?";
        try {
            Db.use().execute(sql, info.getAid(), info.getPrice(), info.getDeposit(), info.getRid());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<RentInfo> getAllInfo() {
        String sql = "select * from rent_info";
        List<RentInfo> rentInfoList = new ArrayList<>();
        try {
            rs = query(sql);
            while (rs.next()) {
                RentInfo rentInfo = new RentInfo();
                rentInfo.setRid(rs.getString("Rid"));
                rentInfo.setUid(rs.getString("Uid"));
                rentInfo.setBid(rs.getString("Bid"));
                rentInfo.setAid(rs.getString("Aid"));
                rentInfo.setCreatetime(rs.getTimestamp("Createtime"));
                rentInfo.setPrice(rs.getBigDecimal("Price"));
                rentInfo.setDeposit(rs.getBigDecimal("Deposit"));
                rentInfo.setEndtime(rs.getTimestamp("Endtime"));
                rentInfo.setAmount(rs.getBigDecimal("Amount"));
                rentInfo.setState(rs.getInt("State"));
                rentInfoList.add(rentInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rentInfoList;
    }

    /**
     * 通过id查找正在租赁期的书
     * @param uid
     * @return
     */
    public List<RentInfo> getRentInfoById(String uid) {
        String sql = "select * from rent_info where uid = ? and state = 2";
        List<RentInfo> rentInfoList = new ArrayList<>();
        try {
            rs = query(sql,uid);
            while (rs.next()) {
                RentInfo rentInfo = new RentInfo();
                rentInfo.setRid(rs.getString("Rid"));
                rentInfo.setUid(rs.getString("Uid"));
                rentInfo.setBid(rs.getString("Bid"));
                rentInfo.setAid(rs.getString("Aid"));
                rentInfo.setCreatetime(rs.getTimestamp("Createtime"));
                rentInfo.setPrice(rs.getBigDecimal("Price"));
                rentInfo.setDeposit(rs.getBigDecimal("Deposit"));
                rentInfo.setEndtime(rs.getTimestamp("Endtime"));
                rentInfo.setAmount(rs.getBigDecimal("Amount"));
                rentInfo.setState(rs.getInt("State"));
                rentInfoList.add(rentInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rentInfoList;
    }

    /**
     * 还书操作将借书记录中的状态设为3(已还)
     * @param rid
     * @return
     */
    public boolean ReturnBook(String rid) {
        String sql = "update rent_info set state = 3 where rid = ?";
        try {
            execute(sql,rid);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

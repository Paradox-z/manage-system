package com.ZYT.library.dao;

import cn.hutool.db.Db;
import com.ZYT.library.entity.RentInfo;
import com.ZYT.library.common.DataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentInfoDao extends DataSource {

    // Add rental record
    public void addRent(RentInfo rent) {
        String sql = "insert into rent_info(rid,uid,bid,createtime,endtime,state) values (uuid(),?,?,now(),DATE_ADD(NOW(), INTERVAL 1 MONTH),1)";
        try {
            Db.use().execute(sql, rent.getUid(), rent.getBid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // List all rental record
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


    // Set the state with rent
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
     * Search books in renting by id.
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
     * Return books would set the state in rental record with 3 (Already return).
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

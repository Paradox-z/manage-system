package com.ZYT.library.dao;

import com.ZYT.library.common.DataSource;
import com.ZYT.library.entity.RechargeRecord;

import java.util.ArrayList;
import java.util.List;

public class RechargeRecordDao extends DataSource {

    // 通过id查找借书记录
    public List<RechargeRecord> findRecodeByUid(String uid){
        String sql="select r.*,su.username " +
                "   from recharge_record r " +
                "    left join sys_user su " +
                "        on r.uid = su.uid " +
                "where r.state<>0 and r.uid=? " +
                "order by r.createtime desc";
        List<RechargeRecord> records =new ArrayList<>();
        try {
            rs=query(sql,new Object[]{uid});
            while(rs.next()){
                RechargeRecord r=new RechargeRecord();
                r.setAid(rs.getString("Aid"));
                r.setUid(rs.getString("Uid"));
                r.setAmount(rs.getBigDecimal("Amount"));
                r.setRemark(rs.getString("Remark"));
                r.setRid(rs.getString("Rid"));
                r.setCreatetime(rs.getTimestamp("Createtime"));
                r.setState(rs.getInt("State"));
                r.setUsername(rs.getString("username"));
                records.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close();
        }
        return records;
    }

    // 添加记录
    public void addRecode(RechargeRecord record){
        String sql="insert into recharge_record(rid, uid, amount, createtime, state, " +
                "aid, remark) VALUES(uuid(),?,?,now(),?,?,null)";
        try {
            execute(sql,record.getUid(),record.getAmount(),record.getState(),record.getAid());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close();
        }

    }

    // 支付押金生成记录
    public void payDeposit(RechargeRecord record){
        String sql="insert into recharge_record(rid, uid, amount, createtime, state, " +
                "aid, remark) VALUES(uuid(),?,?,now(),2,?,null)";
        try {
            execute(sql,record.getUid(),record.getAmount(),record.getAid());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close();
        }
    }

    public void payRent(RechargeRecord record){
        String sql="insert into recharge_record(rid, uid, amount, createtime, state, " +
                "aid, remark) VALUES(uuid(),?,?,now(),2,?,null)";
        try {
            execute(sql,record.getUid(),record.getAmount(),record.getAid());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close();
        }
    }
}

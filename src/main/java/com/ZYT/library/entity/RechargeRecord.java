package com.ZYT.library.entity;

import com.ZYT.library.common.UnExists;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class RechargeRecord {

    private String rid;             //充值记录id
    private String uid;             //用户id
    private BigDecimal amount;      //充值金额
    private Timestamp createtime;   //充值记录创建时间
    private int state;              // 状态
    private String aid;
    private String remark;          // 备注

    @UnExists
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "RechargeRecord{" +
                "rid='" + rid + '\'' +
                ", uid='" + uid + '\'' +
                ", amount=" + amount +
                ", createtime=" + createtime +
                ", state=" + state +
                ", aid='" + aid + '\'' +
                ", remark='" + remark + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

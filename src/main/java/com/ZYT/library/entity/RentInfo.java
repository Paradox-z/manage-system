package com.ZYT.library.entity;

import com.ZYT.library.common.UnExists;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class RentInfo {
    private String rid;
    private String uid;
    private String bid;
    private String aid;
    private Timestamp createtime;    // rent created time
    private BigDecimal price;        // daily rental
    private BigDecimal deposit;      // deposit
    private Timestamp endtime;       // return time
    private BigDecimal amount;       // total rental
    private String remark;           // comment
    private int state;               // state, 0-waste, 1-apply to rent books, 2-borrowing, 3-returned.

    @UnExists
    private String username;

    @UnExists
    private String bookname;

    @UnExists
    private String prices;

    @Override
    public String toString() {
        return "RentInfo{" +
                "rid='" + rid + '\'' +
                ", uid='" + uid + '\'' +
                ", bid='" + bid + '\'' +
                ", aid='" + aid + '\'' +
                ", createtime=" + createtime +
                ", price=" + price +
                ", deposit=" + deposit +
                ", endtime=" + endtime +
                ", amount=" + amount +
                ", remark='" + remark + '\'' +
                ", state=" + state +
                ", username='" + username + '\'' +
                ", bookname='" + bookname + '\'' +
                ", prices='" + prices + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
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

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

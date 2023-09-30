package com.ZYT.library.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BooksInfo {
    private String bid;
    private String bookname;
    private BigDecimal price;
    private String description;
    private String author;
    private Timestamp createtime;
    private int state;

    @Override
    public String toString() {
        return "BooksInfo{" +
                "bid='" + bid + '\'' +
                ", bookname='" + bookname + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", createtime=" + createtime +
                ", state=" + state +
                '}';
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
}

package com.ZYT.library.dao;

import cn.hutool.db.Db;
import com.ZYT.library.entity.BooksInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksInfoDao {

    // Inserting Book information
    public void insert(BooksInfo book){
        String sql="insert into books_info(bid, bookname, price, description, author, createtime, state)" +
                " values (uuid(),?,?,?,?,now(),1)";
        try {
            Db.use().execute(sql,book.getBookname(),book.getPrice(),book.getDescription(),book.getAuthor());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // collect a list for all books, return the array.
    public List<BooksInfo> findForALl(){
        List<BooksInfo> books=new ArrayList<>();
        try {
            books=Db.use().query("select * from books_info where state=1 order by createtime desc",BooksInfo.class,null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

}

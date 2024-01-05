package com.ZYT.library.common;

import java.sql.*;

public class DataSource {

    private String driver = "com.mysql.cj.jdbc.Driver";

    private String url="jdbc:mysql://localhost:3306/hubue?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true";

    private Connection conn;

    private PreparedStatement ps;

    protected ResultSet rs;

    private Connection getConn() throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url,"root","123");
    }

    public void execute(String sql,Object... params) throws Exception {
        conn = getConn();
        ps = conn.prepareStatement(sql);
        if(params!=null&&params.length>0){
            int i=1;
            for(Object o:params){
                ps.setObject(i++,o);
            }
        }
        ps.execute();
    }

    public ResultSet query(String sql,Object... params) throws Exception {
        conn = getConn();
        ps = conn.prepareStatement(sql);
        if(params!=null&&params.length>0){
            int i=1;
            for(Object o:params){
                ps.setObject(i++,o);
            }
        }
        return ps.executeQuery();
    }

    public void close(){
        try {
            if(rs!=null){
                rs.close();
            }
            if(ps!=null){
                ps.close();
            }
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package com.data.dbconnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 7:50
 */
public interface DbConnection {
    public  Connection getConnection(String url, String user, String password);

}

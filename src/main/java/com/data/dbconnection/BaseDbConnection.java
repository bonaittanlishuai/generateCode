package com.data.dbconnection;

import com.data.dbconnection.DbConnection;

import java.sql.*;
import java.util.concurrent.Executor;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 7:52
 */
class BaseDbConnection implements DbConnection {

    @Override
    public Connection getConnection(String url, String user, String password) {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}

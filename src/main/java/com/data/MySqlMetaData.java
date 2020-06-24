package com.data;

import com.data.dbconnection.DbConnection;
import com.data.dbconnection.MysqlDbConnection;

import java.sql.Connection;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 0:51
 */
public class MySqlMetaData extends AbstractMetaData{

    private String url;
    private String user;
    private String password;

    public MySqlMetaData(){

    }

    public MySqlMetaData(String url,String user,String password){
        this.url=url;
        this.user=user;
        this.password=password;
    }

    @Override
    public Connection getConnection() {
        MysqlDbConnection mysqlDbConnection = new MysqlDbConnection();
        Connection connection = mysqlDbConnection.getConnection(url, user, password);
        return connection;
    }

    public MySqlMetaData setUrl(String url) {
        this.url = url;
        return this;
    }

    public MySqlMetaData setUser(String user) {
        this.user = user;
        return this;
    }

    public MySqlMetaData setPassword(String password) {
        this.password = password;
        return this;
    }
}

package com.data;

import com.data.AbstractMetaData;

import java.sql.Connection;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 9:09
 */
public class OracleMetaData extends AbstractMetaData {
    private String url;
    private String user;
    private String password;


    public OracleMetaData(){

    }

    public OracleMetaData(String url,String user,String password){
        this.url=url;
        this.user=user;
        this.password=password;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    public OracleMetaData setUrl(String url) {
        this.url = url;
        return this;
    }

    public OracleMetaData setUser(String user) {
        this.user = user;
        return this;
    }

    public OracleMetaData setPassword(String password) {
        this.password = password;
        return this;
    }
}

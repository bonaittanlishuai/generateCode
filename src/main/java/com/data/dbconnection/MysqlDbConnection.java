package com.data.dbconnection;


/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 7:58
 */
public class MysqlDbConnection extends BaseDbConnection {
    static {
        try {
            System.err.println("------------");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

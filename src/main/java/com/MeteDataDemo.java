package com;

import com.data.dbconnection.MysqlDbConnection;
import com.data.properties.BaseProperties;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-06-24 14:07
 */
public class MeteDataDemo {

    public static void main(String[] args) throws SQLException {
//        BaseProperties baseProperties = new BaseProperties();
//        Properties properites = baseProperties.getProperites();
//        String url = properites.getProperty("url");
//        String user =properites.getProperty("user");
//        String password=properites.getProperty("password");
//        MysqlDbConnection mysqlDbConnection = new MysqlDbConnection();
//        Connection connection = mysqlDbConnection.getConnection(url, user, password);
//        DatabaseMetaData metaData = connection.getMetaData();
//        ResultSet tables = metaData.getTables(null, null, "%app%", new String[]{"TABLE"});
//        while (tables.next()){
//            String tableName = tables.getString("TABLE_NAME");
//            String tableType = tables.getString("TABLE_TYPE");
//            String remarks = tables.getString("REMARKS");
//            System.err.println(tableName);
//            System.err.println(tableType);
//            System.err.println(remarks);
//        }
        System.err.println("\t\n dfdf");

    }
}

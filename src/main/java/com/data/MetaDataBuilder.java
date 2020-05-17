package com.data;

import com.data.enums.DbStateEnum;
import com.data.properties.BaseProperties;

import java.util.List;
import java.util.Properties;

public class MetaDataBuilder {

    private static MetaDataBuilder metaDataBuilder = new MetaDataBuilder();

    public static MetaDataBuilder getInstance() {
        return metaDataBuilder;
    }

    private MetaDataBuilder() {
    }

    private  List<TableData> getMysqlMetaData(Properties properites){
        String url = properites.getProperty("url");
        String user =properites.getProperty("user");
        String password=properites.getProperty("password");
        MetaData metaData=new MySqlMetaData(url, user, password);
        List<TableData> tableData = metaData.getTableData();
        tableData.get(0).setDbState(DbStateEnum.MYSQL.getState());
        return tableData;
    }


    private  List<TableData> getOracleMetaData(Properties properites){
        String url = properites.getProperty("url");
        String user =properites.getProperty("user");
        String password=properites.getProperty("password");
        MetaData metaData=new OracleMetaData(url, user, password);
        List<TableData> tableData = metaData.getTableData();
        tableData.get(0).setDbState(DbStateEnum.ORACLE.getState());
        return tableData;
    }

    public List<TableData> getMetaData(){
        BaseProperties baseProperties = new BaseProperties();
        Properties properites = baseProperties.getProperites();
        emptyValidate(properites);
        String url = properites.getProperty("url");
        if(url.startsWith(DbStateEnum.MYSQL.getState())){
           return getMysqlMetaData(properites);
        }else if(url.startsWith(DbStateEnum.ORACLE.getState())){
           return getOracleMetaData(properites);
        }else{
           return getMysqlMetaData(properites);
        }

    }

    private void emptyValidate(Properties properites) {
        String url = properites.getProperty("url");
        String user =properites.getProperty("user");
        String password=properites.getProperty("password");

        if(url==null){
            throw new NullPointerException("數據庫連接不能為空");
        }
        if(user==null){
            throw new NullPointerException("用戶名不能為空");
        }
        if(password==null){
            throw new NullPointerException("密碼不能為空");
        }
    }

}

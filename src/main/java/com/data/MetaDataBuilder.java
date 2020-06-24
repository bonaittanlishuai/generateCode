package com.data;

import com.data.delegate.MetaDataDelegate;
import com.data.enums.DbStateEnum;
import com.data.properties.BaseProperties;

import java.util.List;
import java.util.Properties;

public class MetaDataBuilder {


    public static MetaDataBuilder getInstance() {
        return InnerClass.metaDataBuilder;
    }

    private MetaDataBuilder() {
        if(InnerClass.metaDataBuilder!=null){
            throw new NullPointerException("该类是单例");
        }
    }

    public List<TableData> getMetaData(){
        BaseProperties baseProperties = new BaseProperties();
        Properties properites = baseProperties.getProperites();
        emptyValidate(properites);
        String dataState=properites.getProperty("dataState");
        String url = properites.getProperty("url");
        String user =properites.getProperty("user");
        String password=properites.getProperty("password");
        MetaData metaData = MetaDataDelegate.getInstance().getMetaData(dataState).setUrl(url).setUser(user).setPassword(password);
        List<TableData> tableData = metaData.getTableData();
        if(tableData.size()==0){
            throw new NullPointerException("表过滤异常tableName 数据库不存在对应的表");
        }
        tableData.get(0).setDbState(dataState);
        return  tableData;
    }

    private void emptyValidate(Properties properites) {
        String url = properites.getProperty("url");
        String user =properites.getProperty("user");
        String password=properites.getProperty("password");
        String dataState=properites.getProperty("dataState");

        if(url==null){
            throw new NullPointerException("数据库链接不能為空");
        }
        if(user==null){
            throw new NullPointerException("用户名不能為空");
        }
        if(password==null){
            throw new NullPointerException("密码不能為空");
        }
        if(dataState==null){
            throw new NullPointerException("数据库声明不能为空");
        }
    }

    private static class InnerClass{
        private static MetaDataBuilder metaDataBuilder = new MetaDataBuilder();
    }

}

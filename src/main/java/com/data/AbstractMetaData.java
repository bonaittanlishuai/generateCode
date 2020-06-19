package com.data;

import com.data.dbconnection.ConnectionRelease;
import com.data.properties.GenerateProperties;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 7:27
 */
public abstract class AbstractMetaData implements MetaData {

    public abstract Connection getConnection();

    @Override
    public List<TableData> getTableData() {
        Connection connection = getConnection();
        DatabaseMetaData meteData = getMeteData(connection);
        List<Map<String,String>> tableInfo = getTableNames(meteData);
        List<TableData> tableDetailInfo = getTableDetailInfo(meteData, tableInfo);
        ConnectionRelease.closeConn(connection);
        //过滤表的数据
        tableDetailInfo=filtrationTable(tableDetailInfo);
        return tableDetailInfo;
    }

    public  List<TableData> filtrationTable(List<TableData> tableDetailInfo){
        //多个以逗号隔开
        String filtrationTableName = getFiltrationTableName();
        if("".equals(filtrationTableName)){
            return tableDetailInfo;
        }
        //是否是模糊匹配
        boolean isLike=false;
        if(filtrationTableName.endsWith("*")){
            filtrationTableName = filtrationTableName.replace("*", "");
            isLike=true;
        }

        List<TableData> newResultList=new LinkedList<>();

        for (TableData tableData : tableDetailInfo) {
            if(isLike){
                if(tableData.getTableName().startsWith(filtrationTableName)){
                    newResultList.add(tableData);
                }
            }else{
                if(filtrationTableName.contains(tableData.getTableName())){
                    newResultList.add(tableData);
                }
            }
        }
        return newResultList;
    }

    private String getFiltrationTableName() {
        Properties properites = GenerateProperties.getProperites();
        Object tableName = properites.get("tableName");
        if(tableName!=null && !"".equals(tableName)){
            String tableNameStr = tableName.toString().trim();
            if(!"*".equals(tableNameStr)){
                return tableNameStr;
            }
        }
        return "";
    }


    private  List<TableData> getTableDetailInfo(DatabaseMetaData meteData, List<Map<String,String>> tableInfos) {
        List<TableData> dataTables = new LinkedList<>();
        TableData table=null;
        TableDetailInfo tableDetailInfo=null;
        try{
            for (Map<String,String> tableInfo : tableInfos) {
                table=new TableData();
                table.setTableName(tableInfo.get("tableName"));
                table.setTableRemark(tableInfo.get("remarks"));
                //4. 提取表内的字段的名字和类型
                String columnName;
                String columnType;
                String columnRemark;
                ResultSet colRet = meteData.getColumns(null, "%", table.getTableName(), "%");
                LinkedList<TableDetailInfo> tableDetailInfos = new LinkedList<>();
                while (colRet.next()) {
                    tableDetailInfo=new  TableDetailInfo();
                    columnName = colRet.getString("COLUMN_NAME");
                    columnType = colRet.getString("TYPE_NAME");
                    columnRemark = colRet.getString("REMARKS");
                    int datasize = colRet.getInt("COLUMN_SIZE");
                    int digits = colRet.getInt("DECIMAL_DIGITS");
                    int nullable = colRet.getInt("NULLABLE");
                    tableDetailInfo.COLUMN_NAME=columnName;
                    tableDetailInfo.COLUMN_TYPE=columnType;
                    tableDetailInfo.COLUMN_SIZE=datasize;
                    tableDetailInfo.DECIMAL_DIGITS=digits;
                    tableDetailInfo.NULLABLE=nullable;
                    tableDetailInfo.columnRemark=columnRemark;
                    tableDetailInfos.add(tableDetailInfo);
                }
                table.setTableDetailInfo(tableDetailInfos);
                dataTables.add(table);
            }
            return dataTables;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private List<Map<String,String>> getTableNames(DatabaseMetaData meteData) {
        try {
            ResultSet rs = meteData.getTables(null, "%", "%", new String[]{"TABLE"});
            List<Map<String,String>> tableNames = new LinkedList<>();
            while (rs.next()) {
                HashMap<String, String> map = new HashMap<>();
                String tableName = rs.getString("TABLE_NAME");
                String tableType = rs.getString("TABLE_TYPE");
                String remarks = rs.getString("REMARKS");
                if (tableType.equalsIgnoreCase("table")){
                    map.put("tableName",tableName);
                    map.put("remarks",remarks);
                    tableNames.add(map);
                }
            }
            return tableNames;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    private DatabaseMetaData getMeteData(Connection connection) {
        try {
            return connection.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

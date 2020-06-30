package com.data.strategy;

import com.data.strategy.entity.FieldType;
import com.data.enums.DbStateEnum;
import com.data.enums.MysqlFieldTypeEnum;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Description 类型委派
 * @Author tanlishuai
 * @Date 2020-06-24 8:57
 */
public class FieldTypeStrategy {
    /**
     * key 是  数据库标识
     */
    private static Map<String,List<FieldType>> container=new HashMap<String,List<FieldType>>();

    static{
        List<FieldType> mysqlFiledList=new LinkedList<>();
        FieldType fieldType=null;
        for (MysqlFieldTypeEnum mysqlFieldTypeEnum : MysqlFieldTypeEnum.values()) {
            fieldType = new FieldType();
            fieldType.setSimpleType(mysqlFieldTypeEnum.getSimpleType());
            fieldType.setDataType(mysqlFieldTypeEnum.getDataType());
            fieldType.setIsPermitImport(mysqlFieldTypeEnum.getIsPermitImport());
            fieldType.setJavaType(mysqlFieldTypeEnum.getJavaType());
            mysqlFiledList.add(fieldType);
        }

        List<FieldType> oracleFiledList=new LinkedList<>();
        for (MysqlFieldTypeEnum mysqlFieldTypeEnum : MysqlFieldTypeEnum.values()) {
            fieldType = new FieldType();
            fieldType.setSimpleType(mysqlFieldTypeEnum.getSimpleType());
            fieldType.setDataType(mysqlFieldTypeEnum.getDataType());
            fieldType.setIsPermitImport(mysqlFieldTypeEnum.getIsPermitImport());
            fieldType.setJavaType(mysqlFieldTypeEnum.getJavaType());
            mysqlFiledList.add(fieldType);
        }
        container.put(DbStateEnum.MYSQL.getState(), mysqlFiledList);
        container.put(DbStateEnum.ORACLE.getState(),oracleFiledList);
    }

    private FieldTypeStrategy(){
        if(InnerClass.fieldTypeStrategy!=null){
            throw new NullPointerException("不能通过反射创建该类");
        }
    }


    public static FieldTypeStrategy getInstance(){
        return InnerClass.fieldTypeStrategy;
    }
    /**
     * 获得 字段类型
     * @param key
     * @return
     */
    public  List<FieldType> getFieldTypeList(String key){
        return container.get(key);
    }


    private static class InnerClass{
        private static FieldTypeStrategy fieldTypeStrategy=new FieldTypeStrategy();
    }
}

package com.data.strategy;

import com.data.MetaData;
import com.data.MySqlMetaData;
import com.data.OracleMetaData;
import com.data.enums.DbStateEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-06-24 14:38
 */
public class MetaDataStrategy {
    /**
     *  DbStateEnum 的key 作为 key
     */
    private static Map<String,MetaData> container=new HashMap<>();

    static{
        container.put(DbStateEnum.MYSQL.getState(),new MySqlMetaData());
        container.put(DbStateEnum.ORACLE.getState(),new OracleMetaData());
    }

    private MetaDataStrategy(){
        if(InnerClass.metaDataStrategy!=null){
            throw new NullPointerException("不能通过反射创建该类");
        }
    }

    public static MetaDataStrategy getInstance(){
        return InnerClass.metaDataStrategy;
    }

    public MetaData getMetaData(String key){
        if(container.containsKey(key)){
            return container.get(key);
        }
        //默认是mysql数据库
        return container.get(DbStateEnum.MYSQL.getState());
    }

    private static class InnerClass{
        private static MetaDataStrategy metaDataStrategy=new MetaDataStrategy();
    }
}

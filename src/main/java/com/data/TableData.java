package com.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-08 23:27
 */
@Data
public class TableData {
    /**
     * 是什么数据库的标识
     */
    private String dbState;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表的注释
     */
    private String tableRemark;
    /**
     * 表的字段信息
     */
    private List<TableDetailInfo> tableDetailInfo;


}

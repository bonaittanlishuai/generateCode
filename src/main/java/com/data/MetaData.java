package com.data;

import java.util.List;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 7:25
 */
public interface MetaData {

    public List<TableData> getTableData();

    public MetaData setUrl(String url) ;

    public MetaData setUser(String user);

    public MetaData setPassword(String password) ;
}

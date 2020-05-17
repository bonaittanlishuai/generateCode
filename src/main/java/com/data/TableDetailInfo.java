package com.data;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 12:47
 */
@Data
public class TableDetailInfo {

    public String COLUMN_NAME;
    public String COLUMN_TYPE;
    public Integer COLUMN_SIZE;
    public Integer DECIMAL_DIGITS;
    public Integer NULLABLE;
    /**
     * 类的注释
     */
    public String columnRemark;


}

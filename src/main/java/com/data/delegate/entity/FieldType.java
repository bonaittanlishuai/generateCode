package com.data.delegate.entity;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-06-24 9:21
 */
public class FieldType {

    private String dataType;
    private String javaType;
    private String simpleType;
    //对应类型是否需要导包  0 需要  1需要
    private int isPermitImport;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getSimpleType() {
        return simpleType;
    }

    public void setSimpleType(String simpleType) {
        this.simpleType = simpleType;
    }

    public int getIsPermitImport() {
        return isPermitImport;
    }

    public void setIsPermitImport(int isPermitImport) {
        this.isPermitImport = isPermitImport;
    }
}

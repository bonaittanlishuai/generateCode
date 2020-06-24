package com.data.enums;

import com.data.PathUtils;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-06-24 10:37
 */
public enum GenerateInfoInitEnum {

    CONTROLLERPACKAGE("controllerPackage","com.framework.controller"),
    SERVICEPACKAGE("servicePackage","com.framework.service"),
    SERVICEIMPLPACKAGE("serviceImplPackage","com.framework.service.impl"),
    ENTITYPACKAGE("entityPackage","com.framework.entity"),
    DAOPACKAGE("daoPackage","com.framework.dao"),
    DAOIMPLPACKAGE("daoImplPackage","com.framework.dao.impl"),
    MAPPERFILEPATH("mapperFilePath","/mapper"),
    GENERATEDAOBEANXMLFILEDIR("generateDaoBeanXmlFileDir","/spring/dao"),
    GENERATESERVICEBEANXMLFILEDIR("mapperFilePath","/spring/service"),

    ;
    private String key;
    private String value;
    GenerateInfoInitEnum(String key,String value){
        this.key=key;
        this.value=value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

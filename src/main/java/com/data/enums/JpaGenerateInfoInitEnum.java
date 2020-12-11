package com.data.enums;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-06-24 10:37
 */
public enum JpaGenerateInfoInitEnum {

    controllerpackage("controllerPackage","com.framework.controller"),
    servicepackage("servicePackage","com.framework.service"),
    serviceimplpackage("serviceImplPackage","com.framework.service.impl"),
    entitypackage("entityPackage","com.framework.entity"),
    modelpackage("modelPackage","com.framework.model"),
    mapperpackage("mapperPackage","com.framework.mapper"),
    repositoryPackage("repositoryPackage","com.framework.repository"),
    GENERATESERVICEBEANXMLFILEDIR("mapperFilePath","/spring/service"),

    ;
    private String key;
    private String value;
    JpaGenerateInfoInitEnum(String key, String value){
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

package com.template;


import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 12:11
 */
@Data
public class TemplateData {
    /**
     * //類名
     */
    private String className;
    private String pathName;
    private String firstLowerCaseClassName;
    private String firstUpperCaseClassName;
    private String classAlias;
    /**
     * //表的注释
     */
    private String tableRemark;
    /**
     * //表名
     */
    private String tableName;
    /**
     * 包
     */
    private String controllerPackage;
    private String serviceImplPackage;
    private String servicePackage;
    private String entityPackage;
    private String daoPackage;
    private String daoPackageImpl;
    /**
     * 类名
     */
    private String controllerClassName;
    private String serviceImplClassName;
    private String serviceClassName;
    private String entityClassName;
    private String daoClassName;
    private String daoImplClassName;
    /**
     * 别名
     */
    private String controllerAliasName;
    private String serviceImplAliasName;
    private String serviceAliasName;
    private String entityAliasName;
    private String daoAliasName;
    private String daoImplAliasName;

    /**
     * //表的字段信息
     */
    private List<DetailInfo> tableDetailInfos;

    @Data
   public class  DetailInfo {
       private String columnName; //表字段名称
       private String columnType;//表字段类型
       private String fieldName;//字段名 驼峰了
       private String fieldType; //java字段类型的全名
       private String fieldSimpleType;//java类型
       private String firstUpperCaseFieldName;//字段首字母大写
       private String getMethodName;//get方法
       private String setMethodName;//set方法
       private String columnRemark;//列的注释

    }



}

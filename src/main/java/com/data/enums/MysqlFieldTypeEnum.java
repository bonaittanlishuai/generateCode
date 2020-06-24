package com.data.enums;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 13:05
 */
public enum MysqlFieldTypeEnum {
    VARCHAR("VARCHAR","java.lang.String","String",0),
    INT("INT","java.lang.Integer","Integer",0),
    INGEGER("INGEGER","java.lang.Integer","Integer",0),
    CHAR("CHAR","java.lang.String","String",0),
    BLOB("BLOB","java.sql.Blob","Blob",1),
    TEXT("TEXT","java.lang.String","String",0),
    INTEGER("INTEGER","java.lang.Long","Long",0),
    TINYINT("TINYINT","java.lang.Byte","Byte",0),
    SMALLINT("SMALLINT","java.lang.Short","Short",0),
    MEDIUMINT("MEDIUMINT","java.lang.Integer","Integer",0),
    BIT("BIT","java.lang.Boolean","Boolean",0),
    BIGINT("BIGINT","java.lang.Long","Long",0),
    FLOAT("FLOAT","java.lang.Float","Float",0),
    DOUBLE("DOUBLE","java.lang.Double","Double",0),
    DECIMAL("DECIMAL","java.math.BigDecimal","BigDecimal",1),
    NUMERIC("NUMERIC","java.math.BigDecimal","BigDecimal",1),
    BOOLEAN("BOOLEAN","java.lang.Integer","Integer",0),
    ID("ID","java.lang.Long","Long",0),
    DATE("DATE","java.util.Date","Date",1),
    TIME("TIME","java.sql.Time","Time",1),
    DATETIME("DATETIME","java.sql.Timestamp","Timestamp",1),
    TIMESTAMP("TIMESTAMP","java.util.Calendar","Calendar",1),
    YEAR("YEAR","java.util.Date","Date",1),
    LONGTEXT("LONGTEXT","java.lang.String","String",0),
    VARBINARY("VARBINARY","java.io.Serializable","Serializable",1),
    CLOB("CLOB","java.sql.Clob","Clob",1),
    LONGBLOB("LONGBLOB","Byte []","Byte []",0),
    MEDIUMTEXT("MEDIUMTEXT","java.lang.String","String",0),
    ;
    private String dataType;
    private String javaType;
    private String simpleType;
    //对应类型是否需要导包  0 需要  1需要
    private int isPermitImport;
    MysqlFieldTypeEnum(String dataType,String javaType,String simpleType,int isPermitImport){
        this.dataType=dataType;
        this.javaType=javaType;
        this.simpleType=simpleType;
        this.isPermitImport=isPermitImport;
    }

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

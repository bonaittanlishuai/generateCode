package com.data.enums;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 13:05
 */
public enum MysqlFieldTypeEnum {
    VARCHAR("VARCHAR","java.lang.String","String"),
    INT("INT","java.lang.Integer","Integer"),
    CHAR("CHAR","java.lang.String","String"),
    BLOB("BLOB","java.lang.Byte","Byte"),
    TEXT("TEXT","java.lang.String","String"),
    INTEGER("INTEGER","java.lang.Long","Long"),
    TINYINT("TINYINT","java.lang.Integer","Integer"),
    SMALLINT("SMALLINT","java.lang.Integer","Integer"),
    MEDIUMINT("MEDIUMINT","java.lang.Integer","Integer"),
    BIT("BIT","java.lang.Boolean","Boolean"),
    BIGINT("BIGINT","java.math.BigInteger","BigInteger"),
    FLOAT("FLOAT","java.lang.Float","Float"),
    DOUBLE("DOUBLE","java.lang.DOUBLE","DOUBLE"),
    DECIMAL("DECIMAL","java.math.BigDecimal","BigDecimal"),
    BOOLEAN("BOOLEAN","java.lang.Integer","Integer"),
    ID("ID","java.lang.Long","Long"),
    DATE("DATE","java.sql.Date","Date"),
    TIME("TIME","java.sql.Time","Time"),
    DATETIME("DATETIME","java.sql.Timestamp","Timestamp"),
    TIMESTAMP("TIMESTAMP","java.sql.Timestamp","Timestamp"),
    YEAR("YEAR","java.sql.Date","Date"),
    ;
    private String dataType;
    private String javaType;
    private String simpleType;
    MysqlFieldTypeEnum(String dataType,String javaType,String simpleType){
        this.dataType=dataType;
        this.javaType=javaType;
        this.simpleType=simpleType;
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
}

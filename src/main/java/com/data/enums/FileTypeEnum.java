package com.data.enums;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 16:05
 */
public enum  FileTypeEnum {

    CONTOLLER("controller","控制层的包","Controller"),
    SERVICE("service","service的包","Service"),
    SERVICEIMP("serviceimp","serviceImp","ServiceImp"),
    ENTITY("entity","entity",""),
    MAPPER("mapper","控制层的包","Mapper"),
    MAPPERXML("mapperxml","xml","Mapper"),
    ;
    private String key;
    private String description;
    /**
     * //生成文件别名后缀
     */
    private String fiewNameSuffix;
    FileTypeEnum(String key,String description,String fiewNameSuffix){
        this.key=key;
        this.description=description;
        this.fiewNameSuffix=fiewNameSuffix;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getFiewNameSuffix() {
        return fiewNameSuffix;
    }

    public void setFiewNameSuffix(String fiewNameSuffix) {
        this.fiewNameSuffix = fiewNameSuffix;
    }
}

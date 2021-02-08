package com.data.enums.jpa;

/**
 * @author tls
 * @version 1.0
 * @date 2021/2/8 10:10
 */
public enum  JpaFileTypeEnum {
    CONTOLLER("controller","控制层的包","Controller"),
    SERVICE("service","service的包","Service"),
    SERVICEIMP("serviceimpl","serviceImpl","ServiceImpl"),
    ENTITY("entity","entity",""),
    MAPPER("mapper","实体与dto相互转化","Mapper"),
    MODEL("model","模型dto","DTO"),
    REPOSITORY("repository","jap数据库","Repository"),
    // 前端文件
    VIEWS_LIST("view_list","list","-list.component"),
    VIEWS_TREE("view_tree","tree","-tree.component"),
    VIEW_EDITER("view_editer","editer","-editer.component"),
    VIEW_SERVER("view_service","前端service","-service"),
    VIEW_MODEL("view_model","前端model","-dto"),
    ;
    //记得都是小写
    private String key;
    private String description;
    /**
     * //生成文件别名后缀
     */
    private String fileNameSuffix;
    JpaFileTypeEnum(String key,String description,String fileNameSuffix){
        this.key=key;
        this.description=description;
        this.fileNameSuffix=fileNameSuffix;
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

    public String getFileNameSuffix() {
        return fileNameSuffix;
    }

    public void setFileNameSuffix(String fileNameSuffix) {
        this.fileNameSuffix = fileNameSuffix;
    }
}

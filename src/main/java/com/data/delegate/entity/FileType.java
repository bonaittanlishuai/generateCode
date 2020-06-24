package com.data.delegate.entity;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-06-24 9:59
 */
public class FileType {

    //记得都是小写
    private String key;
    private String description;
    /**
     * //生成文件别名后缀
     */
    private String fileNameSuffix;

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

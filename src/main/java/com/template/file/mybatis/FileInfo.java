package com.template.file.mybatis;

import lombok.Data;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 10:47
 */
@Data
public class FileInfo {
    /**
     * 生成文件的名
     */
    private String fileNewName;
    private String fileName;
    private String dir;
    private String suffix;
    /**
     * 模板文件名称
     */
    private String fileNameIncludeSuffix;
    /**
     * /_java _xml 等等  flt文件的名稱以什麼結尾就生成什麼類型的文件
     */
    private String generateFileType;
    /**
     * //生成文件的根目录
     */
    private String generateFileDir;
    private String controllerFilePath;
    private String serviceImplFilePath;
    private String serviceFilePath;
    private String entityFilePath;
    private String daoFilePath;
    private String daoImpleFilePath;
    private String mapperFilePath;
    /**
     * 该文件是什么类型文件 controller service serviceimp
     */
    private String state;

}

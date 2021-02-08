package com.template.mybatis;


import com.data.PathUtils;
import com.template.AbstractTempalteBuilder;
import com.template.TemplateData;
import com.template.file.mybatis.FileInfo;
import com.template.file.mybatis.MybatisTemplateFile;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Description 用mybatis生成的
 * @Author tanlishuai
 * @Date 2020-05-09 9:20
 */
public class FreemarkerBuilder extends AbstractTempalteBuilder {

    /**
     * 思路  通過類路徑   獲取模板的位置 然後將數據注入
     * @throws IOException
     * @throws TemplateException
     */
    @Override
    public void generateTemplate() throws IOException, TemplateException {
        Version version = new Version("2.3.23");
        Configuration configuration = new Configuration(version);
        String ftlDir=PathUtils.getSystemRootPath()+ MybatisTemplateFile.MYBATIS_DEFAULT_DIR;
        configuration.setDirectoryForTemplateLoading(new File(ftlDir));
        //生成模板所需要的數據
        List<TemplateData> templateDatas = getTemplateData();

        //獲取指定目錄下面的所有ftl文件
        MybatisTemplateFile templateFile = new MybatisTemplateFile();
        List<FileInfo> appointDirFileNames = templateFile.getAppointDirFileName();
        //设置生成文件的名称
        for (TemplateData templateData : templateDatas) {
            String className=templateData.getClassName();
            for (int i = 0; i < appointDirFileNames.size(); i++) {
                FileInfo fileInfo = appointDirFileNames.get(i);
                setNewFileName(fileInfo,className);
                Template template =
                        configuration.getTemplate(fileInfo.getFileNameIncludeSuffix());
                String rootDir = fileInfo.getGenerateFileDir();
                //专属目录
                String exclusiveDir = super.getExclusiveDir(fileInfo);
                //生成文件
                createFile(rootDir,exclusiveDir,template,fileInfo,templateData);
            }
        }
        specialGenerateTemplate(templateDatas);
    }

    /**
     * 指定的模板，留给子类实现
     */
    protected void specialGenerateTemplate(List<TemplateData> templateDatas){
    }

}

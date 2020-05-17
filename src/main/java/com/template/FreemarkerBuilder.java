package com.template;


import com.data.MetaDataBuilder;
import com.data.PathUtils;
import com.data.TableData;
import com.data.TableDetailInfo;
import com.data.enums.DbStateEnum;
import com.data.enums.FileTypeEnum;
import com.data.enums.MysqlFieldTypeEnum;
import com.data.properties.GenerateProperties;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 9:20
 */
public class FreemarkerBuilder extends AbstractTempalteBuilder{

    /**
     * 思路  通過類路徑   獲取模板的位置 然後將數據注入
     * @throws IOException
     * @throws TemplateException
     */
    @Override
    public void generateTemplate() throws IOException, TemplateException {
        Properties properites = GenerateProperties.getProperites();
        generateInfoCheckAndInit(properites);
        Configuration configuration = new Configuration();
        String ftlDir=PathUtils.getSystemRootPath()+TemplateFile.DEFAULT_DIR;
        configuration.setDirectoryForTemplateLoading(new File(ftlDir));
        //生成模板所需要的數據
        List<TemplateData> templateDatas = getTemplateData();

        //獲取指定目錄下面的所有ftl文件
        TemplateFile templateFile = new TemplateFile();
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
                String exclusiveDir = getExclusiveDir(fileInfo);
                //生成文件
                createFile(rootDir,exclusiveDir,template,fileInfo,templateData);
            }
        }
    }




}

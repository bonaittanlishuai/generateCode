package com.template.jpa;

import com.data.PathUtils;
import com.data.enums.FileTypeEnum;
import com.data.enums.jpa.JpaFileTypeEnum;
import com.data.properties.GenerateProperties;
import com.data.properties.JpaGenerateProperties;
import com.template.AbstractTempalteBuilder;
import com.template.TemplateData;
import com.template.file.jpa.JpaFileInfo;
import com.template.file.jpa.JpaTemplateFile;
import com.template.file.mybatis.FileInfo;
import com.template.file.mybatis.MybatisTemplateFile;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Properties;

/**
 * @author tls
 * @version 1.0
 * @date 2021/2/8 13:56
 */
public class JpaFreemarkerBuilder extends AbstractTempalteBuilder {
    @Override
    public void generateTemplate() throws IOException, TemplateException {
        //获取模板所需要的数据
        List<TemplateData> templateDatas = getTemplateData();
        //獲取指定目錄下面的所有ftl文件
        JpaTemplateFile templateFile = new JpaTemplateFile();
        List<JpaFileInfo> appointDirFileNames = templateFile.getAppointDirFileName();
        for (JpaFileInfo fileInfo : appointDirFileNames) {
            Version version = new Version("2.3.23");
            Configuration configuration = new Configuration(version);
            configuration.setDirectoryForTemplateLoading(new File(fileInfo.getDir()));

            for (TemplateData templateData : templateDatas) {
                String className = templateData.getClassName();
                setNewFileName(fileInfo, className);
                Template template =
                        configuration.getTemplate(fileInfo.getFileNameIncludeSuffix());
                String rootDir = fileInfo.getGenerateFileDir();
                //专属目录
                String exclusiveDir = fileInfo.getExclusiveDir();
                //生成文件
                createFile(rootDir, exclusiveDir, template, fileInfo, templateData);
            }
        }
    }

    protected void setPackageClassName(TemplateData templateData) {
        templateData.setControllerClassName(templateData.getClassName()+ JpaFileTypeEnum.CONTOLLER.getFileNameSuffix());
        templateData.setServiceClassName(templateData.getClassName()+JpaFileTypeEnum.SERVICE.getFileNameSuffix());
        templateData.setServiceImplClassName(templateData.getClassName()+JpaFileTypeEnum.SERVICEIMP.getFileNameSuffix());
        templateData.setEntityClassName(templateData.getClassName()+JpaFileTypeEnum.ENTITY.getFileNameSuffix());
        templateData.setModelClassName(templateData.getClassName()+JpaFileTypeEnum.MODEL.getFileNameSuffix());
        templateData.setMapperClassName(templateData.getClassName()+JpaFileTypeEnum.MAPPER.getFileNameSuffix());
        templateData.setRepositoryClassName(templateData.getClassName()+JpaFileTypeEnum.REPOSITORY.getFileNameSuffix());
    }


    protected void setNewFileName(JpaFileInfo fileInfo, String className) {
        for (JpaFileTypeEnum fileTypeEnum : JpaFileTypeEnum.values()) {
            if (fileTypeEnum.getKey().equals(fileInfo.getState())) {
                fileInfo.setFileNewName(className + fileTypeEnum.getFileNameSuffix());
                break;
            }
        }
    }


    protected void setPackageInfo(TemplateData templateData) {
        Properties properites = JpaGenerateProperties.getProperites();
        String controllerPackage = properites.getProperty("controllerPackage");
        String servicePackage = properites.getProperty("servicePackage");
        String serviceImplPackage = properites.getProperty("serviceImplPackage");
        String entityPackage = properites.getProperty("entityPackage");
        String modelPackage = properites.getProperty("modelPackage");
        String mapperPackage = properites.getProperty("mapperPackage");
        String repositoryPackage = properites.getProperty("repositoryPackage");
        templateData.setControllerPackage(controllerPackage);
        templateData.setServicePackage(servicePackage);
        templateData.setServiceImplPackage(serviceImplPackage);
        templateData.setEntityPackage(entityPackage);
        templateData.setModelPackage(modelPackage);
        templateData.setMapperPackage(mapperPackage);
        templateData.setRepositoryPackage(repositoryPackage);
    }

    protected void setAliasName(TemplateData templateData) {
        templateData.setClassAlias(templateData.getFirstLowerCaseClassName());
        templateData.setControllerAliasName(templateData.getFirstLowerCaseClassName()+JpaFileTypeEnum.CONTOLLER.getFileNameSuffix());
        templateData.setServiceAliasName(templateData.getFirstLowerCaseClassName()+JpaFileTypeEnum.SERVICE.getFileNameSuffix());
        templateData.setServiceImplAliasName(templateData.getFirstLowerCaseClassName()+JpaFileTypeEnum.SERVICEIMP.getFileNameSuffix());
        templateData.setEntityAliasName(templateData.getFirstLowerCaseClassName()+JpaFileTypeEnum.ENTITY.getFileNameSuffix());
        templateData.setModelAliasName(templateData.getFirstLowerCaseClassName()+JpaFileTypeEnum.MODEL.getFileNameSuffix());
        templateData.setMapperAliasName(templateData.getFirstLowerCaseClassName()+JpaFileTypeEnum.MAPPER.getFileNameSuffix());
        templateData.setRepositoryAliasName(templateData.getFirstLowerCaseClassName()+JpaFileTypeEnum.REPOSITORY.getFileNameSuffix());
    }
}
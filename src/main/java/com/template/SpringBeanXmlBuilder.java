package com.template;

import com.data.enums.FileTypeEnum;
import com.data.properties.GenerateProperties;

import java.io.*;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * @Description  生成 bean xml文件
 * @Author tanlishuai
 * @Date 2020-06-22 8:47
 */
public class SpringBeanXmlBuilder extends FreemarkerBuilder {
    /**
     * 换行
     */
    private  String lineFeed="\n";
    /**
     * 制表符
     */
    private  String tab="\t";
    /**
     * 双引号
     */
    private String doubleMark="\"";

    public void specialGenerateTemplate(List<TemplateData> templateDatas){
        if(templateDatas==null || templateDatas.size()==0)return;
        //目前生成dao的
        String daoTemplateStr = getDaoTemplateStr(templateDatas);
        createDaoXmlFile(daoTemplateStr);
        //生成  servie beanxml  的
        String serviceTemplateStr = getServiceTemplateStr(templateDatas);

        createServiceXmlFile(serviceTemplateStr);

        //  controller beanxml 暂时不生成


    }

    private void createServiceXmlFile(String serviceTemplateStr) {
        Properties properites = GenerateProperties.getProperites();
        String  rootDir= properites.getProperty("generateFileDir");
        String  serviceExclusiveDir= properites.getProperty("generateServiceBeanXmlFileDir");
        String  fileName= properites.getProperty("serviceBeanXmlFileName");
        String fileDir = getCreateFileDir(rootDir,serviceExclusiveDir);
        createXmlFile(fileDir,fileName,serviceTemplateStr);
    }

    private String getServiceTemplateStr(List<TemplateData> templateDatas) {
        StringBuffer template = new StringBuffer();
        template.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        template.append(lineFeed);
        template.append("<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN 2.0//EN\" \"http://www.springframework.org/dtd/spring-beans-2.0.dtd\">");
        template.append(lineFeed);
        template.append("<beans>");
        template.append(lineFeed);
        //循环
        int index=0;
        for (TemplateData templateData : templateDatas) {
            //换行
            if(index!=0){
                template.append(lineFeed);
            }
            //service bean名用 接口的小写
            String beanIdName = templateData.getServiceAliasName();
            String packageName= templateData.getServiceImplPackage()+"."+templateData.getServiceImplClassName();
            String daoBeanName = templateData.getDaoAliasName();
            template.append(tab);
            template.append("<bean id="+doubleMark+beanIdName+doubleMark+tab+"class="+doubleMark+packageName+doubleMark +">");
            template.append(lineFeed);
            template.append(tab+tab+"<constructor-arg  ref=\""+daoBeanName+"\" />");
            template.append(lineFeed);
            template.append(tab);
            template.append("</bean>");
            index++;
        }
        template.append(lineFeed);
        template.append("</beans>");
        String reusltStr=template.toString();
        System.err.println("service:"+reusltStr);
        return reusltStr;
    }


    private void createDaoXmlFile(String daoTemplateStr) {
        Properties properites = GenerateProperties.getProperites();
        String  rootDir= properites.getProperty("generateFileDir");
        String  daoExclusiveDir= properites.getProperty("generateDaoBeanXmlFileDir");
        String  fileName= properites.getProperty("daoBeanXmlFileName");
        String fileDir = getCreateFileDir(rootDir,daoExclusiveDir);
        createXmlFile(fileDir,fileName,daoTemplateStr);
    }

    private void createXmlFile(String fileDir,String fileName ,String templateStr) {
        if(fileName==null || "".equals(fileName)){
            fileName= UUID.randomUUID().toString();
        }
        fileName+=".xml";
        if(!fileDir.endsWith(File.separator) && !fileName.startsWith(File.separator)){
            fileName=File.separator+fileName;
        }
        File file = new File(fileDir+fileName);
        boolean exists = file.exists();
        FileOutputStream fileOutputStream=null;
        OutputStreamWriter outputStreamWriter=null;
        try{
            if(!exists){
                file.createNewFile();
            }
            //new OutStr
            fileOutputStream = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(templateStr);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if(outputStreamWriter!=null){
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getCreateFileDir(String rootDir,String exclusiveDir) {
        StringBuffer sb=new StringBuffer();
        sb.append(rootDir);
        if(!rootDir.endsWith(File.separator) && !exclusiveDir.startsWith(File.separator)){
            sb.append(File.separator);
        }
        sb.append(exclusiveDir);
        File fileDir = new File(sb.toString());
        //不存在就创建目录
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
        return sb.toString();
    }



    private String getDaoTemplateStr(List<TemplateData> templateDatas) {
        StringBuffer template = new StringBuffer();
        template.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        template.append(lineFeed);
        template.append("<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN 2.0//EN\" \"http://www.springframework.org/dtd/spring-beans-2.0.dtd\">");
        template.append(lineFeed);
        template.append("<beans>");
        template.append(lineFeed);
        //循环
        int index=0;
        for (TemplateData templateData : templateDatas) {
            //换行
            if(index!=0){
                template.append(lineFeed);
            }
            //dao bean名用 接口的小写
            String beanIdName = templateData.getDaoAliasName();
            String packageName= templateData.getDaoImplPackage()+"."+templateData.getDaoImplClassName();
            template.append(tab);
            template.append("<bean id="+doubleMark+beanIdName+doubleMark+tab+"class="+doubleMark+packageName+doubleMark +">");
            template.append(lineFeed);
            template.append(tab+tab+"<constructor-arg  ref=\"jdbcTemplate\" />");
            template.append(lineFeed);
            template.append(tab);
            template.append("</bean>");
            index++;
        }
        template.append(lineFeed);
        template.append("</beans>");
        String reusltStr=template.toString();
        System.err.println("dao:"+reusltStr);
        return reusltStr;

    }
}

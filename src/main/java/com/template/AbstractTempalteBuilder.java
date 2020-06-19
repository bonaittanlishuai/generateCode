package com.template;

import com.data.MetaDataBuilder;
import com.data.PathUtils;
import com.data.TableData;
import com.data.TableDetailInfo;
import com.data.enums.DbStateEnum;
import com.data.enums.FileTypeEnum;
import com.data.enums.MysqlFieldTypeEnum;
import com.data.properties.GenerateProperties;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-17 14:49
 */
public abstract class AbstractTempalteBuilder {

    public  abstract void  generateTemplate()throws IOException, TemplateException ;


    protected void setPackageClassName(TemplateData templateData) {
        templateData.setControllerClassName(templateData.getClassName()+ FileTypeEnum.CONTOLLER.getFiewNameSuffix());
        templateData.setServiceClassName(templateData.getClassName()+FileTypeEnum.SERVICE.getFiewNameSuffix());
        templateData.setServiceImplClassName(templateData.getClassName()+FileTypeEnum.SERVICEIMP.getFiewNameSuffix());
        templateData.setEntityClassName(templateData.getClassName()+FileTypeEnum.ENTITY.getFiewNameSuffix());
        templateData.setDaoClassName(templateData.getClassName()+FileTypeEnum.MAPPER.getFiewNameSuffix());
    }

    protected void createFile(String rootDir, String exclusiveDir, Template template , FileInfo fileInfo, TemplateData templateData) throws IOException, TemplateException {
        if(!rootDir.endsWith(File.separator) && !exclusiveDir.startsWith(File.separator)){
            rootDir=rootDir+File.separator;
        }
        File dir = new File(rootDir + exclusiveDir );
        String  fileName= fileInfo.getFileNewName()!=null?fileInfo.getFileNewName():fileInfo.getFileName() + "." + fileInfo.getGenerateFileType()!=null?fileInfo.getGenerateFileType():"txt";
        boolean exists = dir.exists();
        File fileOut=null;
        if(dir.mkdirs() && !exists ){
            String file = dir.getAbsolutePath()+File.separator + fileName+"."+fileInfo.getGenerateFileType();
            fileOut = new File(file);
            if( fileOut.isFile() && !fileOut.exists() ){
                fileOut.createNewFile();
            }
        }else{
            fileOut = new File(dir.getAbsolutePath() +File.separator + fileName+"."+fileInfo.getGenerateFileType());
        }
        Writer out = new FileWriter(fileOut);
        //6.生成
        template.process(templateData,out);
        System.err.println(fileOut);
        //7.关闭流
        out.close();
    }

    protected void setNewFileName(FileInfo fileInfo, String className) {

        if(FileTypeEnum.CONTOLLER.getKey().equals(fileInfo.getState())){
            fileInfo.setFileNewName(className+FileTypeEnum.CONTOLLER.getFiewNameSuffix());
        }else if(FileTypeEnum.SERVICEIMP.getKey().equals(fileInfo.getState())){
            fileInfo.setFileNewName(className+FileTypeEnum.SERVICEIMP.getFiewNameSuffix());
        }else if(FileTypeEnum.SERVICE.getKey().equals(fileInfo.getState())){
            fileInfo.setFileNewName(className+FileTypeEnum.SERVICE.getFiewNameSuffix());
        }else if(FileTypeEnum.ENTITY.getKey().equals(fileInfo.getState())){
            fileInfo.setFileNewName(className+FileTypeEnum.ENTITY.getFiewNameSuffix());
        }else if(FileTypeEnum.MAPPER.getKey().equals(fileInfo.getState())){
            fileInfo.setFileNewName(className+FileTypeEnum.MAPPER.getFiewNameSuffix());
        }else if(FileTypeEnum.MAPPERXML.getKey().equals(fileInfo.getState())){
            fileInfo.setFileNewName(className+FileTypeEnum.MAPPERXML.getFiewNameSuffix());
        }

    }


    /**
     * contoller service 目录分层
     * @param fileInfo
     * @return
     */
    protected String getExclusiveDir(FileInfo fileInfo) {
        String state = fileInfo.getState();
        if(state==null){state="";}
        String exclusiveDir="";
        if(state.equals(FileTypeEnum.CONTOLLER.getKey())){
            exclusiveDir=fileInfo.getControllerFilePath();
        }else if(state.equals(FileTypeEnum.SERVICEIMP.getKey())){
            exclusiveDir=fileInfo.getServiceImplFilePath();
        }else if(state.equals(FileTypeEnum.SERVICE.getKey())){
            exclusiveDir=fileInfo.getServiceFilePath();
        }else if(state.equals(FileTypeEnum.ENTITY.getKey())){
            exclusiveDir=fileInfo.getEntityFilePath();
        }else if(state.equals(FileTypeEnum.MAPPER.getKey())){
            exclusiveDir=fileInfo.getDaoFilePath();
        }else if(state.equals(FileTypeEnum.MAPPERXML.getKey())){
            exclusiveDir=fileInfo.getMapperFilePath();
        }
        return exclusiveDir;
    }

    protected List<TemplateData> getTemplateData() {
        List<TableData> metaData = MetaDataBuilder.getInstance().getMetaData();
        //数据标识
        String dbState = metaData.get(0).getDbState();
        List<TemplateData> templateDataList= new LinkedList<TemplateData>();
        for (TableData metaDatum : metaData) {
            TemplateData templateData = new TemplateData();
            setRowInfo(metaDatum,templateData);
            List<TableDetailInfo> tableDetailInfos = metaDatum.getTableDetailInfo();
            LinkedList<TemplateData.DetailInfo> templateTableDetailInfos = new LinkedList<TemplateData.DetailInfo>();
            for (TableDetailInfo tableDetailInfo : tableDetailInfos) {
                setColumnInfo(templateData,templateTableDetailInfos,tableDetailInfo,dbState);
            }
            templateData.setTableDetailInfos(templateTableDetailInfos);
            templateDataList.add(templateData);
        }
        return templateDataList;
    }

    protected void setColumnInfo(TemplateData templateData,LinkedList<TemplateData.DetailInfo> templateTableDetailInfos, TableDetailInfo tableDetailInfo,String dbState) {
        String columnName = tableDetailInfo.COLUMN_NAME;
        TemplateData.DetailInfo detailInfo = templateData.new DetailInfo();
        detailInfo.setColumnType(tableDetailInfo.getCOLUMN_TYPE());
        detailInfo.setColumnName(columnName);
        detailInfo.setColumnRemark(tableDetailInfo.getColumnRemark());
        String fieldName = lineToHump(columnName);
        detailInfo.setFieldName(fieldName);
        String firstToUpperCase = firstToUpperCase(fieldName);
        detailInfo.setFirstUpperCaseFieldName(firstToUpperCase);
        detailInfo.setGetMethodName("get"+firstToUpperCase);
        detailInfo.setSetMethodName("set"+firstToUpperCase);
        //java 类型 设置
        setJavaType(detailInfo,dbState);
        templateTableDetailInfos.add(detailInfo);
    }

    protected void setRowInfo(TableData metaDatum, TemplateData templateData) {
        String tableName = metaDatum.getTableName();
        //设置文件包的信息
        setPackageInfo(templateData);
        String className=namePrefixSuffixHandle(tableName);
        //下劃線轉駝峰
        className=  lineToHump(className);
        templateData.setFirstLowerCaseClassName(className);
        //别名
        setAliasName(templateData);
        templateData.setPathName(templateData.getFirstLowerCaseClassName());
        //首字母大写
        className = firstToUpperCase(className);
        templateData.setFirstUpperCaseClassName(className);
        templateData.setTableName(tableName);
        templateData.setClassName(className);
        //类名
        setPackageClassName(templateData);
        templateData.setTableRemark(metaDatum.getTableRemark());
    }

    protected void setAliasName(TemplateData templateData) {
        templateData.setClassAlias(templateData.getFirstLowerCaseClassName());
        templateData.setControllerAliasName(templateData.getFirstLowerCaseClassName()+FileTypeEnum.CONTOLLER.getFiewNameSuffix());
        templateData.setServiceAliasName(templateData.getFirstLowerCaseClassName()+FileTypeEnum.SERVICE.getFiewNameSuffix());
        templateData.setServiceImplAliasName(templateData.getFirstLowerCaseClassName()+FileTypeEnum.SERVICEIMP.getFiewNameSuffix());
        templateData.setEntityAliasName(templateData.getFirstLowerCaseClassName()+FileTypeEnum.ENTITY.getFiewNameSuffix());
        templateData.setDaoAliasName(templateData.getFirstLowerCaseClassName()+FileTypeEnum.MAPPER.getFiewNameSuffix());
    }

    private String namePrefixSuffixHandle(String tableName) {
        Properties properites = GenerateProperties.getProperites();
        String changeTableName="";
        //是否移除前文件名的前后缀
        String removePrefix=null;
        String removeSuffix=null;
        if(properites.get("removePrefix")!=null){
            removePrefix=(String)properites.get("removePrefix");
        }
        if(properites.get("removeSuffix")!=null){
            removeSuffix=(String)properites.get("removeSuffix");
        }
        if(removePrefix!=null){
            if(tableName.startsWith(removePrefix)){
                int index = tableName.indexOf(removePrefix);
                if(index!=-1){
                    changeTableName=tableName.substring(index+removePrefix.length(),tableName.length());
                }
            }
        }else{
            changeTableName=tableName;
        }
        if(removeSuffix!=null){
            if(changeTableName.endsWith(removeSuffix)){
                int index = changeTableName.lastIndexOf(removeSuffix);
                if(index!=-1){
                    changeTableName=changeTableName.substring(0,index-removeSuffix.length());
                }
            }
        }else{
            changeTableName=tableName;
        }

        return changeTableName;

    }

    protected void setPackageInfo(TemplateData templateData) {
        GenerateProperties generateProperties = new GenerateProperties();
        Properties properites = GenerateProperties.getProperites();
        String controllerPackage = properites.getProperty("controllerPackage");
        String servicePackage = properites.getProperty("servicePackage");
        String serviceImplPackage = properites.getProperty("serviceImplPackage");
        String entityPackage = properites.getProperty("entityPackage");
        String daoPackage = properites.getProperty("daoPackage");
        templateData.setControllerPackage(controllerPackage);
        templateData.setServicePackage(servicePackage);
        templateData.setServiceImplPackage(serviceImplPackage);
        templateData.setEntityPackage(entityPackage);
        templateData.setDaoPackage(daoPackage);

    }

    protected void generateInfoCheckAndInit(Properties properites) {
        String controllerPackage = properites.getProperty("controllerPackage");
        String servicePackage = properites.getProperty("servicePackage");
        String serviceImplPackage = properites.getProperty("serviceImplPackage");
        String entityPackage = properites.getProperty("entityPackage");
        String daoPackage = properites.getProperty("daoPackage");
        String mapperFilePath = properites.getProperty("mapperFilePath");
        String generateFileDir = properites.getProperty("generateFileDir");
        if(controllerPackage==null || "".equals(controllerPackage)){
            properites.setProperty("controllerPackage","com.framework.controller");
        }
        if(servicePackage==null || "".equals(servicePackage)){
            properites.setProperty("servicePackage","com.framework.service");
        }
        if(serviceImplPackage==null || "".equals(serviceImplPackage)){
            properites.setProperty("serviceImplPackage","com.framework.service.impl");
        }
        if(entityPackage==null || "".equals(entityPackage)){
            properites.setProperty("entityPackage","com.framework.entity");
        }
        if(daoPackage==null || "".equals(daoPackage)){
            properites.setProperty("daoPackage","com.framework.dao");
        }
        if(mapperFilePath==null || "".equals(mapperFilePath)){
            properites.setProperty("mapperFilePath","/mapper");
        }
        if(generateFileDir==null || "".equals(generateFileDir)){
            properites.setProperty("generateFileDir", PathUtils.getWorkDir());
        }

        GenerateProperties.setProperties(properites);
    }

    protected void setJavaType(TemplateData.DetailInfo detailInfo, String dbState) {
        String columnType = detailInfo.getColumnType();
        boolean isExistType=false;
        if(DbStateEnum.MYSQL.getState().equals(dbState)){
            for (MysqlFieldTypeEnum value : MysqlFieldTypeEnum.values()) {
                if(value.getDataType().equals(columnType)){
                    detailInfo.setFieldSimpleType((value.getSimpleType()));
                    detailInfo.setFieldType(value.getJavaType());
                    isExistType=true;
                    return;
                }
            }
        }else if(DbStateEnum.ORACLE.getState().equals(dbState)){

        }else {

        }
        if(!isExistType){
            throw new NullPointerException(detailInfo.getColumnName()+"的数据类型"+detailInfo.getColumnType()+"没有匹配到对应的java类型，请到对应的枚举类设置");
        }
    }

    protected String firstToUpperCase(String str){
        char[] chars = str.toCharArray();
        if(chars.length>0){
            chars[0]=Character.toUpperCase(chars[0]);
        }
        return String.valueOf(chars);
    }

    /** 下划线转驼峰 */
    protected  String lineToHump(String str) {
        Pattern linePattern = Pattern.compile("_(\\w)");
        String lowerCase = str.toLowerCase();
        Matcher matcher = linePattern.matcher(lowerCase);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        StringBuffer stringBuffer = matcher.appendTail(sb);
        if("".equals(stringBuffer.toString())){
            return lowerCase;
        }
        return sb.toString();
    }

}

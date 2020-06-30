package com.template;

import com.data.*;
import com.data.strategy.ExclusiveDirStrategy;
import com.data.strategy.FieldTypeStrategy;
import com.data.strategy.GenerateInfoStrategy;
import com.data.strategy.entity.FieldType;
import com.data.enums.FileTypeEnum;
import com.data.enums.JavaKeyWordEnum;
import com.data.properties.GenerateProperties;
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
 * @Date 2020-05-17 14:49
 */
public abstract class AbstractTempalteBuilder {

    public AbstractTempalteBuilder(){
        init();
    }

    private  void init(){
        Properties properites = GenerateProperties.getProperites();
        generateInfoCheckAndInit(properites);
    }



    public  abstract void  generateTemplate()throws IOException, TemplateException ;




    protected void setPackageClassName(TemplateData templateData) {
        templateData.setControllerClassName(templateData.getClassName()+ FileTypeEnum.CONTOLLER.getFileNameSuffix());
        templateData.setServiceClassName(templateData.getClassName()+FileTypeEnum.SERVICE.getFileNameSuffix());
        templateData.setServiceImplClassName(templateData.getClassName()+FileTypeEnum.SERVICEIMP.getFileNameSuffix());
        templateData.setEntityClassName(templateData.getClassName()+FileTypeEnum.ENTITY.getFileNameSuffix());
        templateData.setDaoClassName(templateData.getClassName()+FileTypeEnum.MAPPER.getFileNameSuffix());
        templateData.setDaoImplClassName(templateData.getClassName()+FileTypeEnum.MAPPERIMPL.getFileNameSuffix());
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
        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
            if(fileTypeEnum.getKey().equals(fileInfo.getState())){
                fileInfo.setFileNewName(className+fileTypeEnum.getFileNameSuffix());
                break;
            }
        }
    }


    /**
     * contoller service 目录分层
     * @param fileInfo
     * @return
     */
    protected String getExclusiveDir(FileInfo fileInfo) {
        String state = fileInfo.getState();
        if(state==null || "".equals(state)){
            return "";
        }
        return ExclusiveDirStrategy.getInstance().getResult(state, fileInfo);
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
            List<TemplateData.DetailInfo> templateTableDetailInfos = new LinkedList<TemplateData.DetailInfo>();
            for (TableDetailInfo tableDetailInfo : tableDetailInfos) {
                setColumnInfo(templateData,templateTableDetailInfos,tableDetailInfo,dbState);
            }

            templateData.setTableDetailInfos(templateTableDetailInfos);

            //设置实体类额外需要导入的包;
            setEntityImportPackage(templateData,dbState);

            templateDataList.add(templateData);
        }
        return templateDataList;
    }

    protected void setColumnInfo(TemplateData templateData,List<TemplateData.DetailInfo> templateTableDetailInfos, TableDetailInfo tableDetailInfo,String dbState) {
        String columnName = tableDetailInfo.COLUMN_NAME;
        TemplateData.DetailInfo detailInfo = templateData.new DetailInfo();
        detailInfo.setColumnType(tableDetailInfo.getCOLUMN_TYPE());
        detailInfo.setColumnName(columnName);
        detailInfo.setColumnRemark(tableDetailInfo.getColumnRemark());
        String fieldName = lineToHump(columnName);
        //如果字段名与java关键字相等则进行转换
        for (JavaKeyWordEnum JavaKeyWordEnum : JavaKeyWordEnum.values()) {
            if(JavaKeyWordEnum.getKeyWord().equalsIgnoreCase(fieldName)){
                fieldName=JavaKeyWordEnum.getChangeName();
                break;
            }
        }
        detailInfo.setFieldName(fieldName);
        String firstToUpperCase = firstToUpperCase(fieldName);
        detailInfo.setFirstUpperCaseFieldName(firstToUpperCase);
        detailInfo.setGetMethodName("get"+firstToUpperCase);
        detailInfo.setSetMethodName("set"+firstToUpperCase);
        //java 类型 设置
        setJavaType(detailInfo,dbState);
        templateTableDetailInfos.add(detailInfo);
    }

    /**
     * 设置实体类额外需要导入的包;
     * @param templateData
     * @Param 对应数据的表示
     */
    private void setEntityImportPackage(TemplateData templateData, String dbState) {
        Set<String> entityImportPackageSet = new LinkedHashSet<>();
        List<FieldType> fieldTypes = FieldTypeStrategy.getInstance().getFieldTypeList(dbState);
        for (TemplateData.DetailInfo detailInfo : templateData.getTableDetailInfos()) {
            //基本数据类型的包装类 与 string 不用导包, 标记为 isPermistImport为 1
            String fieldType = detailInfo.getFieldType();
            for (FieldType type : fieldTypes) {
                if(type.getJavaType().equals(fieldType) && type.getIsPermitImport()==1){
                    entityImportPackageSet.add(fieldType);
                }
            }

        }
        templateData.setEntityImportPackages(entityImportPackageSet);
    }

    protected void setRowInfo(TableData metaDatum, TemplateData templateData) {
        String tableName = metaDatum.getTableName();
        for (JavaKeyWordEnum JavaKeyWordEnum : JavaKeyWordEnum.values()) {
            if(JavaKeyWordEnum.getKeyWord().equalsIgnoreCase(tableName)){
                throw new ClassCastException("表名不能与java关键字相同");
            }
        }
        //设置文件包的信息
        setPackageInfo(templateData);
        String className=nameProfixSuffixHandle(tableName);
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
        templateData.setControllerAliasName(templateData.getFirstLowerCaseClassName()+FileTypeEnum.CONTOLLER.getFileNameSuffix());
        templateData.setServiceAliasName(templateData.getFirstLowerCaseClassName()+FileTypeEnum.SERVICE.getFileNameSuffix());
        templateData.setServiceImplAliasName(templateData.getFirstLowerCaseClassName()+FileTypeEnum.SERVICEIMP.getFileNameSuffix());
        templateData.setEntityAliasName(templateData.getFirstLowerCaseClassName()+FileTypeEnum.ENTITY.getFileNameSuffix());
        templateData.setDaoAliasName(templateData.getFirstLowerCaseClassName()+FileTypeEnum.MAPPER.getFileNameSuffix());
        templateData.setDaoImplAliasName(templateData.getFirstLowerCaseClassName()+FileTypeEnum.MAPPERIMPL.getFileNameSuffix());
    }

    private String nameProfixSuffixHandle(String tableName) {
        Properties properites = GenerateProperties.getProperites();
        String changeTableName="";
        //是否移除前文件名的前后缀
        String removePrefix=null;
        String removeSuffix=null;
        if(properites.get("removePrefix")!=null && !"".equals(properites.get("removePrefix"))){
            removePrefix=(String)properites.get("removePrefix");
        }
        if(properites.get("removeSuffix")!=null && !"".equals(properites.get("removeSuffix"))){
            removeSuffix=(String)properites.get("removeSuffix");
        }
        if(removePrefix!=null
                && tableName.startsWith(removePrefix) && tableName.indexOf(removePrefix)!=-1){
            changeTableName = tableName.substring(tableName.indexOf(removePrefix) + removePrefix.length(), tableName.length());
        }else{
            changeTableName=tableName;
        }
        if(removeSuffix!=null &&changeTableName.endsWith(removeSuffix) && changeTableName.lastIndexOf(removeSuffix)!=-1 ){
            changeTableName=changeTableName.substring(0,changeTableName.lastIndexOf(removeSuffix)-removeSuffix.length());
        }else{
            changeTableName=changeTableName;
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
        String daoImplPackage = properites.getProperty("daoImplPackage");
        templateData.setControllerPackage(controllerPackage);
        templateData.setServicePackage(servicePackage);
        templateData.setServiceImplPackage(serviceImplPackage);
        templateData.setEntityPackage(entityPackage);
        templateData.setDaoPackage(daoPackage);
        templateData.setDaoImplPackage(daoImplPackage);

    }

    protected void generateInfoCheckAndInit(Properties properites) {
        Enumeration<String> enumeration = (Enumeration<String>) properites.propertyNames();
        while (enumeration.hasMoreElements()){
            String key = enumeration.nextElement();
            String value = properites.getProperty(key);
            if(value==null || "".equals(value)){
                properites.setProperty(key, GenerateInfoStrategy.getValue(key));
            }
        }
        //更新
        GenerateProperties.setProperties();
    }

    protected void setJavaType(TemplateData.DetailInfo detailInfo, String dbState) {
        String columnType = detailInfo.getColumnType();
        boolean isExistType=false;
        List<FieldType> fieldTypeList = FieldTypeStrategy.getInstance().getFieldTypeList(dbState);
        for (FieldType fieldType : fieldTypeList) {
            if(fieldType.getDataType().equalsIgnoreCase(columnType)){
                detailInfo.setFieldSimpleType((fieldType.getSimpleType()));
                detailInfo.setFieldType(fieldType.getJavaType());
                isExistType=true;
                return;
            }
        }
        if(!isExistType){
            throw new NullPointerException(dbState+"数据库的"+detailInfo.getColumnName()+"的数据类型"+detailInfo.getColumnType()+"没有匹配到对应的java类型，请到对应的枚举类设置");
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

package com.template.file.jpa;

import com.data.PathUtils;
import com.data.enums.jpa.JpaFileTypeEnum;
import com.data.properties.JpaGenerateProperties;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 9:37
 */
public class JpaTemplateFile {


    public static void main(String[] args) {
        JpaTemplateFile jpaTemplateFile = new JpaTemplateFile();
        List<JpaFileInfo> appointDirFileName = jpaTemplateFile.getAppointDirFileName();
        for (JpaFileInfo fileInfo : appointDirFileName) {
            System.err.println(fileInfo);
        }

    }

    private String dir;

    public static String MYBATIS_DEFAULT_DIR="template/jpa";

    public  List<JpaFileInfo> getAppointDirFileName(){
        String systemRootPath = PathUtils.getSystemRootPath();
        if(dir==null){
            dir=MYBATIS_DEFAULT_DIR;
        }
        File file = new File(systemRootPath + dir);
        List<JpaFileInfo> fileInfos = new LinkedList<JpaFileInfo>();
        try {
            getFileInfos(fileInfos, file);
        }catch ( IOException e){
            e.printStackTrace();
        }
        return fileInfos;
    }

    private void getFileInfos(List<JpaFileInfo> fileInfos,File file) throws IOException {
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File sigletonFile : files) {
                if(sigletonFile.isFile()){
                    JpaFileInfo fileInfo = getFileInfo(sigletonFile);
                    fileInfos.add(fileInfo);
                }else if(file.isDirectory()){
                    getFileInfos(fileInfos,sigletonFile);
                }
            }
        }else if(file.isFile()){
            JpaFileInfo fileInfo = getFileInfo(file);
            fileInfos.add(fileInfo);
        }
    }

    private JpaFileInfo getFileInfo(File file){
        JpaFileInfo fileInfo = new JpaFileInfo();
        String allName = file.getName();
        int index = allName.lastIndexOf(".");
        String name=allName.substring(0,index);
        int index_ = name.indexOf("_");
        String generateFileType = name.substring(index_+1 , name.length());
        generateFileType = generateFileType.toLowerCase();
        fileInfo.setGenerateFileType(generateFileType);
        fileInfo.setFileNameIncludeSuffix(allName);
        fileInfo.setFileName(name);
        fileInfo.setDir(file.getParent());
        //设置 生成文件的路径
        setGenerateFilePath(fileInfo);

        return fileInfo;
    }

    private void setGenerateFilePath(JpaFileInfo fileInfo) {
        //带controller  带 service serviceimp dao entity mapper 而且 包含 _java
        String generateFileType = fileInfo.getGenerateFileType();
        Properties properites = JpaGenerateProperties.getProperites();
        String generateFileDir = properites.getProperty("generateFileDir");
        fileInfo.setGenerateFileDir(generateFileDir);
        String viewFilePath = properites.getProperty("viewFilePath");
        if(viewFilePath==null|| "".equals(viewFilePath)){
            viewFilePath="/";
        }
        if(!viewFilePath.startsWith("/")){
            viewFilePath="/"+viewFilePath;
        }
        if(viewFilePath.endsWith("/")){
            viewFilePath=viewFilePath.substring(0,viewFilePath.length()-1);
        }
        if(generateFileType!=null && generateFileType.contains("java")){
            if(fileInfo.getFileName().toLowerCase().contains(JpaFileTypeEnum.CONTOLLER.getKey())){
                String controllerPackage = properites.getProperty("controllerPackage");
                fileInfo.setExclusiveDir(changePath(controllerPackage));
                fileInfo.setState(JpaFileTypeEnum.CONTOLLER.getKey());
            }else if(fileInfo.getFileName().toLowerCase().contains(JpaFileTypeEnum.SERVICEIMP.getKey())){
                String serviceImplPackage = properites.getProperty("serviceImplPackage");
                fileInfo.setExclusiveDir(changePath(serviceImplPackage));
                fileInfo.setState(JpaFileTypeEnum.SERVICEIMP.getKey());
            }else if(fileInfo.getFileName().toLowerCase().contains(JpaFileTypeEnum.SERVICE.getKey())){
                String servicePackage = properites.getProperty("servicePackage");
                fileInfo.setExclusiveDir(changePath(servicePackage));
                fileInfo.setState(JpaFileTypeEnum.SERVICE.getKey());
            }else if(fileInfo.getFileName().toLowerCase().contains(JpaFileTypeEnum.ENTITY.getKey())){
                String entityPackage = properites.getProperty("entityPackage");
                fileInfo.setExclusiveDir(changePath(entityPackage));
                fileInfo.setState(JpaFileTypeEnum.ENTITY.getKey());
            }else if(fileInfo.getFileName().toLowerCase().contains(JpaFileTypeEnum.MODEL.getKey())){
                String modelPackage = properites.getProperty("modelPackage");
                fileInfo.setExclusiveDir(changePath(modelPackage));
                fileInfo.setState(JpaFileTypeEnum.MODEL.getKey());
            }else if(fileInfo.getFileName().toLowerCase().contains(JpaFileTypeEnum.MAPPER.getKey())){
                String mapperPackage = properites.getProperty("mapperPackage");
                fileInfo.setExclusiveDir(changePath(mapperPackage));
                fileInfo.setState(JpaFileTypeEnum.MAPPER.getKey());
            }else if(fileInfo.getFileName().toLowerCase().contains(JpaFileTypeEnum.REPOSITORY.getKey())){
                String repositoryPackage = properites.getProperty("repositoryPackage");
                fileInfo.setExclusiveDir(changePath(repositoryPackage));
                fileInfo.setState(JpaFileTypeEnum.REPOSITORY.getKey());
            }

        }else if(fileInfo.getDir().contains("components")) {
            String path =viewFilePath+"/"+"components";
            if(fileInfo.getDir()!=null && fileInfo.getDir().endsWith("editer")) {
                path =path+"/"+"list";
                fileInfo.setExclusiveDir(path);
                fileInfo.setState(JpaFileTypeEnum.VIEW_EDITER.getKey());
            }
        }else if(fileInfo.getDir().contains("model")) {
            String path =viewFilePath+"/"+"model";
            fileInfo.setExclusiveDir(path);
            fileInfo.setState(JpaFileTypeEnum.VIEW_MODEL.getKey());
        }else if(fileInfo.getDir().contains("service")) {
            String path =viewFilePath+"/"+"service";
            fileInfo.setExclusiveDir(path);
            fileInfo.setState(JpaFileTypeEnum.VIEW_SERVER.getKey());
        }else if(fileInfo.getDir().contains("views")) {
            String path =viewFilePath+"/"+"views";
            if(fileInfo.getDir()!=null && fileInfo.getDir().endsWith("list")) {
                path =path+"/"+"list";
                fileInfo.setExclusiveDir(path);
                fileInfo.setState(JpaFileTypeEnum.VIEWS_LIST.getKey());
            }else if(fileInfo.getDir()!=null && fileInfo.getDir().endsWith("tree")) {
                path =path+"/"+"tree";
                fileInfo.setExclusiveDir(path);
                fileInfo.setState(JpaFileTypeEnum.VIEWS_TREE.getKey());
            }
        }

    }

    public String changePath(String str){
         String s = str.replaceAll("\\.", "/");
        if(s.endsWith(File.separator)){
            return s.substring(0,s.length()-1);
        }
        return s;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}

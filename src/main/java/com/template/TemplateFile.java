package com.template;

import com.data.PathUtils;
import com.data.enums.FileTypeEnum;
import com.data.properties.GenerateProperties;

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
public class TemplateFile {

    private String dir;

    public static final String DEFAULT_DIR="template";

    public  List<FileInfo> getAppointDirFileName(){
        String systemRootPath = PathUtils.getSystemRootPath();
        if(dir==null){
            dir=DEFAULT_DIR;
        }
        File file = new File(systemRootPath + dir);
        List<FileInfo> fileNames = new LinkedList<FileInfo>();
        try {
            getFileNames(fileNames, file);
        }catch ( IOException e){
            e.printStackTrace();
        }
        return fileNames;
    }

    private void getFileNames(List<FileInfo> fileNames,File file) throws IOException {
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File sigletonFile : files) {
                if(sigletonFile.isFile()){
                    FileInfo fileInfo = getFileInfo(sigletonFile);
                    fileNames.add(fileInfo);
                }else if(file.isDirectory()){
                    continue;
                   // throw new IOException("暂时不支持多级目录，请将模板文件放到resource/template下面");
                }
            }
        }else if(file.isFile()){
            FileInfo fileInfo = getFileInfo(file);
            fileNames.add(fileInfo);
        }
    }

    private FileInfo getFileInfo(File file){
        FileInfo fileInfo = new FileInfo();
        String allName = file.getName();
        int index = allName.lastIndexOf(".");
        String name=allName.substring(0,index);
        String absolutePath = file.getAbsolutePath();
        int index_ = name.indexOf("_");
        String generateFileType = name.substring(index_+1 , name.length());
        generateFileType = generateFileType.toLowerCase();
        fileInfo.setGenerateFileType(generateFileType);
        fileInfo.setFileNameIncludeSuffix(allName);
        fileInfo.setFileName(name);
        fileInfo.setDir(absolutePath);
        //设置 生成文件的路径
        setGenerateFilePath(fileInfo);

        return fileInfo;
    }

    private void setGenerateFilePath(FileInfo fileInfo) {
        //带controller  带 service serviceimp dao entity mapper 而且 包含 _java
        String generateFileType = fileInfo.getGenerateFileType();
        Properties properites = GenerateProperties.getProperites();
        String generateFileDir = properites.getProperty("generateFileDir");
        fileInfo.setGenerateFileDir(generateFileDir);
        if(generateFileType!=null && generateFileType.contains("java")){
            if(fileInfo.getFileName().toLowerCase().contains(FileTypeEnum.CONTOLLER.getKey())){
                String controllerPackage = properites.getProperty("controllerPackage");
                fileInfo.setControllerFilePath(changePath(controllerPackage));
                fileInfo.setState(FileTypeEnum.CONTOLLER.getKey());
            }else if(fileInfo.getFileName().toLowerCase().contains(FileTypeEnum.SERVICEIMP.getKey())){
                String serviceImplPackage = properites.getProperty("serviceImplPackage");
                fileInfo.setServiceImplFilePath(changePath(serviceImplPackage));
                fileInfo.setState(FileTypeEnum.SERVICEIMP.getKey());
            }else if(fileInfo.getFileName().toLowerCase().contains(FileTypeEnum.SERVICE.getKey())){
                String servicePackage = properites.getProperty("servicePackage");
                fileInfo.setServiceFilePath(changePath(servicePackage));
                fileInfo.setState(FileTypeEnum.SERVICE.getKey());
            }else if(fileInfo.getFileName().toLowerCase().contains(FileTypeEnum.ENTITY.getKey())){
                String entityPackage = properites.getProperty("entityPackage");
                fileInfo.setEntityFilePath(changePath(entityPackage));
                fileInfo.setState(FileTypeEnum.ENTITY.getKey());
            }else if(fileInfo.getFileName().toLowerCase().contains(FileTypeEnum.MAPPERIMPL.getKey())){
                String daoImplPackage = properites.getProperty("daoImplPackage");
                fileInfo.setDaoImpleFilePath(changePath(daoImplPackage));
                fileInfo.setState(FileTypeEnum.MAPPERIMPL.getKey());
            }else if(fileInfo.getFileName().toLowerCase().contains(FileTypeEnum.MAPPER.getKey())){
                String daoPackage = properites.getProperty("daoPackage");
                fileInfo.setDaoFilePath(changePath(daoPackage));
                fileInfo.setState(FileTypeEnum.MAPPER.getKey());
            }

        }else if(generateFileType!=null && generateFileType.contains("xml")){
            String mapperFilePath = properites.getProperty("mapperFilePath");
            if(mapperFilePath.startsWith(File.separator)){
                mapperFilePath="/"+mapperFilePath;
            }
            if(mapperFilePath.endsWith(File.separator)){
                mapperFilePath=mapperFilePath.substring(0,mapperFilePath.length()-1);
            }
            fileInfo.setDaoFilePath(mapperFilePath);
            fileInfo.setState(FileTypeEnum.MAPPERXML.getKey());
        }
    }

    public String changePath(String str){
        String s = str.replaceAll("\\.", "/");
        if(s.endsWith(File.separator)){
            return s.substring(0,s.length()-1);
        }
        return s;
    }

}

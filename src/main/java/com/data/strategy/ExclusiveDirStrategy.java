package com.data.strategy;

import com.data.enums.FileTypeEnum;
import com.template.file.mybatis.FileInfo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-06-24 11:01
 */
public class ExclusiveDirStrategy {
    /**
     * key  是  fileTypeEnum 的 key  value 是  fileinfo的方法
     */
    private static Map<String,Method> container=new HashMap<String,Method>();

    static {
        Class<FileInfo> fileInfoClass = FileInfo.class;
        try {
            Method controllerFilePath = fileInfoClass.getDeclaredMethod("getControllerFilePath", new Class[]{});
            Method serviceImplFilePath = fileInfoClass.getDeclaredMethod("getServiceImplFilePath", new Class[]{});
            Method serviceFilePath = fileInfoClass.getDeclaredMethod("getServiceFilePath", new Class[]{});
            Method entityFilePath = fileInfoClass.getDeclaredMethod("getEntityFilePath", new Class[]{});
            Method daoFilePath = fileInfoClass.getDeclaredMethod("getDaoFilePath", new Class[]{});
            Method daoImpleFilePath = fileInfoClass.getDeclaredMethod("getDaoImpleFilePath", new Class[]{});
            Method mapperFilePath = fileInfoClass.getDeclaredMethod("getMapperFilePath", new Class[]{});
            container.put(FileTypeEnum.CONTOLLER.getKey(),controllerFilePath);
            container.put(FileTypeEnum.SERVICEIMP.getKey(),serviceImplFilePath);
            container.put(FileTypeEnum.SERVICE.getKey(),serviceFilePath);
            container.put(FileTypeEnum.ENTITY.getKey(),entityFilePath);
            container.put(FileTypeEnum.MAPPER.getKey(),daoFilePath);
            container.put(FileTypeEnum.MAPPERIMPL.getKey(),daoImpleFilePath);
            container.put(FileTypeEnum.MAPPERXML.getKey(),mapperFilePath);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    private ExclusiveDirStrategy(){
        if(InnerClass.exclusiveDirStrategy!=null){
            throw new NullPointerException("不能通过反射创建该类");
        }
    }

    /**
     * 静态内部类初始化
     * @return
     */
    public static ExclusiveDirStrategy getInstance(){
        return InnerClass.exclusiveDirStrategy;
    }
    /**
     *
     * @param key    是  fileTypeEnum 的 key
     * @param fileInfo
     * @return
     */
    public  String getResult(String key,FileInfo fileInfo){
        Method method = container.get(key);
        try {
            Object invoke = method.invoke(fileInfo, null);
            return (String) invoke;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static class InnerClass{
        private static ExclusiveDirStrategy exclusiveDirStrategy=new ExclusiveDirStrategy();
    }
}

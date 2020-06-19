package com.data.properties;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 15:09
 */
public class GenerateProperties {

    private static final String DEFAULT_FILE="generateInfo.properties";


    public static Properties getProperites(){
         Properties properties = new Properties();
        try {
            InputStream resourceAsStream = BaseProperties.class.getClassLoader().getResourceAsStream(DEFAULT_FILE);
            properties.load(resourceAsStream);
            resourceAsStream.close();
            return properties;
        }catch (IOException e){
           throw new NullPointerException("generateInfo.properties 文件不存在请在 resource 新增这个文件");
        }
    }

   public static void setProperties(Properties properties){
       try {
           URL resourceUrl = BaseProperties.class.getClassLoader().getResource(DEFAULT_FILE);
           FileOutputStream fileOutputStream = new FileOutputStream(resourceUrl.getFile());
           OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
           properties.store(outputStreamWriter, "Update  key value");
           fileOutputStream.close();
           outputStreamWriter.close();
       } catch (Exception e) {
           e.printStackTrace();
       }

   }
}

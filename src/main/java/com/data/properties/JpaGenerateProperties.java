package com.data.properties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Properties;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 15:09
 */
public class JpaGenerateProperties {

    public static final String DEFAULT_FILE="jpaGenerateInfo.properties";

    private static Properties properties = new Properties();
    public static Properties getProperites(){
        try {
            InputStream resourceAsStream = BaseProperties.class.getClassLoader().getResourceAsStream(DEFAULT_FILE);
            properties.load(resourceAsStream);
            resourceAsStream.close();
            return properties;
        }catch (IOException e){
           throw new NullPointerException("jpaGenerateInfo.properties 文件不存在请在 resource 新增这个文件");
        }
    }

   public static void setProperties(){
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

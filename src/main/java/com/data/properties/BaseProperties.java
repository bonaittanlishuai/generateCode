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
 * @Date 2020-05-09 8:11
 */
public class BaseProperties {

    private static final String DEFAULT_FILE="baseMessage.properties";
    private static Properties properties = new Properties();

    public BaseProperties(){
    }


    public static Properties getProperites(){
          Properties properties = new Properties();
        try {
            InputStream resourceAsStream = BaseProperties.class.getClassLoader().getResourceAsStream(DEFAULT_FILE);
            properties.load(resourceAsStream);
            return properties;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static  Properties generateTemplateType() {
        String generateTemplateType = BaseProperties.getProperites().getProperty("generateTemplateType");
        Properties properites = null;
        if ("jpa".equals(generateTemplateType)) {
            properites = JpaGenerateProperties.getProperites();
        } else {
            properites = GenerateProperties.getProperites();
        }
        return properites;
    }

    public static void setProperties(){
        try {
            Properties properites2 = BaseProperties.generateTemplateType();
            String generateTemplateType = properites2.getProperty("generateTemplateType");
            String url = null;
            if ("jpa".equals(generateTemplateType)) {
                url = JpaGenerateProperties.DEFAULT_FILE;
            } else {
                url = GenerateProperties.DEFAULT_FILE;
            }
            URL resourceUrl = BaseProperties.class.getClassLoader().getResource(url);
            FileOutputStream fileOutputStream = new FileOutputStream(resourceUrl.getFile());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            properites2.store(outputStreamWriter, "Update  key value");
            fileOutputStream.close();
            outputStreamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

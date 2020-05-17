package com.data.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 8:11
 */
public class BaseProperties {

    private String resourceFile;

    private static final String DEFAULT_FILE="baseMessage.properties";

    private String mkdir;

    public BaseProperties(){

    }

    public BaseProperties(String mkdir,String resourceFile){
        this.resourceFile=resourceFile;
        this.mkdir=mkdir;
    }

    public BaseProperties(String resourceFile){
        this.resourceFile=resourceFile;
    }

    public  Properties getProperites(){
          Properties properties = new Properties();

        try {
            if(resourceFile==null)
                resourceFile=DEFAULT_FILE;
            if(mkdir!=null){
                if(!mkdir.endsWith("/")){
                    mkdir=mkdir+"/";
                }
                resourceFile=mkdir+resourceFile;
            }
            InputStream resourceAsStream = BaseProperties.class.getClassLoader().getResourceAsStream(resourceFile);
            properties.load(resourceAsStream);
            return properties;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;

    }

}

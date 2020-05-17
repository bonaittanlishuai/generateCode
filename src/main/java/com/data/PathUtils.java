package com.data;

import java.net.URL;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 9:59
 */
public class PathUtils {
    public static void main(String[] args) {
        System.err.println(getWorkDir());
    }

    public static String getWorkDir(){
        return System.getProperty("user.dir");
    }

    public static String getSystemRootPath(){
        String name=PathUtils.class.getName();
        name=name.replace(".","/")+".class";
        URL resource = PathUtils.class.getClassLoader().getResource(name);
        String path = resource.getPath();
        int index = path.indexOf(name);
        return path.substring(0,index);
    }
}

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
        return PathUtils.class.getClassLoader().getResource("").getPath();
    }
}

package com;

import com.template.FreemarkerBuilder;
import com.template.SpringBeanXmlBuilder;
import freemarker.template.TemplateException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-17 12:41
 */
public class StartMain {
    public static void main(String[] args) throws Exception {
        FreemarkerBuilder freemarkerBuilder = new SpringBeanXmlBuilder();
        freemarkerBuilder.generateTemplate();
    }
}

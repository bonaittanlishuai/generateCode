package com;

import com.template.FreemarkerBuilder;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-17 12:41
 */
public class StartMain {
    public static void main(String[] args) throws IOException, TemplateException {
        FreemarkerBuilder freemarkerBuilder = new FreemarkerBuilder();
        freemarkerBuilder.generateTemplate();
    }
}

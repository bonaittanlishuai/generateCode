package com;

import com.template.jpa.JpaFreemarkerBuilder;
import com.template.mybatis.FreemarkerBuilder;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-17 12:41
 */
public class StartMain {
    public static void main(String[] args) throws Exception {
        JpaFreemarkerBuilder freemarkerBuilder = new JpaFreemarkerBuilder();
        freemarkerBuilder.generateTemplate();
    }
}

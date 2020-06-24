package com.data.delegate;

import com.data.PathUtils;
import com.data.enums.GenerateInfoInitEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-06-24 10:30
 */
public class GenerateInfoDelegate {
    /**
     * key 是  fileTypeEnum 的key
     */
    private static Map<String,String> container=new HashMap<>();

    static{
        for (GenerateInfoInitEnum generateInfoInitEnum : GenerateInfoInitEnum.values()) {
            container.put(generateInfoInitEnum.getKey(),generateInfoInitEnum.getValue());
        }
        container.put("generateFileDir", PathUtils.getWorkDir());
    }

    private GenerateInfoDelegate(){
        if(InnerClass.generateInfoDelegate!=null){
            throw new NullPointerException("不能通过反射创建该类");
        }
    }

    public static GenerateInfoDelegate getInstance(){
        return InnerClass.generateInfoDelegate;
    }

    public static String getValue(String key){
        if(!container.containsKey(key)){
            return "";
        }
        return container.get(key);
    }



    private static class InnerClass{
        private static GenerateInfoDelegate generateInfoDelegate=new GenerateInfoDelegate();
    }

}

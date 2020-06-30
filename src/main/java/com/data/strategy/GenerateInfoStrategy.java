package com.data.strategy;

import com.data.PathUtils;
import com.data.enums.GenerateInfoInitEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-06-24 10:30
 */
public class GenerateInfoStrategy {
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

    private GenerateInfoStrategy(){
        if(InnerClass.generateInfoStrategy!=null){
            throw new NullPointerException("不能通过反射创建该类");
        }
    }

    public static GenerateInfoStrategy getInstance(){
        return InnerClass.generateInfoStrategy;
    }

    public static String getValue(String key){
        if(!container.containsKey(key)){
            return "";
        }
        return container.get(key);
    }



    private static class InnerClass{
        private static GenerateInfoStrategy generateInfoStrategy=new GenerateInfoStrategy();
    }

}

package com.template.file.jpa;

import com.template.file.mybatis.FileInfo;
import lombok.Data;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 10:47
 */
@Data
public class JpaFileInfo extends FileInfo {
    /**
     * 生成文件的专属目录  将旧版的单个一个路径都改成专属文件一个字段
     */
    private String exclusiveDir;

}

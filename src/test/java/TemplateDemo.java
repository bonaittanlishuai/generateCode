import com.template.file.mybatis.FileInfo;
import com.template.file.mybatis.MybatisTemplateFile;

import java.util.List;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 10:12
 */
public class TemplateDemo {
    public static void main(String[] args) {
        MybatisTemplateFile  templateFile = new MybatisTemplateFile();
        List<FileInfo> appointDirFileName = templateFile.getAppointDirFileName();
    }

}

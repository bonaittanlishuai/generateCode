import com.template.FileInfo;
import com.template.TemplateFile;

import java.util.List;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 10:12
 */
public class TemplateDemo {
    public static void main(String[] args) {
        TemplateFile templateFile = new TemplateFile();
        List<FileInfo> appointDirFileName = templateFile.getAppointDirFileName();
    }

}

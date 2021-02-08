import com.template.mybatis.FreemarkerBuilder;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-08 23:52
 */
public class TestDemo {
    public static void main(String[] args) throws SQLException, IOException, TemplateException {
        FreemarkerBuilder freemarkerBuilder = new FreemarkerBuilder();
        freemarkerBuilder.generateTemplate();
    }
}

package ${servicePackage};

import ${entityPackage}.${entityClassName};

import com.mt.newframework.utils.page.PageResult;

import java.util.List;
/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-13 17:27
 */
public interface ${serviceClassName} {

    int insert(${entityClassName} ${entityAliasName});

    int update(${entityClassName} ${entityAliasName});

    int deleted(Object id);

    List<${entityClassName}> selectAll();

    List<${entityClassName}> selectById(Object id);

    PageResult pageSelect(${entityClassName} ${entityAliasName});
}

package ${servicePackage};

import ${entityPackage}.${entityClassName};
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-13 17:27
 */
public interface ${serviceClassName} {

    PageInfo<${entityClassName}> findPage(${entityClassName} ${entityAliasName},Integer page, Integer size);


    PageInfo<${entityClassName}> findPage(Integer page, Integer size);

    List<${entityClassName}> findList(${entityClassName} ${entityAliasName});

    List<${entityClassName}> selectAll();

    void add(${entityClassName} ${entityAliasName});

    void update(${entityClassName} ${entityAliasName});
}

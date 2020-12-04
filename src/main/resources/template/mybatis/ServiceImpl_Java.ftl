package ${serviceImplPackage};

import ${entityPackage}.${entityClassName};
import ${daoPackage}.${daoClassName};
import ${servicePackage}.${serviceClassName};

import com.mt.newframework.framework.spring.ApplicationContextBean;
import com.mt.newframework.utils.page.PageResult;

import java.util.List;


/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-13 17:28
 */
public class ${serviceImplClassName} implements ${serviceClassName} {

    private  ${daoClassName}  ${daoAliasName};

    public ${serviceImplClassName}(${daoClassName}  ${daoAliasName}){

    }

    @Override
    public int insert(${entityClassName} ${entityAliasName}) {
        return ${daoAliasName}.insert(${entityAliasName});
    }

    @Override
    public int update(${entityClassName} ${entityAliasName}) {
        return ${daoAliasName}.update(${entityAliasName});
    }

    @Override
    public int deleted(Object id) {
        return ${daoAliasName}.deleted(id);
    }

    @Override
    public List<${entityClassName}> selectAll() {
         return ${daoAliasName}.selectAll();
    }

    @Override
    public List<${entityClassName}> selectById(Object id) {
        return ${daoAliasName}.selectById(id);
    }

    @Override
    public PageResult pageSelect(${entityClassName} ${entityAliasName}) {
        return ${daoAliasName}.pageSelect(${entityAliasName});
    }

}

package ${serviceImplPackage};

import ${daoPackage}.${daoClassName};
import ${entityPackage}.${entityClassName};
import ${servicePackage}.${serviceClassName};
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-13 17:28
 */
@Service
public class ${serviceImplClassName} implements ${serviceClassName} {


    @Autowired
    private ${daoClassName} ${daoAliasName};


    @Override
    public PageInfo<${entityClassName}> findPage(${entityClassName} ${entityAliasName}, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        Example example = createExample(${entityAliasName});
        List<${entityClassName}> list = findList(${entityAliasName});
        PageInfo<${entityClassName}> brandPageInfo = new PageInfo<>(list);
        return brandPageInfo;
    }

    @Override
    public PageInfo<${entityClassName}> findPage(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<${entityClassName}> brands = ${daoAliasName}.selectAll();
        PageInfo<${entityClassName}> brandPageInfo = new PageInfo<>(brands);
        return brandPageInfo;
    }

    @Override
    public List<${entityClassName}> findList(${entityClassName} ${entityAliasName}){
        Example example = createExample(${entityAliasName});
        return ${daoAliasName}.selectByExample(example);
    }

    @Override
    public List<${entityClassName}> selectAll() {
        return ${daoAliasName}.selectAll();
    }

    @Override
    public void add(${entityClassName} ${entityAliasName}) {
        ${daoAliasName}.insertSelective(${entityAliasName});
    }

    @Override
    public void update(${entityClassName} ${entityAliasName}) {
        ${daoAliasName}.updateByPrimaryKeySelective(${entityAliasName});
    }

    private Example createExample(${entityClassName} ${entityAliasName}){
        Example example = new Example(${entityClassName}.class);
        Example.Criteria criteria = example.createCriteria();
        if(${entityAliasName}!=null){
            <#list tableDetailInfos as tableDetailInfo>
                if(!StringUtils.isEmpty(${entityAliasName}.${tableDetailInfo.getMethodName})){
                    criteria.andLike("${tableDetailInfo.columnName}","%"+${entityAliasName}.${tableDetailInfo.getMethodName}+"%");
                 }
            </#list>
        }
        return example;
    }

}

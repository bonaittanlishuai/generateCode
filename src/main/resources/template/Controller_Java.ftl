package ${controllerPackage};

import ${entityPackage}.${entityClassName};
import ${servicePackage}.${serviceClassName};
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/${firstLowerCaseClassName}")
/**
 * //跨域
 */
@CrossOrigin
/**
 * @Description
 * @author tanlishuai
 * @Date 2020-05-13 17:29
 */
public class ${controllerClassName}{

    @Autowired
    ${serviceClassName} ${serviceAliasName};
    /**
     * ${tableRemark} 分页条件查询
     * @param ${classAlias}
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/findPage/{page}/{size}")
    public Result<PageInfo< ${className}> > findPage(@RequestBody ${className} ${classAlias}, @PathVariable("page") Integer page,@PathVariable("size") Integer size) {
        PageInfo<${className}> pageInfo = ${serviceAliasName}.findPage(${classAlias},page, size);
        return Result.<PageInfo<${className}>>builder().flag(true).code(StatusCode.OK.getCode()).message("查询分页${tableRemark}集合成功").data(pageInfo).build();
    }

    /**
     * ${tableRemark} 分页查询
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/findPage/{page}/{size}")
    public Result<PageInfo<${className}>> findPage(@PathVariable("page") Integer page,@PathVariable("size") Integer size) {
        PageInfo<${className}> pageInfo = ${serviceAliasName}.findPage(page, size);
        return Result.<PageInfo<${className}>>builder().flag(true).code(StatusCode.OK.getCode()).message("查询分页品牌集合成功").data(pageInfo).build();
    }

    /**
     * ${tableRemark} 查询所有
     * @return
     */
    @GetMapping("/selectAll")
    public Result<List<${className}>> selectAll(){
        List<${className}> brands = ${serviceAliasName}.selectAll();
        return  Result.<List<${className}>>builder().flag(true).code(StatusCode.OK.getCode()).message("查询全部品牌集合成功").data(brands).build();
    }

    /**
     * ${tableRemark} 不分页条件查询
     * @param ${classAlias}
     * @return
     */
    @GetMapping("/findList")
    public Result<List<${className}>> findList(@RequestBody ${className} ${classAlias}){
        List<${className}> list = ${serviceAliasName}.findList(${classAlias});
        return Result.<List<${className}>>builder().flag(true).code(StatusCode.OK.getCode()).message("查询条件品牌集合成功").data(list).build();
    }

    /**
     * ${tableRemark}的添加
     * @param ${classAlias}
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody ${className} ${classAlias}) {
        ${serviceAliasName}.add(${classAlias});
        return Result.<PageInfo<${className}>>builder().flag(true).code(StatusCode.OK.getCode()).message("添加品牌集合成功").build();
    }

    /**
     * ${tableRemark}的修改
     * @param ${classAlias}
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody ${className} ${classAlias}) {
        ${serviceAliasName}.update(${classAlias});
        return Result.<PageInfo<${className}>>builder().flag(true).code(StatusCode.OK.getCode()).message("修改品牌集合成功").build();
    }

}

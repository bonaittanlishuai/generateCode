package com.framework.controller;

import com.framework.dao.entity.Brand;
import com.framework.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/brand")
/**
 * //跨域
 */
@CrossOrigin
/**
 * @Description
 * @author tanlishuai
 * @Date 2020-05-13 17:29
 */
public class BrandController{

    @Autowired
    BrandService brandService;
    /**
     *  分页条件查询
     * @param brand
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/findPage/{page}/{size}")
    public Result<PageInfo< Brand> > findPage(@RequestBody Brand brand, @PathVariable("page") Integer page,@PathVariable("size") Integer size) {
        PageInfo<Brand> pageInfo = brandService.findPage(brand,page, size);
        return Result.<PageInfo<Brand>>builder().flag(true).code(StatusCode.OK.getCode()).message("查询分页集合成功").data(pageInfo).build();
    }

    /**
     *  分页查询
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/findPage/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@PathVariable("page") Integer page,@PathVariable("size") Integer size) {
        PageInfo<Brand> pageInfo = brandService.findPage(page, size);
        return Result.<PageInfo<Brand>>builder().flag(true).code(StatusCode.OK.getCode()).message("查询分页品牌集合成功").data(pageInfo).build();
    }

    /**
     *  查询所有
     * @return
     */
    @GetMapping("/selectAll")
    public Result<List<Brand>> selectAll(){
        List<Brand> brands = brandService.selectAll();
        return  Result.<List<Brand>>builder().flag(true).code(StatusCode.OK.getCode()).message("查询全部品牌集合成功").data(brands).build();
    }

    /**
     *  不分页条件查询
     * @param brand
     * @return
     */
    @GetMapping("/findList")
    public Result<List<Brand>> findList(@RequestBody Brand brand){
        List<Brand> list = brandService.findList(brand);
        return Result.<List<Brand>>builder().flag(true).code(StatusCode.OK.getCode()).message("查询条件品牌集合成功").data(list).build();
    }

    /**
     * 的添加
     * @param brand
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Brand brand) {
        brandService.add(brand);
        return Result.<PageInfo<Brand>>builder().flag(true).code(StatusCode.OK.getCode()).message("添加品牌集合成功").build();
    }

    /**
     * 的修改
     * @param brand
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Brand brand) {
        brandService.update(brand);
        return Result.<PageInfo<Brand>>builder().flag(true).code(StatusCode.OK.getCode()).message("修改品牌集合成功").build();
    }

}

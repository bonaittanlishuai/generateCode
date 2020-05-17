package com.framework.service.impl;

import com.framework.dao.BrandMapper;
import com.framework.dao.entity.Brand;
import com.framework.service.BrandService;
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
public class BrandServiceImp implements BrandService {


    @Autowired
    private BrandMapper brandMapper;


    @Override
    public PageInfo<Brand> findPage(Brand brand, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        Example example = createExample(brand);
        List<Brand> list = findList(brand);
        PageInfo<Brand> brandPageInfo = new PageInfo<>(list);
        return brandPageInfo;
    }

    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Brand> brands = brandMapper.selectAll();
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
        return brandPageInfo;
    }

    @Override
    public List<Brand> findList(Brand brand){
        Example example = createExample(brand);
        return brandMapper.selectByExample(example);
    }

    @Override
    public List<Brand> selectAll() {
        return brandMapper.selectAll();
    }

    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    private Example createExample(Brand brand){
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if(brand!=null){
                if(!StringUtils.isEmpty(brand.getId)){
                    criteria.andLike("id","%"+brand.getId+"%");
                 }
                if(!StringUtils.isEmpty(brand.getName)){
                    criteria.andLike("name","%"+brand.getName+"%");
                 }
                if(!StringUtils.isEmpty(brand.getImage)){
                    criteria.andLike("image","%"+brand.getImage+"%");
                 }
                if(!StringUtils.isEmpty(brand.getLetter)){
                    criteria.andLike("letter","%"+brand.getLetter+"%");
                 }
                if(!StringUtils.isEmpty(brand.getSeq)){
                    criteria.andLike("seq","%"+brand.getSeq+"%");
                 }
        }
        return example;
    }

}

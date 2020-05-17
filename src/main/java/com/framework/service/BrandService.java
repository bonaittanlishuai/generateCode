package com.framework.service;

import com.framework.dao.entity.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-13 17:27
 */
public interface BrandService {

    PageInfo<Brand> findPage(Brand brand,Integer page, Integer size);


    PageInfo<Brand> findPage(Integer page, Integer size);

    List<Brand> findList(Brand brand);

    List<Brand> selectAll();

    void add(Brand brand);

    void update(Brand brand);
}

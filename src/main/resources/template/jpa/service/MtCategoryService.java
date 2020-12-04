package com.matech.common.service;

import com.matech.common.entity.MtCategoryTree;
import com.matech.common.model.MtCategoryTreeDTO;
import com.matech.framework.core.service.exception.NoSuchDataException;

import java.util.List;

public interface MtCategoryService {
    String CODE_SPLITTER = "-";
    /**
     * 根据主题类型获取目录树列表
     *
     * @param preCategoryCode 树类型
     * @return List<MtCategoryTree>
     */
    List<MtCategoryTreeDTO> findByCategory(String preCategoryCode);

    /**
     * 根据目录节点id删除目录树节点
     *
     * @param id 目录树节点id
     * @return 结果信息
     */
    Boolean deleteOneWithChildren(Long id) throws NoSuchDataException;

    MtCategoryTreeDTO findById(Long id);

    MtCategoryTreeDTO create(MtCategoryTree mtCategoryTree, String parentId);

    MtCategoryTreeDTO update(Long id, MtCategoryTree mtCategoryTree);
}

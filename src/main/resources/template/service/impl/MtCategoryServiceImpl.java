package com.matech.common.service.impl;

import com.github.wenhao.jpa.Specifications;
import com.matech.common.entity.MtCategoryTree;
import com.matech.common.mapper.MtCategoryTreeMapper;
import com.matech.common.model.MtCategoryTreeDTO;
import com.matech.common.repository.MtCategoryRepository;
import com.matech.common.service.MtCategoryService;
import com.matech.framework.core.service.exception.NoSuchDataException;
import com.matech.framework.core.service.filter.UnitFilter;
import com.matech.framework.core.service.utils.CycleAvoidingMappingContext;
import com.matech.framework.spring.json.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * 公共目录树service接口实现
 *
 * @author wq
 * @date 2019-1-22 10:16:03
 */
@Slf4j
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MtCategoryServiceImpl implements MtCategoryService {
    private final MtCategoryRepository mtCategoryRepository;
    private final MtCategoryTreeMapper mtCategoryTreeMapper;

    @Autowired
    public MtCategoryServiceImpl(MtCategoryRepository mtCategoryRepository, MtCategoryTreeMapper mtCategoryTreeMapper) {
        this.mtCategoryRepository = mtCategoryRepository;
        this.mtCategoryTreeMapper = mtCategoryTreeMapper;
    }

    @Override
    @UnitFilter
    @Transactional(rollbackFor = Exception.class)
    @Json(type = MtCategoryTreeDTO.class, filter = "remarks")
    public List<MtCategoryTreeDTO> findByCategory(String preCategoryCode) {
        List<MtCategoryTree> categoryTrees;
        if (StringUtils.isNotBlank(preCategoryCode)) {
            preCategoryCode = preCategoryCode + "%";
            categoryTrees = mtCategoryRepository.findByCategory(preCategoryCode);
        } else {
            categoryTrees = mtCategoryRepository.findByParentIsNullOrderByShowOrder();
        }
        for (MtCategoryTree categoryTree : categoryTrees) {
            Hibernate.initialize(categoryTree.getChildren());
        }
        return getChildren(categoryTrees);
    }

    private List<MtCategoryTreeDTO> getChildren(List<MtCategoryTree> categoryTrees) {
        List<MtCategoryTreeDTO> mtCategoryTreeDTOS = new ArrayList<>(16);
        MtCategoryTreeDTO mtCategoryTreeDTO;
        for (MtCategoryTree categoryTree : categoryTrees) {
            mtCategoryTreeDTO = this.mtCategoryTreeMapper.toDTONoChildren(categoryTree, new CycleAvoidingMappingContext());
            mtCategoryTreeDTOS.add(mtCategoryTreeDTO);
            if (categoryTree.getChildren() != null && !categoryTree.getChildren().isEmpty()) {
                mtCategoryTreeDTO.setChildren(getChildren(categoryTree.getChildren()));
            }
        }
        return mtCategoryTreeDTOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteOneWithChildren(Long id) throws NoSuchDataException {
        if (id != null) {
            Optional<MtCategoryTree> optional = mtCategoryRepository.findById(id);
            MtCategoryTree mtCategoryTree = optional.orElseThrow(() -> new NoSuchDataException(String.valueOf(id)));
            // TODO 这种删除如果存在三级,只会删除一级和二级, 待确认(2020-4-6 02:06:40)
            mtCategoryRepository.deleteAll(mtCategoryTree.getChildren());
            mtCategoryRepository.delete(mtCategoryTree);
            return true;
        }
        return false;
    }

    @Override
    @UnitFilter
    public MtCategoryTreeDTO findById(Long id) {
        Optional<MtCategoryTree> optional = mtCategoryRepository.findById(id);
        MtCategoryTree mtCategoryTree = optional.orElseThrow(() -> new NoSuchDataException(String.valueOf(id)));
        MtCategoryTreeDTO mtCategoryTreeDTO = this.mtCategoryTreeMapper.toDTO(mtCategoryTree, new CycleAvoidingMappingContext());
        mtCategoryTreeDTO.setChildren(null);
        return mtCategoryTreeDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MtCategoryTreeDTO create(MtCategoryTree mtCategoryTree, String parentId) {
        setShowOrder(mtCategoryTree);
        if (StringUtils.isEmpty(parentId) && mtCategoryTree.getParent() != null && mtCategoryTree.getParent().getId() != null) {
            parentId = String.valueOf(mtCategoryTree.getParent().getId());
        }
        if (StringUtils.isNotEmpty(parentId)) {
            final String newId = parentId;
            Optional<MtCategoryTree> optional = mtCategoryRepository.findById(Long.parseLong(newId));
            MtCategoryTree parent = optional.orElseThrow(() -> new NoSuchDataException(newId));
            mtCategoryTree.setParent(parent);
        }
        MtCategoryTree save = mtCategoryRepository.save(mtCategoryTree);
        return this.mtCategoryTreeMapper.toDTO(save, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MtCategoryTreeDTO update(Long id, MtCategoryTree mtCategoryTree) {
        Optional<MtCategoryTree> optional = mtCategoryRepository.findById(id);
        MtCategoryTree mtCategoryTreeOld = optional.orElseThrow(() -> new NoSuchDataException(String.valueOf(id)));
        BeanUtils.copyProperties(mtCategoryTree, mtCategoryTreeOld, "parent", "id", "children");
        MtCategoryTree save = mtCategoryRepository.save(mtCategoryTreeOld);
        return this.mtCategoryTreeMapper.toDTO(save, new CycleAvoidingMappingContext());
    }

    /** 排序 */
    private void setShowOrder(MtCategoryTree newEntity) {
        if (StringUtils.isEmpty(newEntity.getCode())) return;
        int lastIdx = newEntity.getCode().lastIndexOf(MtCategoryService.CODE_SPLITTER);
        if (lastIdx < 0) return;
        String lastCode = newEntity.getCode().substring(lastIdx + 1);
        if (NumberUtils.isParsable(lastCode)) {
            newEntity.setShowOrder(Integer.parseInt(lastCode.length() > 9 ? lastCode.substring(0, 9) : lastCode));
            return;
        }
        String prefixCode = newEntity.getCode().substring(0, lastIdx);
        List<MtCategoryTree> siblings = newEntity.getParent() != null ? newEntity.getParent().getChildren() : mtCategoryRepository.findAll(Specifications.<MtCategoryTree>and()
                .like("code", prefixCode + "-%")
                .eq("state", "NORMAL")
                .eq("parent.id", null)
                .build()); // 查找同级目录下的数据
        int showOrder = 0;
        if (siblings != null && !siblings.isEmpty()) {
            Optional<MtCategoryTree> maxShowOrderInsight = siblings.stream().max(Comparator.comparing(MtCategoryTree::getShowOrder));
            if (maxShowOrderInsight != null && maxShowOrderInsight.isPresent()) {
                showOrder = maxShowOrderInsight.get().getShowOrder() + 1;
            } else {
                showOrder = siblings.size() + 1;
            }
        }
        newEntity.setShowOrder(showOrder);
    }
}

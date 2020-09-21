package com.matech.common.repository;

import com.matech.common.entity.MtCategoryTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 公共目录树持久层接口
 *
 * @author wq
 * @date 2019-1-22 10:23:40
 */
@Repository
public interface MtCategoryRepository extends JpaRepository<MtCategoryTree, Long>, JpaSpecificationExecutor<MtCategoryTree> {

    /**
     * 根据资源目录code前缀查询对应的资源目录列表
     *
     * @param preCategoryCode 目录节点编码前缀
     * @return 目录节点列表
     */
    @Query("select u from MtCategoryTree u where u.code like :preCategoryCode and u.parent is null order by u.showOrder")
    List<MtCategoryTree> findByCategory(@Param("preCategoryCode") String preCategoryCode);

    List<MtCategoryTree> findByParentIsNullOrderByShowOrder();

    MtCategoryTree findFirstByCode(String code);

}

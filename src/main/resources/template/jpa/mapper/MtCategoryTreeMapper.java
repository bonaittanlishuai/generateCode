package com.matech.common.mapper;

import com.matech.common.entity.MtCategoryTree;
import com.matech.common.model.MtCategoryTreeDTO;
import com.matech.framework.core.service.mapper.Simple;
import com.matech.framework.core.service.utils.CycleAvoidingMappingContext;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MtCategoryTreeMapper {
    /**
     * 实体类转换为DTO类
     *
     * @param mtCategoryTree 实体
     * @param context   内容
     * @return DTO
     */
    @Mappings({
            @Mapping(target ="parent", ignore = true)
    })
    MtCategoryTreeDTO toDTO(MtCategoryTree mtCategoryTree, @Context CycleAvoidingMappingContext context);

    /**
     * 实体类转换为DTO类
     *
     * @param mtCategoryTree 实体
     * @param context   内容
     * @return DTO
     */
    @Simple
    @Mappings({
            @Mapping(target ="parent", ignore = true),
            @Mapping(target ="children", ignore = true)
    })
    MtCategoryTreeDTO toDTONoChildren(MtCategoryTree mtCategoryTree, @Context CycleAvoidingMappingContext context);
    /**
     * 转换修改信息到实体类
     *
     * @param mtCategoryTreeEditInfo 要更新的信息
     * @param context           内容
     * @return 实体
     */
    MtCategoryTree toEntity(MtCategoryTreeDTO mtCategoryTreeEditInfo, @Context CycleAvoidingMappingContext context);

    /**
     * 转换修改信息到实体类
     *
     * @param mtCategoryTreeEditInfo 要更新的信息
     * @param mtCategoryTree         实体数据
     * @param context           内容
     */
    void updateEntity(MtCategoryTreeDTO mtCategoryTreeEditInfo, @MappingTarget MtCategoryTree mtCategoryTree, @Context CycleAvoidingMappingContext context);

    /**
     * 实体集合转换为DTO集合
     *
     * @param mtCategoryTrees 实体集合
     * @return DTO集合
     */
    List<MtCategoryTreeDTO> toList(List<MtCategoryTree> mtCategoryTrees, @Context CycleAvoidingMappingContext context);
}

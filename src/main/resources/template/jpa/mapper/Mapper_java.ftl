package com.matech.dispatch.common.mapper;

import com.matech.dispatch.common.entity.SystemInfo;
import com.matech.dispatch.common.model.SystemInfoDTO;
import com.matech.dispatch.common.model.SystemInfoEditInfoDTO;
import com.matech.framework.core.service.mapper.Simple;
import com.matech.framework.core.service.utils.CycleAvoidingMappingContext;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 实体类与DTO的转换映射
 *
 * @author lms
 * @date 2020-11-04 09:32
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SystemInfoMapper {
    /**
     * 实体类转换为DTO类
     *
     * @param systemInfo 实体
     * @param context          内容
     * @return DTO
     */
    @Simple
    com.matech.dispatch.common.model.SystemInfoDTO toDTO(com.matech.dispatch.common.entity.SystemInfo systemInfo, @Context CycleAvoidingMappingContext context);

    /**
     * 转换修改信息到实体类
     *
     * @param systemInfoEditInfo 要更新的信息
     * @param context                  内容
     * @return 实体
     */
      com.matech.dispatch.common.entity.SystemInfo toEntity(SystemInfoEditInfoDTO systemInfoEditInfo, @Context CycleAvoidingMappingContext context);

    /**
     * 转换修改信息到实体类
     *
     * @param systemInfoEditInfo 要更新的信息
     * @param systemInfo         实体数据
     * @param context                  内容
     */
    void updateEntity(SystemInfoEditInfoDTO systemInfoEditInfo, @MappingTarget com.matech.dispatch.common.entity.SystemInfo systemInfo, @Context CycleAvoidingMappingContext context);

    /**
     * 实体集合转换为DTO集合
     *
     * @param systemInfos 实体集合
     * @param context           内容
     * @return 实体DTO集合信息
     */
    List<SystemInfoDTO> toList(List<SystemInfo> systemInfos, @Context CycleAvoidingMappingContext context);

}

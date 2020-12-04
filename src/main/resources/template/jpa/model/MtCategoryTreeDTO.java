package com.matech.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author lww
 * @date 2019-01-14
 */
@ApiModel(description = "通用分类树（根据分类编码前缀不同区分不同业务功能的树）")
@Getter
@Setter
@ToString(callSuper = true)
public class MtCategoryTreeDTO {

    @ApiModelProperty(position = 100, value = "id")
    private String id;

    @ApiModelProperty(value = "所属父级")
    private MtCategoryTreeDTO parent;

    @ApiModelProperty(value = "下级列表")
    private List<MtCategoryTreeDTO> children;

    @ApiModelProperty(value = "分类编码（XXX-001-002-003）", required = true)
    private String code;

    @ApiModelProperty(value = "分类名称", required = true)
    @Size(min = 1, max = 100)
    private String name;

    @ApiModelProperty(value = "描述")
    private String remarks;

    @ApiModelProperty(value = "排序", required = true)
    private Integer showOrder;
}

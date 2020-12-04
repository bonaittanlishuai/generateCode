package com.matech.common.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.matech.framework.core.service.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lww
 * @date 2019-01-14
 */
@ApiModel(description = "通用分类树（根据分类编码前缀不同区分不同业务功能的树）")
@Entity
@Table(name = "mt_comm_category")
@Getter
@Setter
public class MtCategoryTree extends AbstractBaseEntity{

    @ApiModelProperty(value = "所属父级")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    @JsonBackReference("MtCategoryTree")
    private MtCategoryTree parent;

    @ApiModelProperty(value = "下级列表", readOnly = true)
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("showOrder")
    private List<MtCategoryTree> children = new LinkedList<>();

    @ApiModelProperty(value = "分类编码（MODEL-001-002-003）", required = true)
    @Column(name = "code", length = 100, nullable = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String code;

    @ApiModelProperty(value = "分类名称", required = true)
    @Column(name = "name", length = 100, nullable = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @ApiModelProperty(value = "描述")
    @Column(name = "remarks")
    private String remarks;

    @ApiModelProperty(value = "排序", required = true)
    @Column(name = "show_order", nullable = false)
    private Integer showOrder;
}

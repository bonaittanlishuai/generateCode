package com.framework.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-11 19:23
 */
@Data
@ApiModel(description = "Brand",value = "Brand")
@Table(name = "tb_brand")
public class Brand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id",required = false)
    @Column(name = "id")
    private Integer id;

    @ApiModelProperty(value = "name",required = false)
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "image",required = false)
    @Column(name = "image")
    private String image;

    @ApiModelProperty(value = "letter",required = false)
    @Column(name = "letter")
    private String letter;

    @ApiModelProperty(value = "seq",required = false)
    @Column(name = "seq")
    private Integer seq;

}

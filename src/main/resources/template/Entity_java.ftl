package ${entityPackage};

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
@ApiModel(description = "${entityClassName}",value = "${entityClassName}")
@Table(name = "${tableName}")
public class ${entityClassName} implements Serializable {

<#list tableDetailInfos as tableDetailInfo>
    <#if tableDetailInfo.columnName=="id">
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    @ApiModelProperty(value = "${tableDetailInfo.columnName}",required = false)
    @Column(name = "${tableDetailInfo.columnName}")
    private ${tableDetailInfo.fieldSimpleType} ${tableDetailInfo.fieldName};

</#list>
}

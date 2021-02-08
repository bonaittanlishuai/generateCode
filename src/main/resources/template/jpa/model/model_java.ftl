package com.matech.dispatch.common.model;

import com.matech.framework.core.model.security.user.UserBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ming.Su.Li
 * @date 2021/1/13
 */
@ApiModel(description = "系统信息详细信息")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SystemInfoDTO extends SystemInfoBaseDTO {

    @ApiModelProperty(position = 300, value = "id")
    private String id;

    @ApiModelProperty(position = 310, value = "创建数据的用户ID，系统根据token自动填写", required = true)
    private UserBaseDTO createUser;

    @ApiModelProperty(position = 320, value = "创建数据的时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date createdTime;

    @ApiModelProperty(position = 330, value = "最后修改者的ID，系统根据token自动填写", required = true)
    private UserBaseDTO lastModifyUser;

    @ApiModelProperty(position = 340, value = "最后的修收时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date lastModifiedTime;
}


import { AbstractBaseEntity } from '@mt-framework-ng/core';

/**
 * 系统信息
 */
export interface SystemInfoDTO extends AbstractBaseEntity {

     /**
      * 名称
      */
     name?: string;
     /**
      * 状态
      */
     status?: string;

     /**
      * 系统地址
      */
     address?: string;

     /**
      * 系统用户
      */
     user?: string;

     /**
      * 系统密码
      */
     password?: string;
     /**
     * 数据源类型
     */
    datasourceType?: string;

     /**
      * 数据源地址
      */
     datasourceAddress?: string;

     /**
      * 数据源端口
      */
     datasourcePort?: string;

     /**
      * 数据源用户
      */
     datasourceUser?: string;

     /**
      * 数据源密码
      */
     datasourcePassword?: string;

}

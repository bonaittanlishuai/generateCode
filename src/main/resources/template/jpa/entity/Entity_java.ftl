package ${entityPackage};

import com.matech.framework.core.service.entity.AbstractBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
<#list entityImportPackages as entityImportPackage>
import ${entityImportPackage};
</#list>

/**
 *
 * @author tls
 * @date
 * ${tableRemark}
 */
@Entity
@Table(name = "${tableName}")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ${entityClassName} extends AbstractBaseEntity {



    <#list tableDetailInfos as tableDetailInfo>
          /**
           * ${tableDetailInfo.columnRemark}
           */
         @Column(name = "${tableDetailInfo.columnName}", length = 100, nullable = false)
         private ${tableDetailInfo.fieldSimpleType} ${tableDetailInfo.firstUpperCaseFieldName};


         public ${tableDetailInfo.fieldSimpleType} ${tableDetailInfo.getMethodName}() {
            return ${tableDetailInfo.fieldName};
         }
         public void  ${tableDetailInfo. setMethodName}(${tableDetailInfo.fieldSimpleType} ${tableDetailInfo.fieldName}) {
            this.${tableDetailInfo.fieldName} = ${tableDetailInfo.fieldName};
         }
    </#list>

    /**
     * 名称
     */
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    /**
     * 状态
     */
    @Column(name = "status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.NORMAL;

    /**
     * 系统地址
     */
    @Column(name = "address", nullable = false)
    private String address;
    /**
     * 系统用户
     */
    @Column(name = "user", nullable = false)
    private String user;
    /**
     * 系统密码
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 人大系统数据范围ID
     */
    @Column(name = "data_scope_organization")
    private String dataScopeOrganization;
    /**
     * 人大系统的工作岗位组织ID
     */
    @Column(name = "working_organization")
    private String workingOrganization;

    /**
     * 登录机构类型
     */
    @Column(name = "organization_type")
    private String organizationType;

    /**
     * 单位编码
     */
    @Column(name = "unit_umber", nullable = false)
    private String unitNumber;

    /**
     * 数据源类型
     */
    @Column(name = "datasource_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DataSourceTypeEnum datasourceType = DataSourceTypeEnum.MYSQL;

    /**
     * 数据源地址
     */
    @Column(name = "datasource_address", nullable = false)
    private String datasourceAddress;
    /**
     * 数据源端口
     */
    @Column(name = "datasource_port", nullable = false)
    private String datasourcePort;
    /**
     * 数据源用户
     */
    @Column(name = "datasource_user", nullable = false)
    private String datasourceUser;
    /**
     * 数据源密码
     */
    @Column(name = "datasource_password", nullable = false)
    private String datasourcePassword;

}

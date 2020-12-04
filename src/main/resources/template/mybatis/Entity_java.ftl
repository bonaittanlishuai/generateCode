package ${entityPackage};

import com.mt.newframework.framework.annotations.db.DbColumn;
import com.mt.newframework.framework.annotations.db.DbTable;
import com.mt.newframework.framework.annotations.db.MajorKey;
import com.mt.newframework.utils.page.Page;
<#list entityImportPackages as entityImportPackage>
import ${entityImportPackage};
</#list>

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-11 19:23
 */

/**
 * ${tableRemark}
 */
@DbTable(tableName = "${tableName}")
public class ${entityClassName} extends Page{

<#list tableDetailInfos as tableDetailInfo>
     /*
      * ${tableDetailInfo.columnRemark}
      */
     <#if tableDetailInfo.columnName=="id" || tableDetailInfo.columnName=="ID">
     @MajorKey
     </#if>
     @DbColumn(columnName="${tableDetailInfo.columnName}")
     private ${tableDetailInfo.fieldSimpleType} ${tableDetailInfo.fieldName};
</#list>

<#list tableDetailInfos as tableDetailInfo>
     public ${tableDetailInfo.fieldSimpleType} ${tableDetailInfo.getMethodName}() {
        return ${tableDetailInfo.fieldName};
     }
     public void  ${tableDetailInfo. setMethodName}(${tableDetailInfo.fieldSimpleType} ${tableDetailInfo.fieldName}) {
        this.${tableDetailInfo.fieldName} = ${tableDetailInfo.fieldName};
     }
</#list>
}

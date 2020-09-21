# generateCode
代码生成器

环境配置  java版本最好1.6以上 maven
#使用说用
执行 com下面的 StartMain

#baseMessage.properties的参数说明
访问路径，暂时只支持mysql  url 路径  user 用户名 password密码
url=jdbc:mysql://119.23.79.3:3306/cg_goods?characterEncoding=utf8&useSSL=false&serverTimezone=UTC
user=**
password=**

#generateinfo.properties 生成信息的配置
包名
controllerPackage=com.framework.controller
servicePackage=com.framework.service
serviceImplPackage=com.framework.service.impl
entityPackage=com.framework.dao.entity
daoPackage=com.framework.dao
daoImplPackage=com.framework.dao.impl
//生成 xml文件路径
mapperFilePath=/mybatisMapper
//生成的根目录
generateFileDir=C:/work/develop/idea_work_space/tanlishuai-generateCode/src/main/java
#删除生成的文件名的前缀 根据数据对应表的名称
removePrefix=tb_
#删除生成的文件名的后缀 根据数据对应表的名称
removeSuffix=_test
#指定生成的表 如果是空或者 * 表示全部  ，多个表名以逗号隔开  ab_* 或者 db_* 如果表不存在则不生的对应的
tableName=tb_brand


需要生成的模板文件请放到 resources/template 下面 

模板使用的是freemark技术  ${}进行注入

模板文件的
默认的controller_java.ftl  service_java.ftl serviceimpl_java.ftl
mapper_java.ftl entity_java.ftl  mapperxml_xml.ftl
mapperimpl_java.ftl
使用方式   固定名字_生成文件的类型.ftl

模板注入的参数
比如说表名 otms_brand 品牌表  字段    brand_name VARCHAR(50) 品牌字段名 good_name 商品名

 ${className}   OtmsBrand
 ${pathName}      otmsBrand
 ${firstLowerCaseClassName}  otmsBrand
 ${firstUpperCaseClassName} OtmsBrand
 ${classAlias}  otmsBrand
 ${tableRemark}  品牌表
 ${tableName}  otms_brand
 ${controllerPackage}  com.framework.controller
 ${serviceImplPackage}  com.framework.service.imp
 ${servicePackage}  com.framework.service
 ${entityPackage}   com.framework.dao.entity
 ${daoPackage}  com.framework.dao
 ${daoImplPackage} com.framework.dao.impl
 ${controllerClassName}   OtmsBrandController
 ${serviceImplClassName}  OtmsBrandServiceImpl
 ${serviceClassName} OtmsBrandService
 ${entityClassName}  OtmsBrand
 ${daoClassName}   OtmsBrandMapper
 ${daoImplClassName}   OtmsBrandMapperImpl
 ${controllerAliasName}   otmsBrandController
 ${serviceImplAliasName}  otmsBrandServiceImpl
 ${serviceAliasName}   otmsBrandService
 ${entityAliasName}  otmsBrand
 ${daoAliasName}  otmsBrandMapper
 ${daoImplAliasName}  otmsBrandMapper
 ${entityImportPackages} 实体类需要额外导入的包

 //表的字段信息
 <#list  tableDetailInfos as tableDetailInfo>
 ${ tableDetailInfo.columnName}   brand_name
 ${ tableDetailInfo.columnType} VARCHAR
 ${ tableDetailInfo.fieldName}  brandName
 ${ tableDetailInfo.fieldType} java.lang.String
 ${tableDetailInfo. fieldSimpleType} String
 ${ tableDetailInfo.firstUpperCaseFieldName}/ BrandName
 ${tableDetailInfo.getMethodName} getBrandName
 ${tableDetailInfo.setMethodName} setBrandName
 ${tableDetailInfo.columnRemark} 品牌字段名

 </#list>



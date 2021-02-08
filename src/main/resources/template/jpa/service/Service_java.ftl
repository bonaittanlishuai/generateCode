package com.matech.dispatch.common.service;

import com.matech.dispatch.common.model.SystemInfoDTO;
import com.matech.dispatch.common.model.SystemInfoEditInfoDTO;
import com.matech.framework.core.model.common.PageDataDTO;
import com.matech.framework.core.service.exception.NoSuchDataException;
import com.matech.insight.data.dto.DataSourceColumn;
import com.matech.insight.data.dto.DataSourceTable;
import com.matech.insight.data.exception.InvalidParamValueException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * 系统信息管理service接口
 * @author wq
 * @since 2019-1-29 10:02:34
 */
public interface SystemInfoService {

    /**
     * 测试系统信息数据源是否能连通
     * @param systemInfoEditInfoDTO 系统信息
     * @return 测试结果（为空则通过）
     */
    String check(SystemInfoEditInfoDTO systemInfoEditInfoDTO) throws Exception;

    /**
     * 分页查找系统信息
     * @param name   系统信息名称
     * @param page   页码
     * @param size   页面记录条数
     * @param sort   排序字段, 例如：字段1,asc,字段2,desc
     * @return 系统信息列表
     */
    PageDataDTO<com.matech.dispatch.common.model.SystemInfoDTO> findPage(String name, Integer page, Integer size, String sort);

    /**
     * 根据id删除一个系统信息
     * @param id 系统信息id
     * @return 删除的结果
     */
    Boolean deleteOneById(String id) throws NoSuchDataException;

    /**
     * 新增一个系统信息
     * @param systemInfoEditInfoDTO 系统信息对象
     * @return  保存后的系统信息信息
     */
    com.matech.dispatch.common.model.SystemInfoDTO create(SystemInfoEditInfoDTO systemInfoEditInfoDTO) throws InvalidParamValueException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException;

    /**
     *  根据系统信息id查询系统信息属性
     * @param id                            系统信息id
     * @return                              系统信息对象
     * @throws NoSuchDataException        未查询到数据
     * @throws IllegalBlockSizeException    AES加密解密异常
     * @throws InvalidKeyException          AES加密解密异常
     * @throws NoSuchPaddingException       AES加密解密异常
     * @throws NoSuchAlgorithmException     AES加密解密异常
     * @throws BadPaddingException          AES加密解密异常
     */
    com.matech.dispatch.common.model.SystemInfoDTO findOneById(String id) throws NoSuchDataException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException;

    /**
     *  修改系统信息属性
     * @param systemInfoEditInfoDTO    修改后的系统信息
     * @return  保存的系统信息
     * @throws NoSuchDataException 未找到对应系统信息,无法修改
     */
    com.matech.dispatch.common.model.SystemInfoDTO updateOneById(String id, SystemInfoEditInfoDTO systemInfoEditInfoDTO) throws NoSuchDataException, InvalidParamValueException;

    /**
     *  根据系统信息id查询所有库实例
     * @param id 系统信息id
     * @return  数据库实例名称
     */
    List<String> schema(String id) throws Exception;

    /**
     * 根据系统信息id和数据库实例查询所有表列表
     * @param id 系统信息id
     * @param dataBaseName 数据库实例名称
     * @return 数据库表列表
     * @throws NoSuchDataException 未找到系统信息时抛出异常
     */
    List<DataSourceTable> table(String id, String dataBaseName) throws Exception;

    /**
     * 根据系统信息id,库名和表名查询表中的所有字段信息
     * @param id 系统信息id
     * @param tableName 库名.表名
     * @return 字段列表
     * @throws NoSuchDataException 未找到系统信息抛出异常
     */
    List<DataSourceColumn> column(String id, String tableName) throws Exception;

    /**
     * 根据系统信息id预览数据
     * @param id 系统信息id
     * @param tableName 数据表名称(库名.表名)
     * @return 表数据
     * @throws NoSuchDataException 未找到系统信息时抛出异常
     */
    List<Map<String,Object>> data(String id, String tableName) throws Exception;

    /**
     * 根据ID获取
     * @param id
     * @return
     */
    com.matech.dispatch.common.entity.SystemInfo findSystemInfoById(String id);

    /**
     * 查找所有系统信息
     * @return
     */
	List<SystemInfoDTO> findAll();
}

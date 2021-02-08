package com.matech.dispatch.common.service;

import com.matech.dispatch.common.entity.SystemInfo;
import com.matech.dispatch.common.model.SystemInfoDTO;
import com.matech.dispatch.common.model.SystemInfoEditInfoDTO;
import com.matech.dispatch.task.enumeration.StatusEnum;
import com.matech.framework.core.model.common.PageDataDTO;
import com.matech.framework.core.service.exception.NoSuchDataException;
import com.matech.framework.core.service.utils.CycleAvoidingMappingContext;
import com.matech.framework.core.service.utils.PageDataUtil;
import com.matech.framework.spring.jpa.PageHelper;
import com.matech.insight.data.consts.Consts;
import com.matech.insight.data.dto.DataSourceColumn;
import com.matech.insight.data.dto.DataSourceTable;
import com.matech.insight.data.entity.data_source.DataSource;
import com.matech.insight.data.entity.data_source.DataSourceAttr;
import com.matech.insight.data.exception.InvalidParamValueException;
import com.matech.insight.engine.DataHandler;
import com.matech.insight.engine.DataHandlerFactory;
import com.matech.insight.util.aes.AesEncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.criteria.Predicate;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SystemInfoServiceImpl implements SystemInfoService {

    private final com.matech.dispatch.common.repository.SystemInfoRepository systemInfoRepository;
    private final com.matech.dispatch.common.mapper.SystemInfoMapper systemInfoMapper;

    @Autowired
    public SystemInfoServiceImpl(com.matech.dispatch.common.repository.SystemInfoRepository systemInfoRepository,
                                 com.matech.dispatch.common.mapper.SystemInfoMapper systemInfoMapper) {
        this.systemInfoRepository = systemInfoRepository;
        this.systemInfoMapper = systemInfoMapper;
    }

    @Override
    public String check(SystemInfoEditInfoDTO systemInfoEditInfo) throws Exception {
        DataHandler dao = DataHandlerFactory.getInstance().getDataHandler(systemInfoEditInfo.getDatasourceType());
        com.matech.dispatch.common.entity.SystemInfo systemInfo = systemInfoMapper.toEntity(systemInfoEditInfo, new CycleAvoidingMappingContext());
        DataSource dataSource = getDataSource(systemInfo);
        if (dao != null) {
            return dao.check(dataSource);
        }
        return "找不到["+systemInfoEditInfo.getDatasourceType()+"]对应的数据源";
    }

    private DataSource getDataSource(com.matech.dispatch.common.entity.SystemInfo systemInfo) throws InvalidParamValueException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException {
        DataSource dataSource = new DataSource();
        Map<String, Object> attrMap = new HashMap<>();
        attrMap.put("address", systemInfo.getDatasourceAddress());
        attrMap.put("port", systemInfo.getDatasourcePort());
        attrMap.put("user", systemInfo.getDatasourceUser());
        attrMap.put("password", systemInfo.getDatasourcePassword());
        dataSource.setAttrMap(attrMap);
        //设置数据源属性
        resolveDataSourceMap(dataSource);
        return dataSource;
    }

    @Override
    public PageDataDTO<com.matech.dispatch.common.model.SystemInfoDTO> findPage(String name, Integer page, Integer size, String sort) {
        Page<com.matech.dispatch.common.entity.SystemInfo> result;
        // 设置查询条件
        Specification<com.matech.dispatch.common.entity.SystemInfo> spec = (root, query, cb) -> {
            List<Predicate> conditions = new ArrayList<>();
            if (StringUtils.isNotBlank(name)) {
                conditions.add(cb.equal(root.get("name"), name));
            }
            conditions.add(cb.equal(root.get("status"), StatusEnum.NORMAL));
            if (!conditions.isEmpty()) {
                Predicate[] array = new Predicate[conditions.size()];
                query.where(conditions.toArray(array));
            }
            return null;
        };
        if (StringUtils.isBlank(sort)) {
            sort = Consts.SORT_KEY_ID_ASC;
        }
        // 分页查询数据源
        page = ((page == null || page <= 0) ? 1 : page);
        size = (size == null ? 1 : size);
        result = systemInfoRepository.findAll(spec, PageHelper.generatePageRequest(page - 1, size, sort));
        return PageDataUtil.toPageData(result, systemInfoMapper.toList(result.getContent(), new CycleAvoidingMappingContext()));
    }

    @Override
    @Transactional
    public Boolean deleteOneById(String id) throws NoSuchDataException {
        if (id != null) {
            com.matech.dispatch.common.entity.SystemInfo old = findSystemInfoById(id);
            old.setStatus(StatusEnum.DELETE);
            systemInfoRepository.save(old);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public com.matech.dispatch.common.model.SystemInfoDTO create(SystemInfoEditInfoDTO systemInfoEditInfoDTO) throws InvalidParamValueException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException {
        com.matech.dispatch.common.entity.SystemInfo systemInfo = systemInfoMapper.toEntity(systemInfoEditInfoDTO, new CycleAvoidingMappingContext());
        systemInfo.setStatus(StatusEnum.NORMAL);
        systemInfo = systemInfoRepository.saveAndFlush(systemInfo);
        return systemInfoMapper.toDTO(systemInfo, new CycleAvoidingMappingContext());

    }

    @Override
    public com.matech.dispatch.common.model.SystemInfoDTO findOneById(String id)throws NoSuchDataException {
        com.matech.dispatch.common.entity.SystemInfo systemInfo = findSystemInfoById(id);
        return systemInfoMapper.toDTO(systemInfo, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional
    public com.matech.dispatch.common.model.SystemInfoDTO updateOneById(String id, SystemInfoEditInfoDTO systemInfoEditInfoDTO) throws NoSuchDataException, InvalidParamValueException {
        com.matech.dispatch.common.entity.SystemInfo systemInfo = this.findSystemInfoById(id);
        systemInfoMapper.updateEntity(systemInfoEditInfoDTO, systemInfo, new CycleAvoidingMappingContext());
        systemInfo = systemInfoRepository.saveAndFlush(systemInfo);
        return systemInfoMapper.toDTO(systemInfo, new CycleAvoidingMappingContext());
    }

    @Override
    public List<String> schema(String id) throws Exception {
        List<String> data = new ArrayList<>();
        com.matech.dispatch.common.entity.SystemInfo systemInfo = findSystemInfoById(id);
        DataHandler dao = DataHandlerFactory.getInstance().getDataHandler(systemInfo.getDatasourceType().name());
        DataSource dataSource = getDataSource(systemInfo);
        if (dao != null) {
            data = dao.getDatabases(dataSource);
        }
        return data;
    }

    @Override
    public List<DataSourceTable> table(String id, String dataBaseName) throws Exception {
        List<DataSourceTable> data = new ArrayList<>();
        com.matech.dispatch.common.entity.SystemInfo systemInfo = findSystemInfoById(id);
        DataHandler dao = DataHandlerFactory.getInstance().getDataHandler(systemInfo.getDatasourceType().name());
        DataSource dataSource = getDataSource(systemInfo);
        if (dao != null) {
            data = dao.getTables(dataSource, dataBaseName, null);
        }
        return data;
    }

    @Override
    public List<DataSourceColumn> column(String id, String tableName) throws Exception {
        List<DataSourceColumn> data = new ArrayList<>();
        com.matech.dispatch.common.entity.SystemInfo systemInfo = findSystemInfoById(id);
        String[] tableMess = tableName.split("\\.");
        DataHandler dao = DataHandlerFactory.getInstance().getDataHandler(systemInfo.getDatasourceType().name());
        DataSource dataSource = getDataSource(systemInfo);
        if (dao != null) {
            data = dao.getColumns(dataSource, tableMess[0], tableMess[1]);
        }
        return data;
    }

    @Override
    public List<Map<String, Object>> data(String id, String tableName) throws Exception {
        List<Map<String, Object>> data = new ArrayList<>();
        com.matech.dispatch.common.entity.SystemInfo systemInfo = findSystemInfoById(id);
        String[] tableMess = tableName.split("\\.");
        DataHandler dao = DataHandlerFactory.getInstance().getDataHandler(systemInfo.getDatasourceType().name());
        DataSource dataSource = getDataSource(systemInfo);
        if (dao != null) {
            data = dao.getData(dataSource, tableMess[0], tableMess[1]);
        }
        return data;
    }

    /**
     * 新增或修改时设置数据源属性
     *
     * @param dataSource 数据源
     */
    private void resolveDataSourceMap(DataSource dataSource)
            throws InvalidParamValueException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException {
        Map<String, Object> mapAttr = dataSource.getAttrMap();
        if (mapAttr != null && !mapAttr.isEmpty()) {
            DataSourceAttr dataSourceAttr;
            List<DataSourceAttr> dataSourceAttrList = new ArrayList<>();
            // 遍历map,将所有的属性值保存
            for (Map.Entry<String, Object> map : mapAttr.entrySet()) {
                String k = StringUtils.isBlank(map.getKey()) ? "" : map.getKey();
                String v = map.getValue() == null ? "" : String.valueOf(map.getValue());
                //判断是否为密码,是的话则加密
                v = k.toUpperCase().contains(Consts.DATA_SOURCE_ATTR_KEY_PASSWORD) ? AesEncryptUtils.encrypt(v) : v;
                dataSourceAttr = new DataSourceAttr();
                dataSourceAttr.setAttrKey(k);
                dataSourceAttr.setAttrVal(String.valueOf(v));
                dataSourceAttr.setSource(dataSource);
                dataSourceAttrList.add(dataSourceAttr);
            }
            // 数据源设置属性
            dataSource.setAttrs(dataSourceAttrList);
        } else {
            throw new InvalidParamValueException("数据源属性不能为空!");
        }
    }

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return
     */
    @Override
    public com.matech.dispatch.common.entity.SystemInfo findSystemInfoById(String id) {
        Optional<SystemInfo> systemInfoOptional = systemInfoRepository.findById(Long.valueOf(id));
        return systemInfoOptional.orElseThrow(() -> new NoSuchDataException(id));
    }

    /**
     * 查找所有系统信息
     * @return
     */
    @Override
    public List<SystemInfoDTO> findAll() {
        // 设置查询条件
        Specification<com.matech.dispatch.common.entity.SystemInfo> spec = (root, query, cb) -> {
            List<Predicate> conditions = new ArrayList<>();
            conditions.add(cb.equal(root.get("status"), StatusEnum.NORMAL));
            if (!conditions.isEmpty()) {
                Predicate[] array = new Predicate[conditions.size()];
                query.where(conditions.toArray(array));
            }
            return null;
        };
        return systemInfoMapper.toList(systemInfoRepository.findAll(spec), new CycleAvoidingMappingContext());
    }


}

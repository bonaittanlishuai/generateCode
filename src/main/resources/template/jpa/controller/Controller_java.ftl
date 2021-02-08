package com.matech.dispatch.common.controller;

import com.matech.dispatch.common.model.SystemInfoDTO;
import com.matech.dispatch.common.model.SystemInfoEditInfoDTO;
import com.matech.dispatch.common.service.SystemInfoService;
import com.matech.framework.core.api.advice.SystemLog;
import com.matech.framework.core.model.common.PageDataDTO;
import com.matech.framework.core.model.common.SimpleDataDTO;
import com.matech.framework.core.model.security.log.OperationType;
import com.matech.framework.core.service.exception.NoSuchDataException;
import com.matech.insight.data.dto.DataSourceColumn;
import com.matech.insight.data.dto.DataSourceTable;
import com.matech.insight.data.exception.InvalidParamValueException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;



@Api(tags = {"系统信息管理接口"})
@RestController
@RequestMapping("/api/systemInfo")
public class SystemInfoController {

	private final SystemInfoService systemInfoService;

	@Autowired
	public SystemInfoController(SystemInfoService systemInfoService) {
		this.systemInfoService = systemInfoService;
	}

	@ApiOperation(value = "测试jdbc系统信息是否连通（返回为空则通过，否则返回错误信息）")
	@PostMapping(value = "/check")
	@PreAuthorize("hasAnyAuthority('SYSTEMINFO_RETRIEVE')")
	@SystemLog(dataType = "系统信息管理", operationType = OperationType.RETRIEVE, parameters = {0})
	public SimpleDataDTO<String> check(@ApiParam(value = "系统信息", required = true) @RequestBody SystemInfoEditInfoDTO systemInfo) throws Exception {
		return new SimpleDataDTO<>(systemInfoService.check(systemInfo));
	}

	@ApiOperation(value = "分页查找系统信息")
	@GetMapping(value = "")
	@PreAuthorize("hasAnyAuthority('SYSTEMINFO_RETRIEVE')")
	@SystemLog(dataType = "系统信息管理", operationType = OperationType.RETRIEVE, parameters = {0, 1, 2, 3})
	public PageDataDTO<com.matech.dispatch.common.model.SystemInfoDTO> findPage(@ApiParam(value = "系统信息名称") @RequestParam(value = "name", required = false) String name,
                                                                                @ApiParam(value = "页码", defaultValue = "1") @RequestParam(value = "page", required = false) Integer page,
                                                                                @ApiParam(value = "页面记录条数", defaultValue = "15") @RequestParam(value = "size", required = false) @Min(1) @Max(50) Integer size,
                                                                                @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false) String sort) {
		return systemInfoService.findPage(name, page, size, sort);
	}
	@ApiOperation(value = "查找所有系统信息")
	@GetMapping(value = "/all")
	@PreAuthorize("hasAnyAuthority('SYSTEMINFO_RETRIEVE')")
	@SystemLog(dataType = "系统信息管理", operationType = OperationType.RETRIEVE, parameters = {})
	public List<SystemInfoDTO> findAll() {
		return systemInfoService.findAll();
	}

	@ApiOperation(value = "根据ID查找系统信息")
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyAuthority('SYSTEMINFO_RETRIEVE')")
	@SystemLog(dataType = "系统信息管理", operationType = OperationType.RETRIEVE, parameters = {0})
	public com.matech.dispatch.common.model.SystemInfoDTO findById(@ApiParam(value = "系统信息ID", required = true) @PathVariable String id)
			throws NoSuchDataException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {
		return systemInfoService.findOneById(id);
	}

	@ApiOperation(value = "新增系统信息")
	@PostMapping(value = "/")
	@PreAuthorize("hasAnyAuthority('SYSTEMINFO_CREATE')")
	@SystemLog(dataType = "系统信息管理", operationType = OperationType.CREATE, parameters = {0})
	public com.matech.dispatch.common.model.SystemInfoDTO create(@ApiParam(value = "系统信息信息", required = true) @RequestBody @Validated SystemInfoEditInfoDTO entity) throws InvalidParamValueException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {
		return systemInfoService.create(entity);
	}

	@ApiOperation(value = "更新系统信息信息")
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyAuthority('SYSTEMINFO_UPDATE')")
	@SystemLog(dataType = "系统信息管理", operationType = OperationType.UPDATE, parameters = {0, 1})
	public com.matech.dispatch.common.model.SystemInfoDTO update(@ApiParam(value = "系统信息信息 ID", required = true) @PathVariable String id,
                                                                 @ApiParam(value = "系统信息信息", required = true) @RequestBody @Validated SystemInfoEditInfoDTO entity)
			throws NoSuchDataException, InvalidParamValueException {
		return systemInfoService.updateOneById(id, entity);
	}

	@ApiOperation(value = "删除系统信息信息")
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyAuthority('SYSTEMINFO_DELETE')")
	@SystemLog(dataType = "系统信息管理", operationType = OperationType.DELETE, parameters = {0})
	public SimpleDataDTO<Boolean> delete(@ApiParam(value = "系统信息ID", required = true) @PathVariable String id) throws NoSuchDataException {
		return new SimpleDataDTO<>(systemInfoService.deleteOneById(id));
	}

	@ApiOperation(value = "根据系统信息ID查找权限内的所有数据库")
	@GetMapping(value = "/{id}/scheme")
	@PreAuthorize("hasAnyAuthority('SYSTEMINFO_RETRIEVE')")
	@SystemLog(dataType = "系统信息管理", operationType = OperationType.RETRIEVE, parameters = {0})
	public List<String> schema(@ApiParam(value = "系统信息ID", required = true) @PathVariable String id) throws Exception {
		return systemInfoService.schema(id);
	}

	@ApiOperation(value = "根据系统信息ID及数据库查找权限内的所有表")
	@GetMapping(value = "/{id}/{schema}/table")
	@PreAuthorize("hasAnyAuthority('SYSTEMINFO_RETRIEVE')")
	@SystemLog(dataType = "系统信息管理", operationType = OperationType.RETRIEVE, parameters = {0, 1})
	public List<DataSourceTable> table(@ApiParam(value = "系统信息ID", required = true) @PathVariable String id,
	                                   @ApiParam(value = "JDBC数据库名", required = true) @PathVariable String schema) throws Exception {
		return systemInfoService.table(id, schema);
	}

	@ApiOperation(value = "根据系统信息ID及数据库名查找权限内的所有字段")
	@GetMapping(value = "/{id}/{tableName}/column")
	@PreAuthorize("hasAnyAuthority('SYSTEMINFO_RETRIEVE')")
	@SystemLog(dataType = "系统信息管理", operationType = OperationType.RETRIEVE, parameters = {0, 1})
	public List<DataSourceColumn> column(@ApiParam(value = "系统信息ID", required = true) @PathVariable String id,
	                                     @ApiParam(value = "JDBC数据表全名（库名.表名）", required = true) @PathVariable String tableName) throws Exception {
		return systemInfoService.column(id, tableName);
	}

	@ApiOperation(value = "根据系统信息ID及数据库表名预览数据")
	@GetMapping(value = "/{id}/{tableName}/data")
	@PreAuthorize("hasAnyAuthority('SYSTEMINFO_RETRIEVE')")
	@SystemLog(dataType = "系统信息管理", operationType = OperationType.RETRIEVE, parameters = {0, 1})
	public List<Map<String, Object>> data(@ApiParam(value = "系统信息ID", required = true) @PathVariable String id,
												  @ApiParam(value = "JDBC数据表全名（库名.表名）", required = true) @PathVariable String tableName) throws Exception {
		return systemInfoService.data(id, tableName);
	}
}

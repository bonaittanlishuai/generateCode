package com.matech.common.controller;

import com.matech.common.entity.MtCategoryTree;
import com.matech.common.model.MtCategoryTreeDTO;
import com.matech.common.service.MtCategoryService;
import com.matech.framework.core.api.advice.SystemLog;
import com.matech.framework.core.model.security.log.OperationType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"公共目录树管理接口"})
@RestController
@RequestMapping("/api/comm/category")
public class MtCategoryController {

	private final MtCategoryService mtCategoryService;

	@Autowired
	public MtCategoryController(MtCategoryService mtCategoryService) {
		this.mtCategoryService = mtCategoryService;
	}

	@ApiOperation(value = "根据主题类型获取目录树列表")
	@GetMapping(value = "/tree/{preCategoryCode}")
	@PreAuthorize("hasAnyAuthority('COMM_CATEGORY_RETRIEVE')")
	@SystemLog(dataType = "公共目录树管理", operationType = OperationType.RETRIEVE, parameters = {0})
	public List<MtCategoryTreeDTO> findByCategory(@ApiParam(value = "树类型", required = true) @PathVariable String preCategoryCode) {
        List<MtCategoryTreeDTO> byCategory = mtCategoryService.findByCategory(preCategoryCode);
        for (MtCategoryTreeDTO mtCategoryTreeDTO : byCategory) {
            System.out.println(mtCategoryTreeDTO.getChildren());
        }
        return byCategory;
	}
	@ApiOperation(value = "根据ID查找目录")
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyAuthority('COMM_CATEGORY_RETRIEVE')")
	@SystemLog(dataType = "公共目录树管理", operationType = OperationType.RETRIEVE, parameters = {0})
	public MtCategoryTreeDTO findById(@ApiParam(value = "目录树ID", required = true) @PathVariable String id) {
		return mtCategoryService.findById(Long.parseLong(id));
	}
	@ApiOperation(value = "新增目录")
	@PostMapping(value = "/")
	@PreAuthorize("hasAnyAuthority('COMM_CATEGORY_CREATE')")
	@SystemLog(dataType = "公共目录树管理", operationType = OperationType.CREATE, parameters = {0})
	public MtCategoryTreeDTO create(@ApiParam(value = "目录树信息", required = true) @RequestBody @Valid MtCategoryTree mtCategoryTree,
											@ApiParam(value = "目录树信息 ID") @RequestParam(required = false) String parentId) {

		return mtCategoryService.create(mtCategoryTree,parentId);
	}

	@ApiOperation(value = "更新目录")
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyAuthority('COMM_CATEGORY_UPDATE')")
	@SystemLog(dataType = "公共目录树管理", operationType = OperationType.UPDATE, parameters = {0, 1})
	public MtCategoryTreeDTO update(@ApiParam(value = "目录树信息 ID", required = true) @PathVariable String id,
											@ApiParam(value = "目录树信息", required = true) @RequestBody @Valid MtCategoryTree mtCategoryTree) {
		return mtCategoryService.update(Long.parseLong(id), mtCategoryTree);
	}

	@ApiOperation(value = "删除目录")
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyAuthority('COMM_CATEGORY_DELETE')")
	@SystemLog(dataType = "公共目录树管理", operationType = OperationType.DELETE, parameters = {0})
	public Boolean delete(@ApiParam(value = "目录树ID", required = true) @PathVariable String id) {
		return mtCategoryService.deleteOneWithChildren(Long.parseLong(id));
	}
}

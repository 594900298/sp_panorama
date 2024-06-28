package com.example.admin.controller;

import com.example.admin.dto.AreaAddDTO;
import com.example.admin.dto.AreaEditDTO;
import com.example.admin.service.AreaService;
import com.example.admin.vo.AreaDetailVO;
import com.example.admin.vo.AreaTreeVO;
import com.example.common.po.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/area")
@Api(tags = "地区管理")
public class AreaController {
    @Autowired
    private AreaService areaService;

    /**
     * 获取区域树
     *
     * @return
     */
    @ApiOperation("树形列表")
    @PostMapping("/getTree")
    private ResultData<List<AreaTreeVO>> getTree() {
        return ResultData.success(areaService.getTree());
    }

    /**
     * 添加区域
     *
     * @param areaAddDTO
     * @return
     */
    @ApiOperation("添加")
    @PostMapping("/add")
    private ResultData add(@Validated @RequestBody AreaAddDTO areaAddDTO) {
        if (areaService.add(areaAddDTO) != 0) {
            return ResultData.success(0, "添加成功");
        } else {
            return ResultData.fail(106, "添加失败");
        }
    }

    /**
     * 详情
     *
     * @param areaId
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("/detail/{id}")
    private ResultData<AreaDetailVO> detail(
            @ApiParam(name = "id", value = "地区id", required = true) @PathVariable("id") Integer areaId
    ) {
        return ResultData.success(areaService.detail(areaId));
    }

    /**
     * 编辑
     *
     * @param areaEditDTO
     * @return
     */
    @ApiOperation("编辑")
    @PostMapping("/edit")
    private ResultData edit(@Validated @RequestBody AreaEditDTO areaEditDTO) {
        if (areaService.edit(areaEditDTO) != 0) {
            return ResultData.success(0, "更新成功");
        } else {
            return ResultData.fail(106, "更新失败");
        }
    }

    /**
     * 修改显示状态
     *
     * @param areaId
     * @return
     */
    @ApiOperation("修改显示状态")
    @GetMapping("/editIsShow/{id}")
    private ResultData editIsShow(
            @ApiParam(name = "id", value = "地区id", required = true) @PathVariable("id") Integer areaId
    ) {
        if (areaService.editIsShow(areaId) != 0) {
            return ResultData.success(0, "更新成功");
        } else {
            return ResultData.fail(106, "更新失败");
        }
    }

    /**
     * 删除地区
     *
     * @param areaId
     * @return
     */
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    private ResultData delete(
            @ApiParam(name = "id", value = "地区id", required = true) @PathVariable("id") Integer areaId
    ) {
        if (areaService.delete(areaId) != 0) {
            return ResultData.success(0, "删除成功");
        } else {
            return ResultData.fail(106, "删除失败");
        }
    }
}

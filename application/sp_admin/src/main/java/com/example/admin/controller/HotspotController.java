package com.example.admin.controller;

import com.example.admin.dto.HotspotAddDTO;
import com.example.admin.dto.HotspotEditDTO;
import com.example.admin.dto.HotspotListDTO;
import com.example.admin.service.HotspotService;
import com.example.admin.vo.HotspotListVO;
import com.example.common.po.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/hotspot")
@Api(tags = "热点控制器")
public class HotspotController {
    @Resource
    private HotspotService hotspotService;

    /**
     * 列表
     *
     * @param hotspotListDTO
     * @return
     */
    @ApiOperation("列表")
    @PostMapping("/getList")
    private ResultData<List<HotspotListVO>> getList(@RequestBody HotspotListDTO hotspotListDTO) {
        return ResultData.success(hotspotService.getList(hotspotListDTO));
    }

    /**
     * 添加
     *
     * @param hotspotAddDTO
     * @return
     */
    @ApiOperation("添加")
    @PostMapping("/add")
    private ResultData add(@Validated @RequestBody HotspotAddDTO hotspotAddDTO) {
        if (hotspotService.add(hotspotAddDTO) != 0) {
            return ResultData.success(0, "添加成功");
        } else {
            return ResultData.fail(106, "添加失败");
        }
    }


    /**
     * 编辑
     *
     * @param hotspotEditDTO
     * @return
     */
    @ApiOperation("编辑")
    @PostMapping("/edit")
    private ResultData edit(@Validated @RequestBody HotspotEditDTO hotspotEditDTO) {
        if (hotspotService.edit(hotspotEditDTO) != 0) {
            return ResultData.success(0, "更新成功");
        } else {
            return ResultData.fail(106, "更新失败");
        }
    }

    /**
     * 删除
     *
     * @param hotspotId
     * @return
     */
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    private ResultData delete(
            @ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") Integer hotspotId) {
        if (hotspotService.delete(hotspotId) != 0) {
            return ResultData.success(0, "删除成功");
        } else {
            return ResultData.fail(106, "删除失败");
        }
    }
}

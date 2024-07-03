package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.dto.*;
import com.example.admin.service.SceneService;
import com.example.admin.vo.SceneDetailVo;
import com.example.admin.vo.SceneListVo;
import com.example.admin.vo.ScenePaginateVo;
import com.example.common.bo.PageParamBO;
import com.example.common.po.PageResult;
import com.example.common.po.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scene")
@Api(tags = "场景接口")
public class SceneController {
    @Autowired
    private SceneService sceneService;

    /**
     * 分页
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @ApiOperation("分页")
    @PostMapping("/paginate")
    private ResultData<List<ScenePaginateVo>> paginate(
            @ApiParam(name = "pageIndex", value = "当前页数", required = true) @RequestParam Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示条目个数", required = true) @RequestParam Integer pageSize,
            @RequestBody ScenePaginateDTO scenePaginateDTO
    ) {
        // 查询数据
        IPage page = sceneService.getPaginate(new PageParamBO(pageIndex, pageSize), scenePaginateDTO);
        //构建响应对象
        return ResultData.success(page.getRecords(), new PageResult(page));
    }

    /**
     * 列表
     *
     * @param sceneListDTO
     * @return
     */
    @ApiOperation("列表")
    @PostMapping("/getList")
    private ResultData<List<SceneListVo>> getList(@RequestBody SceneListDTO sceneListDTO) {
        return ResultData.success(sceneService.getList(sceneListDTO));
    }

    /**
     * 添加
     *
     * @param sceneAddDTO
     * @return
     */
    @ApiOperation("添加")
    @PostMapping("/add")
    private ResultData add(@Validated @RequestBody SceneAddDTO sceneAddDTO) {
        if (sceneService.add(sceneAddDTO) != 0) {
            return ResultData.success(0, "添加成功");
        } else {
            return ResultData.fail(106, "添加失败");
        }
    }

    /**
     * 详情
     *
     * @param sceneId
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("/detail/{id}")
    private ResultData<SceneDetailVo> detail(
            @ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") Integer sceneId
    ) {
        return ResultData.success(sceneService.detail(sceneId));
    }

    /**
     * 编辑
     *
     * @param sceneEditDTO
     * @return
     */
    @ApiOperation("编辑")
    @PostMapping("/edit")
    private ResultData edit(@Validated @RequestBody SceneEditDTO sceneEditDTO) {
        if (sceneService.edit(sceneEditDTO) != 0) {
            return ResultData.success(0, "更新成功");
        } else {
            return ResultData.fail(106, "更新失败");
        }
    }

    /**
     * 修改显示状态
     *
     * @return
     */
    @ApiOperation("修改显示状态")
    @GetMapping("/editIsShow/{id}")
    private ResultData editIsShow(
            @ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") Integer sceneId
    ) {
        if (sceneService.editIsShow(sceneId) != 0) {
            return ResultData.success(0, "更新成功");
        } else {
            return ResultData.fail(106, "更新失败");
        }
    }

    /**
     * 更新排序
     *
     * @param sceneEditSortDTO
     * @return
     */
    @ApiOperation("更新排序")
    @PostMapping("/editSort")
    private ResultData editSort(@Validated @RequestBody SceneEditSortDTO sceneEditSortDTO) {
        if (sceneService.editSort(sceneEditSortDTO) != 0) {
            return ResultData.success(0, "更新成功");
        } else {
            return ResultData.fail(106, "更新失败");
        }
    }

    /**
     * 删除
     *
     * @param sceneId
     * @return
     */
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    private ResultData delete(
            @ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") Integer sceneId) {
        if (sceneService.delete(sceneId) != 0) {
            return ResultData.success(0, "删除成功");
        } else {
            return ResultData.fail(106, "删除失败");
        }
    }
}

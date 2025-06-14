package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.dto.*;
import com.example.admin.service.SceneService;
import com.example.admin.vo.SceneDetailVO;
import com.example.admin.vo.SceneListVO;
import com.example.admin.vo.ScenePaginateVO;
import com.example.common.bo.PageParamBO;
import com.example.common.vo.PageResultVO;
import com.example.common.vo.ResultDataVO;
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
@CrossOrigin(origins = "*")
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
    private ResultDataVO<List<ScenePaginateVO>> paginate(
            @ApiParam(name = "pageIndex", value = "当前页数", required = true) @RequestParam Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示条目个数", required = true) @RequestParam Integer pageSize,
            @RequestBody ScenePaginateDTO scenePaginateDTO
    ) {
        // 查询数据
        IPage page = sceneService.getPaginate(new PageParamBO(pageIndex, pageSize), scenePaginateDTO);
        //构建响应对象
        return ResultDataVO.success(page.getRecords(), new PageResultVO(page));
    }

    /**
     * 列表
     *
     * @param sceneListDTO
     * @return
     */
    @ApiOperation("列表")
    @PostMapping("/getList")
    private ResultDataVO<List<SceneListVO>> getList(@RequestBody SceneListDTO sceneListDTO) {
        return ResultDataVO.success(sceneService.getList(sceneListDTO));
    }

    /**
     * 添加
     *
     * @param sceneAddDTO
     * @return
     */
    @ApiOperation("添加")
    @PostMapping("/add")
    private ResultDataVO add(@Validated @RequestBody SceneAddDTO sceneAddDTO) {
        if (sceneService.add(sceneAddDTO) != 0) {
            return ResultDataVO.success(0, "添加成功");
        } else {
            return ResultDataVO.fail(106, "添加失败");
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
    private ResultDataVO<SceneDetailVO> detail(
            @ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") Integer sceneId
    ) {
        return ResultDataVO.success(sceneService.detail(sceneId));
    }

    /**
     * 获取xml
     *
     * @param sceneId
     * @return
     */
    @ApiOperation("获取xml")
    @GetMapping(value = "/getXml/{id}", produces = "application/xhtml+xml;charset=UTF-8")
    private String getXml(
            @ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") Integer sceneId
    ) {
        return sceneService.getXml(sceneId);
    }

    /**
     * 编辑
     *
     * @param sceneEditDTO
     * @return
     */
    @ApiOperation("编辑")
    @PostMapping("/edit")
    private ResultDataVO edit(@Validated @RequestBody SceneEditDTO sceneEditDTO) {
        if (sceneService.edit(sceneEditDTO) != 0) {
            return ResultDataVO.success(0, "更新成功");
        } else {
            return ResultDataVO.fail(106, "更新失败");
        }
    }

    /**
     * 修改显示状态
     *
     * @return
     */
    @ApiOperation("修改显示状态")
    @GetMapping("/editIsShow/{id}")
    private ResultDataVO editIsShow(
            @ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") Integer sceneId
    ) {
        if (sceneService.editIsShow(sceneId) != 0) {
            return ResultDataVO.success(0, "更新成功");
        } else {
            return ResultDataVO.fail(106, "更新失败");
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
    private ResultDataVO editSort(@Validated @RequestBody SceneEditSortDTO sceneEditSortDTO) {
        if (sceneService.editSort(sceneEditSortDTO) != 0) {
            return ResultDataVO.success(0, "更新成功");
        } else {
            return ResultDataVO.fail(106, "更新失败");
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
    private ResultDataVO delete(
            @ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") Integer sceneId) {
        if (sceneService.delete(sceneId) != 0) {
            return ResultDataVO.success(0, "删除成功");
        } else {
            return ResultDataVO.fail(106, "删除失败");
        }
    }
}

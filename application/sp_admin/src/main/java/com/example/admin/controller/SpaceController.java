package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.dto.SpaceAddDTO;
import com.example.admin.dto.SpaceEditDTO;
import com.example.admin.dto.SpaceEditSortDTO;
import com.example.admin.service.SpaceService;
import com.example.admin.vo.SpaceDetailVo;
import com.example.admin.vo.SpaceListVo;
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
@RequestMapping("/space")
@Api(tags = "空间接口")
public class SpaceController {
    @Autowired
    private SpaceService spaceService;

    /**
     * 分页
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @ApiOperation("分页")
    @PostMapping("/paginate")
    private ResultData paginate(
            @ApiParam(name = "pageIndex", value = "当前页数", required = true) @RequestParam Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示条目个数", required = true) @RequestParam Integer pageSize
    ) {
        // 查询数据
        IPage page = spaceService.getPaginate(new PageParamBO(pageIndex, pageSize));
        //构建响应对象
        return ResultData.success(page.getRecords(), new PageResult(page));
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("列表")
    @GetMapping("/getList")
    private ResultData<List<SpaceListVo>> getList() {
        return ResultData.success(spaceService.getList());
    }

    /**
     * 添加
     *
     * @param spaceAddDTO
     * @return
     */
    @ApiOperation("添加")
    @PostMapping("/add")
    private ResultData add(@Validated @RequestBody SpaceAddDTO spaceAddDTO) {
        if (spaceService.add(spaceAddDTO) != 0) {
            return ResultData.success(0, "添加成功");
        } else {
            return ResultData.fail(106, "添加失败");
        }
    }


    /**
     * 详情
     *
     * @param spaceId
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("/detail/{id}")
    private ResultData<SpaceDetailVo> detail(
            @ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") Integer spaceId
    ) {
        return ResultData.success(spaceService.detail(spaceId));
    }

    /**
     * 编辑
     *
     * @param spaceEditDTO
     * @return
     */
    @ApiOperation("编辑")
    @PostMapping("/edit")
    private ResultData edit(@Validated @RequestBody SpaceEditDTO spaceEditDTO) {
        if (spaceService.edit(spaceEditDTO) != 0) {
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
            @ApiParam(name = "id", value = "主键\"", required = true) @PathVariable("id") Integer spaceId
    ) {
        if (spaceService.editIsShow(spaceId) != 0) {
            return ResultData.success(0, "更新成功");
        } else {
            return ResultData.fail(106, "更新失败");
        }
    }

    /**
     * 更新排序
     *
     * @param spaceEditSortDTO
     * @return
     */
    @ApiOperation("更新排序")
    @PostMapping("/editSort")
    private ResultData editSort(@Validated @RequestBody SpaceEditSortDTO spaceEditSortDTO) {
        if (spaceService.editSort(spaceEditSortDTO) != 0) {
            return ResultData.success(0, "更新成功");
        } else {
            return ResultData.fail(106, "更新失败");
        }
    }

    /**
     * 删除
     *
     * @param spaceId
     * @return
     */
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    private ResultData delete(
            @ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") Integer spaceId) {
        if (spaceService.delete(spaceId) != 0) {
            return ResultData.success(0, "删除成功");
        } else {
            return ResultData.fail(106, "删除失败");
        }
    }
}

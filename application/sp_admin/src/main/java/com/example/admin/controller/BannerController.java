package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.dto.BannerAddDTO;
import com.example.admin.dto.BannerEditDTO;
import com.example.admin.dto.BannerEditSortDTO;
import com.example.admin.dto.BannerPaginateDTO;
import com.example.admin.service.BannerService;
import com.example.admin.vo.BannerDetailVO;
import com.example.common.bo.PageParamBO;
import com.example.common.po.PageResult;
import com.example.common.po.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banner")
@Api(tags = "轮播图管理")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    /**
     * 分页
     *
     * @param pageIndex
     * @param pageSize
     * @param bannerPaginateDTO
     * @return
     */
    @ApiOperation("分页")
    @PostMapping("/paginate")
    private ResultData paginate(
            @ApiParam(name = "pageIndex", value = "当前页数", required = true) @RequestParam Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示条目个数", required = true) @RequestParam Integer pageSize,
            @RequestBody BannerPaginateDTO bannerPaginateDTO
    ) {
        // 查询数据
        IPage page = bannerService.getPaginate(new PageParamBO(pageIndex, pageSize), bannerPaginateDTO);
        //构建响应对象
        return ResultData.success(page.getRecords(), new PageResult(page));
    }

    /**
     * 添加
     *
     * @param bannerAddDTO
     * @return
     */
    @ApiOperation("添加")
    @PostMapping("/add")
    private ResultData add(@Validated @RequestBody BannerAddDTO bannerAddDTO) {
        if (bannerService.add(bannerAddDTO) != 0) {
            return ResultData.success(0, "添加成功");
        } else {
            return ResultData.fail(106, "添加失败");
        }
    }

    /**
     * 详情
     *
     * @param bannerId
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("/detail/{id}")
    private ResultData<BannerDetailVO> detail(
            @ApiParam(name = "id", value = "轮播id", required = true) @PathVariable("id") Integer bannerId
    ) {
        return ResultData.success(bannerService.detail(bannerId));
    }

    /**
     * 编辑
     *
     * @param bannerEditDTO
     * @return
     */
    @ApiOperation("编辑")
    @PostMapping("/edit")
    private ResultData edit(@Validated @RequestBody BannerEditDTO bannerEditDTO) {
        if (bannerService.edit(bannerEditDTO) != 0) {
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
            @ApiParam(name = "id", value = "轮播id", required = true) @PathVariable("id") Integer bannerId
    ) {
        if (bannerService.editIsShow(bannerId) != 0) {
            return ResultData.success(0, "更新成功");
        } else {
            return ResultData.fail(106, "更新失败");
        }
    }

    /**
     * 更新排序
     *
     * @param bannerEditSortDTO
     * @return
     */
    @ApiOperation("更新排序")
    @PostMapping("/editSort")
    private ResultData editSort(@Validated @RequestBody BannerEditSortDTO bannerEditSortDTO) {
        if (bannerService.editSort(bannerEditSortDTO) != 0) {
            return ResultData.success(0, "更新成功");
        } else {
            return ResultData.fail(106, "更新失败");
        }
    }

    /**
     * 删除
     *
     * @param bannerId
     * @return
     */
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    private ResultData delete(
            @ApiParam(name = "id", value = "轮播id", required = true) @PathVariable("id") Integer bannerId) {
        if (bannerService.delete(bannerId) != 0) {
            return ResultData.success(0, "删除成功");
        } else {
            return ResultData.fail(106, "删除失败");
        }
    }
}

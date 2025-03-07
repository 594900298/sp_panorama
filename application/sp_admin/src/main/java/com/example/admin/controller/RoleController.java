package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.dto.RoleAddDTO;
import com.example.admin.dto.RoleEditDTO;
import com.example.admin.dto.RoleEditSortDTO;
import com.example.admin.service.RoleService;
import com.example.admin.vo.RoleDetailVO;
import com.example.common.bo.PageParamBO;
import com.example.common.vo.PageResultVO;
import com.example.common.vo.ResultDataVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@Api(tags = "权限角色")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 分页
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @ApiOperation("分页")
    @PostMapping("/paginate")
    public ResultDataVO paginate(
            @ApiParam(name = "pageIndex", value = "当前页数", required = true) @RequestParam Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示条目个数", required = true) @RequestParam Integer pageSize
    ) {
        IPage page = roleService.getPaginate(new PageParamBO(pageIndex, pageSize));
        //构建响应对象
        return ResultDataVO.success(page.getRecords(), new PageResultVO(page));
    }

    /**
     * 添加
     *
     * @param roleAddDTO
     * @return
     */
    @ApiOperation("添加")
    @PostMapping("/add")
    public ResultDataVO add(@Validated @RequestBody RoleAddDTO roleAddDTO) {
        if (roleService.add(roleAddDTO) != 0) {
            return ResultDataVO.success(0, "添加成功");
        } else {
            return ResultDataVO.fail(106, "添加失败");
        }
    }

    /**
     * 详情
     *
     * @param roleId
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("/detail/{id}")
    public ResultDataVO<RoleDetailVO> detail(
            @ApiParam(name = "id", value = "角色id", required = true) @PathVariable("id") Integer roleId
    ) {
        return ResultDataVO.success(roleService.detail(roleId));
    }

    /**
     * 更新
     *
     * @param roleEditDTO
     * @return
     */
    @ApiOperation("更新")
    @PostMapping("/edit")
    public ResultDataVO edit(@Validated @RequestBody RoleEditDTO roleEditDTO) {
        if (roleService.edit(roleEditDTO) != 0) {
            return ResultDataVO.success(0, "更新成功");
        } else {
            return ResultDataVO.fail(106, "更新失败");
        }
    }

    /**
     * 编辑排序
     *
     * @param roleEditSortDTO
     * @return
     */
    @ApiOperation("编辑排序")
    @PostMapping("/editSort")
    public ResultDataVO editSort(@RequestBody RoleEditSortDTO roleEditSortDTO) {
        if (roleService.editSort(roleEditSortDTO) != 0) {
            return ResultDataVO.success(0, "更新成功");
        } else {
            return ResultDataVO.fail(106, "更新失败");
        }
    }

    /**
     * 删除
     *
     * @param roleId
     * @return
     */
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    public ResultDataVO delete(@ApiParam(name = "id", value = "角色id", required = true) @PathVariable("id") Integer roleId) {
        if (roleService.delete(roleId) != 0) {
            return ResultDataVO.success(0, "删除成功");
        } else {
            return ResultDataVO.fail(106, "删除失败");
        }
    }
}

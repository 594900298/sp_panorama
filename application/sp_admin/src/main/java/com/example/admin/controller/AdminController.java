package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.dto.*;
import com.example.admin.service.AdminService;
import com.example.admin.vo.AdminDetailVO;
import com.example.admin.vo.AdminPaginateVO;
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
@RequestMapping("/admin")
@Api(tags = "管理员")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 管理员分页
     * @return
     */
    @ApiOperation("分页")
    @PostMapping("/paginate")
    public ResultDataVO<List<AdminPaginateVO>> paginate(
            @ApiParam(name = "pageIndex",value = "当前页数",required = true) @RequestParam Integer pageIndex,
            @ApiParam(name = "pageSize",value = "每页显示条目个数",required = true) @RequestParam Integer pageSize
    ) {
        IPage page = adminService.getPaginate(new PageParamBO(pageIndex,pageSize));
        //构建响应对象
        return ResultDataVO.success(page.getRecords(), new PageResultVO(page));
    }

    /**
     * 添加
     * @param adminAddDTO
     * @return
     */
    @ApiOperation("添加")
    @PostMapping("/add")
    public ResultDataVO add(@Validated @RequestBody AdminAddDTO adminAddDTO) {
        if (adminService.add(adminAddDTO)!=0) {
            return ResultDataVO.success(null, "添加成功");
        } else {
            return ResultDataVO.fail(106, "添加失败");
        }
    }

    /**
     * 获取详情
     * @param userId
     * @return
     */
    @ApiOperation("获取详情")
    @GetMapping("/detail/{id}")
    public ResultDataVO<AdminDetailVO> detail(
            @ApiParam(name = "id",value = "管理员id",required = true) @PathVariable("id") Integer userId
    ) {
        return ResultDataVO.success(adminService.detail(userId));
    }


    /**
     * 编辑
     * @param adminEditDTO
     * @return
     */
    @ApiOperation("编辑")
    @PostMapping("/edit")
    public ResultDataVO edit(
            @Validated @RequestBody AdminEditDTO adminEditDTO
    ) {
        if (adminService.edit(adminEditDTO)!=0) {
            return ResultDataVO.success(0, "更新成功");
        } else {
            return ResultDataVO.fail(106, "更新失败");
        }
    }

    /**
     * 修改状态
     * @return
     */
    @ApiOperation("修改状态")
    @GetMapping("/editStatus/{id}")
    public ResultDataVO editStatus(
            @ApiParam(name = "id",value = "管理员id",required = true) @PathVariable("id") Integer userId
    ) {
        if (adminService.editStatus(userId)!=0) {
            return ResultDataVO.success(0, "更新成功");
        } else {
            return ResultDataVO.fail(106, "更新失败");
        }
    }

    /**
     * 修改排序
     * @param adminEditSortDTO
     * @return
     */
    @ApiOperation("修改排序")
    @PostMapping("/editSort")
    public ResultDataVO editSort(@Validated @RequestBody AdminEditSortDTO adminEditSortDTO) {
        if (adminService.editSort(adminEditSortDTO)!=0) {
            return ResultDataVO.success(0, "更新成功");
        } else {
            return ResultDataVO.fail(106, "更新失败");
        }
    }

    /**
     * 修改密码
     * @param adminEditPasswordDTO
     * @return
     */
    @ApiOperation("修改密码")
    @PostMapping("/editPassword")
    public ResultDataVO editPassword(
            @Validated @RequestBody AdminEditPasswordDTO adminEditPasswordDTO
    ) {
        if (adminService.editPassword(adminEditPasswordDTO)!=0) {
            return ResultDataVO.success(0, "更新成功");
        } else {
            return ResultDataVO.fail(106, "更新失败");
        }
    }

    /**
     * 本账号修改密码
     * @param modifyPasswordDto
     * @return
     */
    @ApiOperation("本账号修改密码")
    @PostMapping("/modifyPassword")
    public ResultDataVO modifyPassword(
            @Validated @RequestBody ModifyPasswordDTO modifyPasswordDto
    ) {
        if (adminService.modifyPassword(modifyPasswordDto)!=0) {
            return ResultDataVO.success(0, "更新成功");
        } else {
            return ResultDataVO.fail(106, "更新失败");
        }
    }


    /**
     * 删除
     * @param userId
     * @return
     */
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    public ResultDataVO delete(@ApiParam(name = "id",value = "管理员id",required = true) @PathVariable("id") Integer userId) {
        if (adminService.delete(userId)!=0) {
            return ResultDataVO.success(0, "更新成功");
        } else {
            return ResultDataVO.fail(106, "更新失败");
        }
    }
}

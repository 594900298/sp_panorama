package com.example.admin.controller;

import com.example.admin.dto.RuleAddDTO;
import com.example.admin.dto.RuleEditDTO;
import com.example.admin.service.RuleService;
import com.example.admin.vo.RuleDetailVO;
import com.example.admin.vo.RuleTreeVO;
import com.example.common.po.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rule")
@Api(tags = "权限规则")
public class RuleController {
    @Autowired
    private RuleService ruleService;

    /**
     * 获取属性结构
     *
     * @return
     */
    @ApiOperation("树形列表")
    @GetMapping("/getTree")
    public ResultData<List<RuleTreeVO>> getTree() {
        return ResultData.success(
                ruleService.getTree()
        );
    }

    /**
     * 添加
     *
     * @param ruleAddDTO
     * @return
     */
    @ApiOperation("添加")
    @PostMapping("/add")
    public ResultData add(@Validated @RequestBody RuleAddDTO ruleAddDTO) {
        if (ruleService.add(ruleAddDTO) != 0) {
            return ResultData.success(0, "添加成功");
        } else {
            return ResultData.fail(106, "添加失败");
        }
    }

    /**
     * 详情
     *
     * @param ruleId
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("/detail/{id}")
    public ResultData<RuleDetailVO> detail(
            @ApiParam(name = "id", value = "规则id", required = true) @PathVariable("id") Integer ruleId
    ) {
        return ResultData.success(ruleService.detail(ruleId));
    }

    /**
     * 编辑
     *
     * @param ruleEditDTO
     * @return
     */
    @ApiOperation("编辑")
    @PostMapping("/edit")
    public ResultData edit(@Validated @RequestBody RuleEditDTO ruleEditDTO) {
        if (ruleService.edit(ruleEditDTO) != 0) {
            return ResultData.success(0, "更新成功");
        } else {
            return ResultData.fail(106, "更新失败");
        }
    }

    /**
     * 删除
     *
     * @param ruleId
     * @return
     */
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    public ResultData delete(
            @ApiParam(name = "id", value = "规则id", required = true) @PathVariable("id") Integer ruleId
    ) {
        if (ruleService.delete(ruleId) != 0) {
            return ResultData.success(0, "删除成功");
        } else {
            return ResultData.fail(106, "删除失败");
        }
    }

}

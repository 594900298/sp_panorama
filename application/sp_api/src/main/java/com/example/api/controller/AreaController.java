package com.example.api.controller;

import com.example.api.service.AreaService;
import com.example.api.vo.AreaTreeVO;
import com.example.common.po.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("ApiAreaController")
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
}

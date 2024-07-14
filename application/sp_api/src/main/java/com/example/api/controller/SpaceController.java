package com.example.api.controller;

import com.example.api.service.SpaceService;
import com.example.api.vo.SpaceDetailVO;
import com.example.common.po.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/space")
@Api(tags = "空间")
@CrossOrigin(origins = "*")
public class SpaceController {
    @Resource
    private SpaceService spaceService;

    /**
     * 详情
     *
     * @param spaceId
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("/detail/{id}")
    private ResultData<SpaceDetailVO> detail(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") Integer spaceId) {
        return ResultData.success(spaceService.detail(spaceId));
    }

    @ApiOperation("获取xml")
    @GetMapping(value = "/getXml/{id}", produces = "application/xhtml+xml;charset=UTF-8")
    private String getXml(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") Integer spaceId) {
        return spaceService.getXml(spaceId);
    }
}

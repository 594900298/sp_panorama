package com.example.api.controller;

import com.example.api.service.BannerService;
import com.example.api.vo.BannerSelectVO;
import com.example.common.vo.ResultDataVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("ApiBanner")
@RequestMapping("/banner")
@Api(tags = "轮播图")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation("列表")
    @GetMapping("/getList")
    private ResultDataVO<List<BannerSelectVO>> getList() {
        return ResultDataVO.success(bannerService.getList());
    }
}

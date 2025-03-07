package com.example.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.api.service.SpaceService;
import com.example.api.vo.SpaceDetailVO;
import com.example.api.vo.SpaceListVO;
import com.example.api.vo.SpacePaginateVO;
import com.example.common.bo.PageParamBO;
import com.example.common.vo.PageResultVO;
import com.example.common.vo.ResultDataVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/space")
@Api(tags = "空间")
@CrossOrigin(origins = "*")
public class SpaceController {
    @Resource
    private SpaceService spaceService;

    @ApiOperation("获取空间列表")
    @PostMapping("/getList")
    public ResultDataVO<List<SpaceListVO>> getList() {
        return ResultDataVO.success(spaceService.getList());
    }

    @ApiOperation("获取空间分页")
    @PostMapping("/paginate")
    public ResultDataVO<List<SpacePaginateVO>> paginate(
            @ApiParam(name = "pageIndex", value = "当前页数", required = true) @RequestParam Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示条目个数", required = true) @RequestParam Integer pageSize
    ) {
        IPage page = spaceService.getPaginate(new PageParamBO(pageIndex,pageSize));
        //构建响应对象
        return ResultDataVO.success(page.getRecords(), new PageResultVO(page));
    }

    /**
     * 详情
     *
     * @param spaceId
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("/detail/{id}")
    private ResultDataVO<SpaceDetailVO> detail(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") Integer spaceId) {
        return ResultDataVO.success(spaceService.detail(spaceId));
    }

    @ApiOperation("获取xml")
    @GetMapping(value = "/getXml/{id}", produces = "application/xhtml+xml;charset=UTF-8")
    private String getXml(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") Integer spaceId) {
        return spaceService.getXml(spaceId);
    }
}

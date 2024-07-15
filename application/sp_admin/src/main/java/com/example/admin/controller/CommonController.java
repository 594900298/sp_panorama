package com.example.admin.controller;

import com.example.common.po.ResultData;
import com.example.common.service.CommonService;
import com.example.common.vo.UpLoadFileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/common")
@Api(tags = "通用接口")
public class CommonController {
    @Autowired
    private CommonService commonService;

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @ApiOperation("上传图片")
    @PostMapping("/uploadPic")
    public ResultData<UpLoadFileVO> uploadPic(
            @ApiParam(name = "img", value = "图片流", required = true) @RequestParam("img") MultipartFile file
    ) {
        return ResultData.success(commonService.uploadPic(file), "上传成功");
    }

    /**
     * 上传音乐
     *
     * @param file
     * @return
     */
    @ApiOperation("上传图片")
    @PostMapping("/uploadMusic")
    public ResultData<UpLoadFileVO> uploadMusic(
            @ApiParam(name = "file", value = "文件流", required = true) @RequestParam("file") MultipartFile file
    ) {
        return ResultData.success(commonService.uploadMusic(file), "上传成功");
    }

}

package com.example.api.controller;


import com.example.common.po.ResultData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {

    /**
     * 用户登录
     *
     * @return
     */
    @GetMapping("/index")
    public ResultData index(){
        return ResultData.success(null,"running");
    }
}

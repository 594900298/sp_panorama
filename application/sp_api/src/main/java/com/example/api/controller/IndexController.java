package com.example.api.controller;


import com.example.common.vo.ResultDataVO;
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
    public ResultDataVO index(){
        return ResultDataVO.success(null,"running");
    }
}

package com.example.common.service.impl;

import com.example.common.service.CommonService;
import com.example.common.utils.UpLoadUtil;
import com.example.common.vo.UpLoadFileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * 管理员
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private UpLoadUtil upLoadUtil;

    @Override
    public UpLoadFileVO uploadPic(MultipartFile file) {
        return this.upLoadUtil.upload(file, "jpg,jpeg,gif,png,bmp");
    }
}

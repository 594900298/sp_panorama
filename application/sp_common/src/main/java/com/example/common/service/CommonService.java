package com.example.common.service;

import com.example.common.vo.UpLoadFileVO;
import org.springframework.web.multipart.MultipartFile;

public interface CommonService {
    UpLoadFileVO uploadPic(MultipartFile file);

    UpLoadFileVO uploadMusic(MultipartFile file);
}


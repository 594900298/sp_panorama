package com.example.admin.service;

import com.example.admin.dto.LoginDTO;
import com.example.admin.vo.LoginVO;
import com.example.common.exception.ServiceException;

public interface LoginService {
    LoginVO login (LoginDTO loginDTO) throws Exception;

    LoginVO refreshToken(String refreshToken, String model) throws ServiceException;
}

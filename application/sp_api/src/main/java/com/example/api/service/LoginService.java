package com.example.api.service;

import com.example.api.vo.LoginVO;
import com.example.common.exception.ServiceException;

public interface LoginService {
    LoginVO refreshToken(String refreshToken, String model) throws ServiceException;
}

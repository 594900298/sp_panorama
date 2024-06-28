package com.example.api.service;

import com.example.api.vo.LoginVO;
import com.example.api.dto.LoginCodeDTO;
import com.example.common.exception.ServiceException;

public interface LoginService {
    LoginVO code(LoginCodeDTO loginCodeDTO) throws Exception;

    LoginVO refreshToken(String refreshToken, String model) throws ServiceException;
}

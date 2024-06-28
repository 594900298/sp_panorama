package com.example.common.annotation.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.common.annotation.MobileValidate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义验证手机号
 */
public class MobileValidateImpl implements ConstraintValidator<MobileValidate, String> {
    private boolean required = true;
    String REGEX = "[1]([3-9])[0-9]{9}$";

    @Override
    public void initialize(MobileValidate constraintAnnotation) {
        //获取到填的值:true或者false
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //必填：则使用手机号码校验工具类去校验
        if (required) {
            return checkUtils(value);
            //非必填：
        } else {
            //传的号码为空
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                //传的号码非空，则使用手机号码校验工具类去校验
                return checkUtils(value);
            }
        }
    }

    /**
     * 验证手机号码
     */
    public boolean checkUtils(String mobile) {
        Pattern mobile_pattern = Pattern.compile(REGEX);
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(mobile);
        return matcher.matches();
    }
}
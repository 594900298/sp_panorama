package com.example.common.annotation;

import com.example.common.annotation.impl.MobileValidateImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
//范围
@Retention(RetentionPolicy.RUNTIME) // 运行时
@Documented

@Constraint(validatedBy = {MobileValidateImpl.class})
public @interface MobileValidate {
    boolean required() default true;

    String message() default "手机号码格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

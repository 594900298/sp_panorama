package com.example.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaVO {
    @ApiModelProperty(value = "验证码签名", example = "code")
    private String codeSign;

    @ApiModelProperty(value = "验证码图片", example = "base64url")
    private String base64CodeImg;
}

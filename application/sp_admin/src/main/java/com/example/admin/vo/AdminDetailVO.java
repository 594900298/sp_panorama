package com.example.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDetailVO {
    @ApiModelProperty(value = "用户id", example = "1")
    private Integer userId;

    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;

    @ApiModelProperty(value = "真实姓名", example = "半夏")
    private String real_name;

    @ApiModelProperty(value = "手机号", example = "13800000001")
    private String mobile;

    @ApiModelProperty(value = "管理员状态 1-正常 0-禁用", example = "1")
    private Boolean status;
}

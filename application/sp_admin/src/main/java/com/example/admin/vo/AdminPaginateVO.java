package com.example.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminPaginateVO {
    @ApiModelProperty(value = "用户id", example = "1")
    private Integer userId;

    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;

    @ApiModelProperty(value = "真实姓名", example = "半夏")
    private String realName;

    @ApiModelProperty(value = "手机号", example = "13800000001")
    private String mobile;

    @ApiModelProperty(value = "管理员状态 1-正常 0-禁用", example = "1")
    private Boolean status;

    @ApiModelProperty(value = "是否主账号 1-主账号", example = "1")
    private Boolean isMain;

    @ApiModelProperty(value = "排序", example = "0")
    private Integer sort;

    @ApiModelProperty(value = "创建时间", example = "1711954731")
    private Integer createTime;

}

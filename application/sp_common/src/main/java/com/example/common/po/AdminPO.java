package com.example.common.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 后台管理员
 *
 * @TableName sp_admin
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@TableName(value = "sp_admin")
public class AdminPO implements Serializable {
    @ApiModelProperty(value = "用户id")
    @TableId(type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "账号状态 1-正常 0-禁用")
    private Boolean status;

    @ApiModelProperty(value = "是否为主账号 1-主账号")
    private Boolean isMain;

    @ApiModelProperty(value = "登录ip")
    private String loginIp;

    @ApiModelProperty(value = "登录时间")
    private Integer loginTime;

    @ApiModelProperty(value = "登录次数")
    private Integer loginNum;

    private String uuid;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否系统 1-是 0-否")
    private Boolean isSystem;

    @ApiModelProperty(value = "删除时间 不为0则软删")
    @TableField(fill = FieldFill.INSERT)
    private Integer deleteTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateTime;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Integer createTime;

    private static final long serialVersionUID = 1L;

}


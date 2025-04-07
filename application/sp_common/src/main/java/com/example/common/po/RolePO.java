package com.example.common.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 后台角色
 *
 * @TableName sp_role
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@TableName(value ="sp_role")
public class RolePO implements Serializable {
    @ApiModelProperty(value = "角色id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer roleId;

    @ApiModelProperty(value = "角色名称", example = "半夏")
    private String roleName;

    @ApiModelProperty(value = "角色描述", example = "半夏")
    private String roleDesc;

    @ApiModelProperty(value = "权限", example = "")
    private String rules;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "是否系统", example = "Boolean")
    private Boolean isSystem;

    @ApiModelProperty(value = "创建时间", example = "1714794887")
    @TableField(fill = FieldFill.INSERT)
    private Integer createTime;

    private static final long serialVersionUID = 1L;

}
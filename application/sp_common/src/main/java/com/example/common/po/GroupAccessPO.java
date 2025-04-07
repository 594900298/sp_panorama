package com.example.common.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @TableName sp_group_access
 */
@TableName(value ="sp_group_access")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupAccessPO implements Serializable {
    @ApiModelProperty(value = "用户id", example = "1")
    private Integer uid;

    @ApiModelProperty(value = "角色id", example = "1")
    private Integer roleId;

    private static final long serialVersionUID = 1L;
}
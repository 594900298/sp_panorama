package com.example.common.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.common.Interface.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 学校管理规则列表
 *
 * @TableName sp_rule
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Rule implements Serializable, TreeNode {
    @ApiModelProperty(value = "权限节点id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer ruleId;

    @ApiModelProperty(value = "父节点", example = "1")
    private Integer pid;

    @ApiModelProperty(value = "权限名称", example = "半夏")
    private String name;

    @ApiModelProperty(value = "权限规则", example = "")
    private String ruleUrl;

    @ApiModelProperty(value = "图标", example = "")
    private String icon;

    @ApiModelProperty(value = "备注", example = "")
    private String remark;

    @ApiModelProperty(value = "菜单", example = "Boolean")
    private Boolean isMenu;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "创建时间", example = "1714794887")
    @TableField(fill = FieldFill.INSERT)
    private Integer createTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String getId() {
        return String.valueOf(this.getRuleId());
    }

    @Override
    public String getParentId() {
        return String.valueOf(this.getPid());
    }

    @Override
    public String getWeight() {
        return String.valueOf(this.getSort());
    }
}
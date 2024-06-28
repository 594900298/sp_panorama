package com.example.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户
 *
 * @TableName sp_user
 */
@TableName(value = "sp_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @ApiModelProperty(value = "用户id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "用户手机号码", example = "1380000001")
    private String mobile;

    @ApiModelProperty(value = "用户姓名", example = "半夏")
    private String name;

    @ApiModelProperty(value = "状态", example = "Boolean")
    private Boolean status;

    @ApiModelProperty(value = "用户openid", example = "oPj7W5TJFzG90mDKuqSyHe5e7gow")
    private String openid;

    @ApiModelProperty(value = "uuid用户唯一标识", example = "4d87a868-39a1-448c-b4e7-6bc7584744cf")
    private String uuid;

    @ApiModelProperty(value = "登录ip", example = "192.168.1.163")
    private String loginIp;

    @ApiModelProperty(value = "登录时间", example = "1714794887")
    private Integer loginTime;

    @ApiModelProperty(value = "登录次数", example = "1")
    private Integer loginNum;

    @ApiModelProperty(value = "创建时间", example = "1714794887")
    private Integer createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getOpenid() == null ? other.getOpenid() == null : this.getOpenid().equals(other.getOpenid()))
                && (this.getUuid() == null ? other.getUuid() == null : this.getUuid().equals(other.getUuid()))
                && (this.getLoginIp() == null ? other.getLoginIp() == null : this.getLoginIp().equals(other.getLoginIp()))
                && (this.getLoginTime() == null ? other.getLoginTime() == null : this.getLoginTime().equals(other.getLoginTime()))
                && (this.getLoginNum() == null ? other.getLoginNum() == null : this.getLoginNum().equals(other.getLoginNum()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getOpenid() == null) ? 0 : getOpenid().hashCode());
        result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
        result = prime * result + ((getLoginIp() == null) ? 0 : getLoginIp().hashCode());
        result = prime * result + ((getLoginTime() == null) ? 0 : getLoginTime().hashCode());
        result = prime * result + ((getLoginNum() == null) ? 0 : getLoginNum().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", mobile=").append(mobile);
        sb.append(", name=").append(name);
        sb.append(", status=").append(status);
        sb.append(", openid=").append(openid);
        sb.append(", uuid=").append(uuid);
        sb.append(", loginIp=").append(loginIp);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", loginNum=").append(loginNum);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
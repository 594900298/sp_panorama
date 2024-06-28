package com.example.common.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @TableName sp_user_address
 */
@TableName(value = "sp_user_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer addressId;

    @ApiModelProperty(value = "用户id", example = "1")
    private Integer userId;

    /**
     * 省id
     */
    @ApiModelProperty(value = "省id", example = "1")
    private Integer provinceId;

    @ApiModelProperty(value = "省名称", example = "上海")
    private String provinceName;

    @ApiModelProperty(value = "市id", example = "1")
    private Integer cityId;

    @ApiModelProperty(value = "市名称", example = "上海")
    private String cityName;

    @ApiModelProperty(value = "区id", example = "1")
    private Integer districtId;

    @ApiModelProperty(value = "区名称", example = "浦东新区")
    private String districtName;

    @ApiModelProperty(value = "收货人名称", example = "半夏")
    private String name;

    @ApiModelProperty(value = "手机号码", example = "1380000001")
    private String mobile;

    @ApiModelProperty(value = "详细地址", example = "迪士尼")
    private String fullAddress;

    @ApiModelProperty(value = "是否默认", example = "Boolean")
    private Boolean isDefault;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateTime;

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
        UserAddress other = (UserAddress) that;
        return (this.getAddressId() == null ? other.getAddressId() == null : this.getAddressId().equals(other.getAddressId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getProvinceId() == null ? other.getProvinceId() == null : this.getProvinceId().equals(other.getProvinceId()))
                && (this.getProvinceName() == null ? other.getProvinceName() == null : this.getProvinceName().equals(other.getProvinceName()))
                && (this.getCityId() == null ? other.getCityId() == null : this.getCityId().equals(other.getCityId()))
                && (this.getCityName() == null ? other.getCityName() == null : this.getCityName().equals(other.getCityName()))
                && (this.getDistrictId() == null ? other.getDistrictId() == null : this.getDistrictId().equals(other.getDistrictId()))
                && (this.getDistrictName() == null ? other.getDistrictName() == null : this.getDistrictName().equals(other.getDistrictName()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
                && (this.getFullAddress() == null ? other.getFullAddress() == null : this.getFullAddress().equals(other.getFullAddress()))
                && (this.getIsDefault() == null ? other.getIsDefault() == null : this.getIsDefault().equals(other.getIsDefault()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAddressId() == null) ? 0 : getAddressId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getProvinceId() == null) ? 0 : getProvinceId().hashCode());
        result = prime * result + ((getProvinceName() == null) ? 0 : getProvinceName().hashCode());
        result = prime * result + ((getCityId() == null) ? 0 : getCityId().hashCode());
        result = prime * result + ((getCityName() == null) ? 0 : getCityName().hashCode());
        result = prime * result + ((getDistrictId() == null) ? 0 : getDistrictId().hashCode());
        result = prime * result + ((getDistrictName() == null) ? 0 : getDistrictName().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getFullAddress() == null) ? 0 : getFullAddress().hashCode());
        result = prime * result + ((getIsDefault() == null) ? 0 : getIsDefault().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", addressId=").append(addressId);
        sb.append(", userId=").append(userId);
        sb.append(", provinceId=").append(provinceId);
        sb.append(", provinceName=").append(provinceName);
        sb.append(", cityId=").append(cityId);
        sb.append(", cityName=").append(cityName);
        sb.append(", districtId=").append(districtId);
        sb.append(", districtName=").append(districtName);
        sb.append(", name=").append(name);
        sb.append(", mobile=").append(mobile);
        sb.append(", fullAddress=").append(fullAddress);
        sb.append(", isDefault=").append(isDefault);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
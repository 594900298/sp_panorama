package com.example.api.dto;

import com.example.common.annotation.MobileValidate;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressAddDTO {
    @ApiModelProperty(value = "省id", required = true, example = "1")
    @NotNull(message = "请选择省市区")
    private Integer provinceId;

    @ApiModelProperty(value = "省名称", required = true, example = "上海市")
    @NotNull(message = "请选择省市区")
    private String provinceName;

    @ApiModelProperty(value = "市id", required = true, example = "1")
    @NotNull(message = "请选择省市区")
    private Integer cityId;

    @ApiModelProperty(value = "市名称", required = true, example = "上海市")
    @NotNull(message = "请选择省市区")
    private String cityName;

    @ApiModelProperty(value = "区id", required = true, example = "1")
    @NotNull(message = "请选择省市区")
    private Integer districtId;

    @ApiModelProperty(value = "区名称", required = true, example = "浦东新区")
    @NotNull(message = "请选择省市区")
    private String districtName;

    @ApiModelProperty(value = "收货人名称", required = true, example = "半夏")
    @NotNull(message = "请填写收货人名称")
    private String name;

    @ApiModelProperty(value = "收货人手机号", required = true, example = "13800000001")
    @NotNull(message = "请填写收货人手机号码")
    @MobileValidate(message = "请输入正确的手机号")
    private String mobile;

    @ApiModelProperty(value = "收货地址", required = true, example = "迪士尼")
    @NotNull(message = "请填写详情地址")
    @Size(max = 50, message = "详情地址不能超过50字")
    private String fullAddress;

    @ApiModelProperty(value = "是否默认 0非 1真【需要确保只有一个】", required = true, example = "true")
    @NotNull(message = "请选择是否为默认地址")
    private Boolean isDefault;
}

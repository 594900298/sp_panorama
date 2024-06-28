package com.example.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginCodeDTO {
    @ApiModelProperty(value = "code", example = "0a379gml2igymd4YjEll24qLBt079gmF")
    @NotNull(message = "缺少code")
    private String code;
}

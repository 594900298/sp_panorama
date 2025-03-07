package com.example.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应对象
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDataVO<T> {
    @ApiModelProperty(value = "状态码")
    private int errCode;
    @ApiModelProperty(value = "异常信息")
    private String errMsg;
    @ApiModelProperty(value = "数据")
    private T data;
    @ApiModelProperty(value = "分页数据")
    private PageResultVO page;

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultDataVO<T> success(T data) {
        ResultDataVO<T> resultDataVO = new ResultDataVO<>();
        resultDataVO.setErrCode(0);
        resultDataVO.setErrMsg("ok");
        resultDataVO.setData(data);
        return resultDataVO;
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultDataVO<T> success(T data, PageResultVO page) {
        ResultDataVO<T> resultDataVO = new ResultDataVO<>();
        resultDataVO.setErrCode(0);
        resultDataVO.setErrMsg("ok");
        resultDataVO.setData(data);
        resultDataVO.setPage(page);
        return resultDataVO;
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultDataVO<T> success(T data, String message) {
        ResultDataVO<T> resultDataVO = new ResultDataVO<>();
        resultDataVO.setErrCode(0);
        resultDataVO.setErrMsg(message);
        resultDataVO.setData(data);
        return resultDataVO;
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultDataVO<T> success(int errCode, T data, String message) {
        ResultDataVO<T> resultDataVO = new ResultDataVO<>();
        resultDataVO.setErrCode(errCode);
        resultDataVO.setErrMsg(message);
        resultDataVO.setData(data);
        return resultDataVO;
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResultDataVO<T> fail(int code, String message) {
        ResultDataVO<T> resultDataVO = new ResultDataVO<>();
        resultDataVO.setErrCode(code);
        resultDataVO.setErrMsg(message);
        return resultDataVO;
    }

}

package com.example.common.po;

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
public class ResultData<T> {
    @ApiModelProperty(value = "状态码")
    private int errCode;
    @ApiModelProperty(value = "异常信息")
    private String errMsg;
    @ApiModelProperty(value = "数据")
    private T data;
    @ApiModelProperty(value = "分页数据")
    private PageResult page;

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setErrCode(0);
        resultData.setErrMsg("ok");
        resultData.setData(data);
        return resultData;
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultData<T> success(T data, PageResult page) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setErrCode(0);
        resultData.setErrMsg("ok");
        resultData.setData(data);
        resultData.setPage(page);
        return resultData;
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultData<T> success(T data, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setErrCode(0);
        resultData.setErrMsg(message);
        resultData.setData(data);
        return resultData;
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultData<T> success(int errCode, T data, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setErrCode(errCode);
        resultData.setErrMsg(message);
        resultData.setData(data);
        return resultData;
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResultData<T> fail(int code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setErrCode(code);
        resultData.setErrMsg(message);
        return resultData;
    }

}

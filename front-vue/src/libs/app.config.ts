/**
 * 应用配置
 */
export default {
    commonConfig: {
        //是否开启 history 路由模式,不开启则使用hash模式【开启history模式可能会冲突后端路由】
        history: false,
    },
    //请求配置
    requestConfig: {
        //超时时间
        timeout: 30000,
        //是否携带请求信息
        withCredentials: true,
        //请求地址
        webServiceUrl: '/api/',
        //请求时是否显示加载
        showLoading: true,
        //是否刷新token
        isRefreshing: true,
        //刷新地址
        refreshUrl: "login/refreshToken",
    },
    //缓存配置
    storageConfig: {
        //前缀
        prefix: "admin_",
        //访问令牌名称
        accessTokenName: "token",
        //刷新令牌名称
        refreshTokenName: "refreshToken",
    },
    //editor富文本配置
    editorConfig: {
        // 图片参数名
        name: "img",
        //服务器地址
        action: "common/uploadPic"
    },
    //配置响应码各响应码作用
    //执行顺序 showMessage->storageClear->backToPrevious
    returnHandleConfig: {
        //提示错误的响应码
        showMessage: [
            1,
            2,
            4,
            103,
            104,
        ],
        //清理缓存响应码
        storageClear: [
            10011,
            -1
        ],
        //返回上一级响应码
        backToPrevious: [
            -1,
            2,
            10011,
        ],
    },
}

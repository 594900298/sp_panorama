/**
 * 请求封装
 */
import axios from 'axios'
import router from '../router'
import { storage } from "./storage"
import { ElLoading, ElMessage } from 'element-plus'
import appConfig from "./app.config";
import qs from "qs";
import type { responseInterface } from "@/interfaces"
//是否显示Loading,配置是否刷新令牌,刷新token地址,服务器地址,超时时间,携带请求信息
let { showLoading, isRefreshing, refreshUrl, domain, webServiceUrl, timeout, withCredentials } = appConfig.requestConfig;
//访问令牌名称,刷新token名称
let { accessTokenName, refreshTokenName } = appConfig.storageConfig;
//提示,清除,返回上一级,超时时间
let { showMessage, storageClear, backToPrevious } = appConfig.returnHandleConfig;
//配置请求超时时间
axios.defaults.timeout = timeout;
//是否携带请求信息
axios.defaults.withCredentials = withCredentials;
//请求队列,用于刷新token后重新调用接口
let subscribers: Array<any> = []
axios.defaults.transformRequest = [
    (data) => {
        if (data) {
            Object.keys(data).forEach((key: any) => {
                if (Array.isArray(data[key]) && data[key].length === 0) {
                    data[key] = null;
                }
            });
            // 正常请求需要json格式数据，formdata上传数据不需要格式数据
            return data instanceof FormData ? data : JSON.stringify(data);
        }
    },
];

//请求拦截器
axios.interceptors.request.use(
    (config: any) => {
        config.headers["X-Requested-With"] = "XMLHttpRequest"; //标记异步请求
        config.headers[accessTokenName] = storage.get(accessTokenName)
        config.url = webServiceUrl + config.url
        return config
    },
    (error: any) => {
        return Promise.reject(error)
    }
)

//响应拦截器
axios.interceptors.response.use(
    (response: any) => {
        returnHandle(response.data)
        return response
    },
    (error: any) => {
        returnHandle(error.response.data)
        return Promise.reject(error.response.data)
    }
)

/**
 * 拦截处理
 * @param response
 */
function returnHandle(response: any) {
    //显示提示
    if (showMessage.indexOf(response.errCode) != -1) {
        ElMessage({
            dangerouslyUseHTMLString: true,
            message: response.errMsg,
            type: 'error',
        })
    }
    //清理缓存
    if (storageClear.indexOf(response.errCode) != -1) {
        storage.clear()
    }
    //返回上一级
    if (backToPrevious.indexOf(response.errCode) != -1) {
        router.go(-1)
    }
}

/**
 * 网络请求,类型GET
 * @param {String} url
 * @param {Object} params
 * @return {Object}
 */
export function get(url: string, params: object = {}): Promise<responseInterface> {
    let loadingInstance: any = null;
    if (showLoading) { loadingInstance = ElLoading.service(); }
    return new Promise((resolve, reject) => {
        const timestamp = (new Date()).getTime() / 1000
        const expire = storage.getExpire(accessTokenName)
        if (timestamp > expire - 3600 && url !== refreshUrl && expire >= 0) {
            resolve(reload(url, {}, params, 'get'))
            if (showLoading) { loadingInstance.close() }
            return
        }
        axios.get(url, {
            params
        }).then(function (response) {
            if (showLoading) { loadingInstance.close() }
            resolve(response.data)
        }).catch(function (err) {
            if (showLoading) { loadingInstance.close() }
            resolve(err)
        })
    })
}

/**
 * 网络请求,类型POST
 * @param {String} url
 * @param {Object} data
 * @param {Object} params
 * @return {Object}
 */
export function post(url: string, data: object = {}, params: object = {}): Promise<responseInterface> {
    let loadingInstance: any = null;
    if (showLoading) { loadingInstance = ElLoading.service(); }
    return new Promise((resolve, reject) => {
        const timestamp = (new Date()).getTime() / 1000
        const expire = storage.getExpire(accessTokenName)
        if (timestamp > expire - 3600 && url !== refreshUrl && expire >= 0) {
            resolve(reload(url, data, params, 'post'))
            if (showLoading) { loadingInstance.close() }
            return
        }
        axios.post(url, data, {
            params,
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            if (showLoading) { loadingInstance.close() }
            resolve(response.data)
        }).catch(function (err) {
            if (showLoading) { loadingInstance.close() }
            resolve(err)
        })
    })
}

/**
 * 令牌快过期,请求刷新token
 * @param {String} url
 * @param {Object} params
 * @param {String} method
 * @return {Object}
 */
function reload(url: string, data: object, params: object, method: string): Promise<responseInterface> {
    if (isRefreshing) {
        post(refreshUrl, {
            [refreshTokenName]: storage.get(refreshTokenName)
        }).then((res: any) => {
            if (res.errCode === 0) {
                storage.set(accessTokenName, res.data[accessTokenName], res.data.expire_in)
                storage.set(refreshTokenName, res.data[refreshTokenName])
                subscribers.forEach((callback) => {
                    callback()
                })
                subscribers = []
            }
            isRefreshing = true
        })
    }
    isRefreshing = false
    return new Promise((resolve) => {
        subscribers.push(() => {
            if (method === 'get') {
                get(url, data).then((data) => {
                    resolve(data)
                })
            } else if (method === 'post') {
                post(url, data, params).then((data) => {
                    resolve(data)
                })
            }
        })
    })
}


import appConfig from "./app.config";
import { storage } from './storage'
import { ElMessage } from 'element-plus'
let { accessTokenName } = appConfig.storageConfig
/**
 * 判断登录状态
 * @return {Boolean} true 已登录 | false 未登录
 */
export function isLogin() {
    return storage.get(accessTokenName)
}

/**
 * 下载文件
 * @param {*} url 
 */
export function downloadFile(url: string) {
    let a = document.createElement('a')
    a.href = url
    a.click()
}

/**
 * 弹出提示
 * @param message 
 * @param type 
 */
export function message(message:string,type:string="success"){
    ElMessage({
        dangerouslyUseHTMLString: true,
        message,
        type,
    })
}

/**
 * 数组转字符串
 * @param arr 
 */
export function arrayToString(arr: Array<any>) {
    return arr.toString();
}

/**
 * 字符串转数组
 * @param str 
 */
export function stringToArray(str: String,separator=",") {
    return str.split(separator);
}

/**
 * 方法防抖
 * @param func 
 * @param delay 
 * @returns 
 */
let timer:any = null;
export function debounce(func, delay=1000) {
    clearTimeout(timer);
    timer = setTimeout(() => {
        func.apply(this, arguments);
    }, delay);
}

/**
 * 笛卡尔乘积
 * @param array 
 */
export function calcDescartes (array) {
    if (array.length < 2) return [array[0]] || [];
    return [].reduce.call(array, function (col, set) {
        var res = [];
        col.forEach(function (c) {
            set.forEach(function (s) {
                var t = [].concat(Array.isArray(c) ? c : [c]);
                t.push(s);
                res.push(t);
            })
        });
        return res;
    });
}

/**
 * 格式化时间
 */
export function dateFormat(date,fmt="yyyy-MM-dd HH:mm:ss") {
    if(!(date instanceof Date)){
        date = new Date(date*1000);
    }
    let ret
    const opt = {
        'y+': date.getFullYear().toString(), // 年
        'M+': (date.getMonth() + 1).toString(), // 月
        'd+': date.getDate().toString(), // 日
        'H+': date.getHours().toString(), // 时
        'm+': date.getMinutes().toString(), // 分
        's+': date.getSeconds().toString(), // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
    }
    for (let k in opt) {
        ret = new RegExp('(' + k + ')').exec(fmt)
        if (ret) {
            fmt = fmt.replace(
                ret[1],
                ret[1].length == 1 ? opt[k] : opt[k].padStart(ret[1].length, '0')
            )
        }
    }
    return fmt
};
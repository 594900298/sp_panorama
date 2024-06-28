//请求响应接口
interface responseInterface {
    errCode: number,
    errMsg: string,
    data?: any,
    page?:any,
    permission?: Array<string>;
}

export type { 
    responseInterface
 };
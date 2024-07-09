import appConfig from "./app.config";
//缓存前缀
let { prefix } = appConfig.storageConfig;
/**
 * 缓存助手函数
 */
class Storage {

    /**
     * 获取当前时间
     * @returns {number}
     */
    currentTime() {
        return Number(new Date())
    }

    /**
     * 判断值
     * @param value
     * @returns {boolean}
     */
    isNotExist(value: any) {
        return value === null || typeof (value) === 'undefined'
    }

    /**
     * isOutPeriod 方法，判断 value 值是否过期
     *
     * @param value
     * @returns {boolean}
     */
    isOutPeriod(value: any) {
        if (!value.value) {
            return true
        }
        return this.currentTime() > value.expired * 1000
    }

    /**
     * set 方法，设置
     * @param key String 键
     * @param value 值
     * @param expired writeTime 写入时应该为11位时间戳
     */
    set(key: string, value: any, expired: number | undefined = undefined) {
        let data = {
            value,
            writeTime: this.currentTime(), // 写入时间
            expired
        }
        // 值是数组，不能直接存储，需要转换 JSON.stringify
        localStorage.setItem(prefix + key, JSON.stringify(data))
    }

    /**
     * get 方法，获取
     * @param key 键
     */
    get(key: string) {
        const dataJSON: any = localStorage.getItem(prefix + key)
        // 当目标不存在时直接结束
        if (this.isNotExist(dataJSON)) {
            return null
        }
        try {
            let data = JSON.parse(dataJSON)
            // 当数据的存在周期未定义时，它被认为是永久的
            if (this.isNotExist(data.expired)) {
                return data.value
            }
            // 数据声明期结束时释放数据
            if (this.isOutPeriod(data)) {
                this.del(key)
                return null
            }
            return data.value
        } catch (e) {
            this.del(key)
            return null
        }
    }

    /**
     * get 方法，获取
     * @param key 键
     */
    getExpire(key: string) {
        const dataJSON: any = localStorage.getItem(prefix + key)
        // 当目标不存在时直接结束
        if (this.isNotExist(dataJSON)) {
            return -1
        }
        try {
            let data = JSON.parse(dataJSON)

            // 数据声明期结束时释放数据
            if (this.isOutPeriod(data)) {
                this.del(key)
                return -1
            }
            return data.expired
        } catch (e) {
            this.del(key)
            return -1
        }
    }

    /**
     * del 方法，删除
     * @param key 键
     */
    del(key: string) {
        localStorage.removeItem(prefix + key)
    }

    /**
     * clear 清空
     */
    clear() {
        let itemKey = []
        // 获取匹配前缀的本地存储
        for (let i = 0; i < localStorage.length; i++) {
            let key: string = localStorage.key(i) as string;
            if (key.includes(prefix)) {
                itemKey.push(key)
            }
        }
        itemKey.forEach(key => {
            localStorage.removeItem(key)
        })
    }
}
export const storage = new Storage()
import { createApp } from 'vue'
//引入Pinia
import { createPinia } from 'pinia'
//引入pinia数据持久化插件
import piniaPluginPersist from 'pinia-plugin-persist'
import App from './App.vue'
//引入路由
import router from './router'
//引入ElementPlus
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import 'element-plus/dist/index.css'
const app = createApp(App)
// 挂载Pinia
app.use(createPinia().use(piniaPluginPersist))
// 挂载路由
app.use(router)
// 挂载elementUI
app.use(ElementPlus, { locale: zhCn, })
app.mount('#app')

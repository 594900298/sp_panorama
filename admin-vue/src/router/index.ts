import { createRouter, createWebHistory,createWebHashHistory } from 'vue-router'
import { routers } from '../libs/router'
import appConfig from "@/libs/app.config";
let { history } = appConfig.commonConfig
const router = createRouter({
  history: history?createWebHistory(import.meta.env.BASE_URL):createWebHashHistory(import.meta.env.BASE_URL),
  routes:routers,
})
export default router
